/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2Byte;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.core.imageprocessing.interpolation.BilinearInterpolationFunction;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.BiFunction;

/**
 * <p>Allows to scale a {@link Image2Byte}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
@Setter
public class TwoByteScaleFunction<T, R> implements ImageFunction<T, R> {
    /**
     * Type checker to check if the function allows the given image type
     */
    private static final TypeChecker typeCheck = new TypeChecker(ChannelType.GREYSCALE, ChannelType.BINARY);
    @NonNull
    private final ImageFactory<R> provider;
    /**
     * Interpolation function
     */
    private BiFunction<ImageWrapper<?>, JavaPoint2D, Double> interpolation = new BilinearInterpolationFunction(0.0);

    /**
     * the new width of the result image
     */
    private int newWidth;

    /**
     * the new height of the result image
     */
    private int newHeight;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        typeCheck.accept(imageWrapper);
        ImageWrapper<R> resultImageWrapper = provider.getImage(newHeight, newWidth);
        double scaleX = (double) imageWrapper.getWidth() / (double) newWidth;
        double scaleY = (double) imageWrapper.getHeight() / (double) newHeight;

        resultImageWrapper.applyFunction((image, x, y, c) -> image.setValue(x, y, c, interpolation.apply(imageWrapper, new JavaPoint2D(scaleX * x, scaleY * y)) + 0.5));

        return resultImageWrapper;
    }
}
