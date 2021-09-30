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
import org.opencv.features2d.Features2d;

/**
 * <p>Enum which specifies the opencv output image creation</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVFeatures2d {
    /**
     * Output image matrix will be created (Mat::create), i.e. existing memory of output image may be reused. Two source image, matches and single keypoints will be drawn. For each keypoint only the center point will be drawn (without the circle around keypoint with keypoint size and orientation).
     */
    DEFAULT(0),

    /**
     * Output image matrix will not be created (Mat::create). Matches will be drawn on existing content of output image.
     */
    DRAW_OVER_OUTIMG(Features2d.DrawMatchesFlags_DRAW_OVER_OUTIMG),
    /**
     * Single keypoints will not be drawn.
     */
    NOT_DRAW_SINGLE_POINTS(Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS),
    /**
     * For each keypoint the circle around keypoint with keypoint size and orientation will be drawn.
     */
    DRAW_RICH_KEYPOINTS(Features2d.DrawMatchesFlags_DRAW_RICH_KEYPOINTS);

    /**
     * OpenCV label Type
     */
    private final int features2D;
}
