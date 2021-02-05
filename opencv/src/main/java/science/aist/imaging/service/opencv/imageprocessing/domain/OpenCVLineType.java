/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Type of a line</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVLineType {
    /**
     * LINE_8 (or omitted) - 8-connected line.
     */
    LINE_8(Imgproc.LINE_8),
    /**
     * LINE_4 - 4-connected line.
     */
    LINE_4(Imgproc.LINE_4),
    /**
     * LINE_AA - antialiased line.
     */
    LINE_AA(Imgproc.LINE_AA);

    /**
     * OpenCV label Type
     */
    private final int lineType;
}
