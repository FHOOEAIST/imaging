/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.highpass;

import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Arrays;

/**
 * <p>Implementation of sobel edge detection</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class SobelEdgeDetectionFilterFunction<T, R> implements ImageFunction<T, R> {

    private static final double[][] SOBEL_H = new double[][]{{1.0, 0.0, -1.0}, {2.0, 0.0, -2.0}, {1.0, 0.0, -1.0}};
    private static final double[][] SOBEL_V = new double[][]{{1.0, 2.0, 1.0}, {0.0, 0.0, 0.0}, {-1.0, -2.0, -1.0}};

    private final ImageFunction<T, R> maskBasedDetector;


    public SobelEdgeDetectionFilterFunction(ImageFactory<R> provider) {
        MaskBasedEdgeDetection<T, R> maskBasedDetectorConf = new MaskBasedEdgeDetection<>(provider);
        maskBasedDetectorConf.setMasks(Arrays.asList(SOBEL_H, SOBEL_V));
        maskBasedDetector = maskBasedDetectorConf;
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        return maskBasedDetector.apply(imageWrapper);
    }
}
