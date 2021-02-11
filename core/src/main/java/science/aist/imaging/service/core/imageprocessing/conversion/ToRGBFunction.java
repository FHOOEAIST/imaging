/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion;

import lombok.NonNull;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.imaging.service.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.service.core.imageprocessing.transformers.RGB2BGRTransformer;
import science.aist.imaging.service.core.imageprocessing.transformers.RGB2RGBATransformer;

import java.util.stream.IntStream;

/**
 * <p>This class tries to convert any 2ByteImage to a RGB Image.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class ToRGBFunction<P, T> implements ImageFunction<P, T> {
    /**
     * Type checker for supported types
     */
    private static final TypeChecker checker = new TypeChecker(ChannelType.RGB, ChannelType.RGBA, ChannelType.BGR, ChannelType.GREYSCALE, ChannelType.BINARY);
    private ImageFactory<T> tProvider;
    private GenericImageWrapperTransformer<T, P> genericImageWrapperTransformer;
    /**
     * rgbaToRgb Converter Function
     */
    private ImageFunction<P, T> rgbaToRgb;
    /**
     * bgrToRgb Converter Function
     */
    private ImageFunction<P, T> bgrToRgb;
    /**
     * GreyscalOrBinaryToRgb Converter Function
     */
    private ImageFunction<P, T> greyscaleBinaryToRgb = input -> {
        int height = input.getHeight();
        int width = input.getWidth();
        ImageWrapper<T> rgbImageWrapper = tProvider.getImage(height, width, ChannelType.RGB);

        IntStream.range(0, input.getHeight()).parallel().forEach(y -> {
            for (int x = 0; x < input.getWidth(); x++) {
                for (int c = 0; c < 3; c++) {
                    rgbImageWrapper.setValue(x, y, c, input.getValue(x, y, 0));
                }
            }
        });

        return rgbImageWrapper;
    };


    public ToRGBFunction(@NonNull ImageFactory<T> tProvider, @NonNull ImageFactory<P> pProvider) {
        this.tProvider = tProvider;
        this.genericImageWrapperTransformer = new GenericImageWrapperTransformer<>(tProvider, pProvider);
        this.rgbaToRgb = new RGB2RGBATransformer<>(tProvider, pProvider)::transformTo;
        this.bgrToRgb = new RGB2BGRTransformer<>(tProvider, pProvider)::transformTo;
    }

    @Override
    public ImageWrapper<T> apply(ImageWrapper<P> imageWrapper) {
        checker.accept(imageWrapper);

        switch (imageWrapper.getChannelType()) {
            case RGB:
                return genericImageWrapperTransformer.transformTo(imageWrapper);
            case RGBA:
                return rgbaToRgb.apply(imageWrapper);
            case BGR:
                return bgrToRgb.apply(imageWrapper);
            case GREYSCALE:
            case BINARY:
                return greyscaleBinaryToRgb.apply(imageWrapper);
            default:
                throw new IllegalStateException();
        }
    }
}
