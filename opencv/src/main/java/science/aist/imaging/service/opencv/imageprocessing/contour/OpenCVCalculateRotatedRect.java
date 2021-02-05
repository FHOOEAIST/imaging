/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RotatedRectangleWrapper;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVRotatedRectangleWrapper;
import lombok.Cleanup;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>This class calculates the minimal rotated rectangle.</p>
 * <p>Input: List of points</p>
 * <p>Output: Corner points of the rotated rectangle</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class OpenCVCalculateRotatedRect implements Function<Collection<Point2Wrapper<Point>>, RotatedRectangleWrapper<RotatedRect, Point>> {

    @Override
    public RotatedRectangleWrapper<RotatedRect, Point> apply(Collection<Point2Wrapper<Point>> point2Wrappers) {
        @Cleanup("release") MatOfPoint2f input = new MatOfPoint2f();
        // Create the mat of points from the input points
        input.fromList(point2Wrappers.stream().map(Point2Wrapper::getPoint).collect(Collectors.toList()));

        // Calculate the rotated rectangle
        return new OpenCVRotatedRectangleWrapper(Imgproc.minAreaRect(input));
    }


}
