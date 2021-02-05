/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.core.imageprocessing.conversion.greyscale.GreyscaleLuminosityConverter;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <p>Converts a Colored Image into a Greyscale one</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class ColoredToGreyscaleFunction<I, T> implements ImageFunction<I, T> {

    /**
     * The supported types for this function
     */
    private static final TypeChecker typeChecker = new TypeChecker(Arrays.asList(ChannelType.GREYSCALE, ChannelType.BINARY, ChannelType.BGRA, ChannelType.RGBA, ChannelType.RGB, ChannelType.BGR));
    @NonNull
    private ImageFactory<T> provider;
    /**
     * The method with which a single colored pixel is converted into a greyscale pixel
     */
    private ColorToGreyScaleConverter colorToGreyScale = new GreyscaleLuminosityConverter();


    /**
     * Converts a given image wrapper into a greyscale image
     *
     * @param imageWrapper the colored input image supported types: see supportedTypes
     * @return a greyscale image
     */
    @Override
    public ImageWrapper<T> apply(ImageWrapper<I> imageWrapper) {
        typeChecker.accept(imageWrapper);

        if(imageWrapper.getChannelType() == ChannelType.GREYSCALE || imageWrapper.getChannelType() == ChannelType.BINARY){
            return imageWrapper.createCopy(provider);
        }

        ChannelType type = imageWrapper.getChannelType();

        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();

        ImageWrapper<T> result = provider.getImage(height, width, ChannelType.GREYSCALE);


        IntStream.range(0, height).parallel().forEach(y -> {
            for (int x = 0; x < width; x++) {
                result.setValue(x, y, 0, colorToGreyscaleConv(type, imageWrapper.getValues(x, y)));
            }
        });

        return result;
    }

    private double colorToGreyscaleConv(ChannelType type, double[] color) {
        return type == ChannelType.BGR || type == ChannelType.BGRA ? colorToGreyScale.toGreyscale(color[2], color[1], color[0]) : colorToGreyScale.toGreyscale(color[0], color[1], color[2]);
    }
}
