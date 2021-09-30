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

/**
 * <p>The mask Size of the OpenCV OpenCVDistanceMap</p>
 * <p>OpenCV: Size of the distance transform mask. It can be 3, 5, or CV_DIST_MASK_PRECISE (the latter option is only supported by the first function).
 * In case of the CV_DIST_L1 or CV_DIST_C distance type, the parameter is forced to 3 because a 3x3 mask gives the same result as 5x5 or any larger aperture.</p>
 * <p><a href="https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html">https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html</a></p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVDistanceMask {
    /**
     * CV_DIST_MASK_3
     */
    MASK_3(Imgproc.CV_DIST_MASK_3),
    /**
     * CV_DIST_MASK_5
     */
    MASK_5(Imgproc.CV_DIST_MASK_5),
    /**
     * CV_DIST_MASK_PRECISE
     */
    MASK_PRECISE(Imgproc.CV_DIST_MASK_PRECISE);

    /**
     * The OpenCV mask Size
     */
    private int maskSize;
}
