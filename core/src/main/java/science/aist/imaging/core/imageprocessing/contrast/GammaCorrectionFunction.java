/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.contrast;

import lombok.NonNull;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.core.imageprocessing.transformation.ValueTransformationFunction;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * <p>Gamma correction implementation</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class GammaCorrectionFunction<P, T> implements ImageFunction<P, T> {
    private static final int MAX_VAL = 255;
    private double gamma = 1;
    private BiFunction<ImageWrapper<P>, int[], ImageWrapper<T>> transformationFunction;

    public GammaCorrectionFunction(@NonNull ImageFactory<T> provider) {
        this.transformationFunction = new ValueTransformationFunction<>(provider);
    }

    @Override
    public ImageWrapper<T> apply(ImageWrapper<P> imageWrapper) {
        int[] transferFunction = new int[MAX_VAL + 1];
        IntStream.rangeClosed(0, MAX_VAL).parallel().forEach(i -> transferFunction[i] = (short) (Math.pow((double) i / ((double) MAX_VAL), gamma) * (double) MAX_VAL + 0.5));
        return transformationFunction.apply(imageWrapper, transferFunction);
    }
}
