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
 * <p>OpenCV Threshold type</p>
 * <a href="https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html?highlight=threshold#threshold">OpenCV ThresholdFunction types</a>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVThresholdType {
    BINARY(Imgproc.THRESH_BINARY),
    BINARY_INV(Imgproc.THRESH_BINARY_INV),
    OTSU(Imgproc.THRESH_OTSU),
    MASK(Imgproc.THRESH_MASK),
    TOZERO(Imgproc.THRESH_TOZERO),
    TOZERO_INV(Imgproc.THRESH_TOZERO_INV),
    TRIANLGE(Imgproc.THRESH_TRIANGLE),
    TRUNC(Imgproc.THRESH_TRUNC);

    /**
     * OpenCV thresh Type
     */
    private final int type;
}
