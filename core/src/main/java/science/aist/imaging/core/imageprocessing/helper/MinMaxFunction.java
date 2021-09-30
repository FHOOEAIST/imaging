/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.helper;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.jack.math.MinMax;

import java.util.Arrays;
import java.util.function.Function;

/**
 * <p>Function for getting all minimum and maximum values per channel in the image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class MinMaxFunction implements Function<ImageWrapper<?>, MinMax<double[]>> {
    @Override
    public MinMax<double[]> apply(ImageWrapper<?> imageWrapper) {
        double[] min = new double[imageWrapper.getChannels()];
        Arrays.fill(min, Double.MAX_VALUE);
        double[] max = new double[imageWrapper.getChannels()];
        Arrays.fill(max, Double.MIN_VALUE);

        for (int x = 0; x < imageWrapper.getWidth(); x++) {
            for (int y = 0; y < imageWrapper.getHeight(); y++) {
                for (int c = 0; c < imageWrapper.getChannels(); c++) {
                    double value = imageWrapper.getValue(x, y, c);
                    if (min[c] > value) {
                        min[c] = value;
                    }

                    if (max[c] < value) {
                        max[c] = value;
                    }
                }
            }
        }

        return MinMax.of(min, max);
    }

}
