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
 * <p>interpolation method types </p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVInterpolationType {
    INTER_LINEAR(Imgproc.INTER_LINEAR),
    INTER_NEAREST(Imgproc.INTER_NEAREST);

    /**
     * OpenCV interpolationType
     */
    private final int interpolationType;
}
