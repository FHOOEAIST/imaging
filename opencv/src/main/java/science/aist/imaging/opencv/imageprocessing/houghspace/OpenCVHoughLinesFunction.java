/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.houghspace;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVLineWrapper;
import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * <p>Finds line segments in a binary image (8-bit, single-channel binary source image.) using the probabilistic Hough transform.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVHoughLinesFunction implements Function<ImageWrapper<Mat>, Collection<LineWrapper<Point>>> {
    /**
     * Distance resolution of the accumulator in pixels.
     */
    private double rho;

    /**
     * Angle resolution of the accumulator in radians.
     */
    private double theta;

    /**
     * Accumulator threshold parameter. Only those lines are returned that get enough votes ( > ThresholdFunction ).
     */
    private int threshold;

    /**
     * Minimum line length. Line segments shorter than that are rejected.
     */
    private double minLineLength;

    /**
     * Maximum allowed gap between points on the same line to link them.
     */
    private double maxLineGap;

    @Override
    public Collection<LineWrapper<Point>> apply(ImageWrapper<Mat> image) {
        @Cleanup("release") Mat lines = new Mat();

        Collection<LineWrapper<Point>> result = new ArrayList<>();
        Imgproc.HoughLinesP(image.getImage(), lines, rho, theta, threshold, minLineLength, maxLineGap);
        for (int i = 0; i < lines.rows(); i++) {
            double[] val = lines.get(i, 0);
            result.add(new OpenCVLineWrapper(val[0], val[1], val[2], val[3]));
        }
        return result;
    }
}
