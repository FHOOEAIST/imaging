/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.jack.general.transformer.Transformer;

import java.util.stream.IntStream;

/**
 * <p>Transforms a RGB image into a RGBA image and vice versa.</p>
 * <p>RGB -&gt; RGBA: The A channel is set to 255</p>
 * <p>RGBA -&gt; RGB: The A channel is just thrown away</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class RGB2RGBATransformer<P, T> implements Transformer<ImageWrapper<P>, ImageWrapper<T>> {

    private static final TypeChecker fromTC = new TypeChecker(ChannelType.RGBA);
    private static final TypeChecker toTC = new TypeChecker(ChannelType.RGB);

    @NonNull
    private final ImageFactory<P> pProvider;
    @NonNull
    private final ImageFactory<T> tProvider;


    private static <R, I> ImageWrapper<R> helper(ImageWrapper<I> imageWrapper, ChannelType newType, TypeChecker checker, ImageFactory<R> resultProvider) {
        checker.accept(imageWrapper);

        final int height = imageWrapper.getHeight();
        final int width = imageWrapper.getWidth();

        ImageWrapper<R> resultImageWrapper = resultProvider.getImage(height, width, newType);

        if (resultImageWrapper.supportsParallelAccess()) {
            IntStream.range(0, height).parallel().forEach(y -> setData(y, width, imageWrapper, resultImageWrapper, newType));
        } else {
            for (int y = 0; y < height; y++) {
                setData(y, width, imageWrapper, resultImageWrapper, newType);
            }
        }


        return resultImageWrapper;
    }

    private static <R, I> void setData(int y, int width, ImageWrapper<I> imageWrapper, ImageWrapper<R> resultImageWrapper, ChannelType newType) {
        for (int x = 0; x < width; x++) {
            for (int c = 0; c < 3; c++) {
                resultImageWrapper.setValue(x, y, c, imageWrapper.getValue(x, y, c));
            }
            if (newType == ChannelType.RGBA) resultImageWrapper.setValue(x, y, 3, 255);
        }
    }

    @Override
    public ImageWrapper<P> transformTo(ImageWrapper<T> rgbaImageWrapper) {
        return helper(rgbaImageWrapper, ChannelType.RGB, fromTC, pProvider);
    }

    @Override
    public ImageWrapper<T> transformFrom(ImageWrapper<P> rgbImageWrapper) {
        return helper(rgbImageWrapper, ChannelType.RGBA, toTC, tProvider);
    }
}
