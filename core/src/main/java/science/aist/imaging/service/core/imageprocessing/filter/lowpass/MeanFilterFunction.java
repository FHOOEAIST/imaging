/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.lowpass;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import lombok.Setter;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * <p>Implementation of a mean filter</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class MeanFilterFunction<T, R> implements ImageFunction<T, R> {

    private BiFunction<ImageWrapper<T>, double[][], ImageWrapper<R>> convolveFunction;
    private int radius = 1;
    public MeanFilterFunction(BiFunction<ImageWrapper<T>, double[][], ImageWrapper<R>> convolveFunction) {
        this.convolveFunction = convolveFunction;
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        return convolveFunction.apply(imageWrapper, getMeanMask());
    }

    private double[][] getMeanMask() {
        double[][] meanMask = new double[radius * 2 + 1][radius * 2 + 1];
        double value = 1.0 / (double) (radius * 2 + 1) * (radius * 2 + 1);
        for (double[] doubles : meanMask) {
            Arrays.fill(doubles, value);
        }
        return meanMask;
    }
}
