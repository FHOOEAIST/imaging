/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.lowpass;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.core.imageprocessing.filter.ConvolveFunction;
import lombok.CustomLog;
import lombok.NonNull;
import lombok.Setter;

import java.util.function.BiFunction;

/**
 * <p>Implementation of a gauss filter</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@CustomLog
public class GaussFilterFunction<T, R> implements ImageFunction<T, R> {
    private double sigma = 2;
    private int radius = 4;
    private BiFunction<ImageWrapper<T>, double[][], ImageWrapper<R>> convolveFunction;

    public GaussFilterFunction(@NonNull ImageFactory<R> provider) {
        convolveFunction = new ConvolveFunction<>(provider);
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        return convolveFunction.apply(imageWrapper, gaussKernel());
    }

    private double[][] gaussKernel() {
        int size = 2 * radius + 1;
        double sum = 0;
        double[][] kernel = new double[size][size];
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                double val = g(x, y);
                sum += val;
                kernel[y + radius][x + radius] = val;
            }
        }
        log.info("Gauss Kernel sum: {} - Normalization will be done", sum);

        if (sum == 0) throw new IllegalStateException("Kernel sum is not allowed to be zero!");

        double sum2 = 0.0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                kernel[y + radius][x + radius] *= 1.0 / sum;
                sum2 += kernel[y + radius][x + radius];
            }
        }

        log.info("Gauss Kernel sum after normalization: {}", sum2);

        return kernel;
    }

    private double g(int x, int y) {
        return 1.0 / (2.0 * Math.PI * sigma * sigma) * Math.exp(-((x * x + y * y) / (2.0 * sigma * sigma)));
    }
}
