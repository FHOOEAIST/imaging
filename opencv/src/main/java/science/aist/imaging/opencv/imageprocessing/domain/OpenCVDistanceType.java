/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opencv.imgproc.Imgproc;
import science.aist.imaging.opencv.imageprocessing.distance.OpenCVDistanceMap;

/**
 * <p>Distance Types for {@link OpenCVDistanceMap}</p>
 * <p>OpenCV: Type of the label array to build. If labelType==DIST_LABEL_CCOMP then each connected component of zeros in src
 * (as well as all the non-zero pixels closest to the connected component) will be assigned the same label. If labelType==DIST_LABEL_PIXEL
 * then each zero pixel (and all the non-zero pixels closest to it) gets its own label.</p>
 * <p><a href="https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html">https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html</a></p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVDistanceType {
    /**
     * CV_DIST_L1
     */
    L1(Imgproc.CV_DIST_L1),
    /**
     * CV_DIST_L2
     */
    L2(Imgproc.CV_DIST_L2),
    /**
     * CV_DIST_C
     */
    C(Imgproc.CV_DIST_C);

    /**
     * The openCV type
     */
    private final int type;
}