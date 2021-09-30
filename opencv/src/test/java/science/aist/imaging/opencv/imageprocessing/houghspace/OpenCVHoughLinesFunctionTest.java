/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.houghspace;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.draw.line.OpenCVDrawLine;

import java.util.Collection;

/**
 * @author Christoph Praschl
 */
public class OpenCVHoughLinesFunctionTest extends OpenCVTest {
    /**
     * Tests OpenCVHoughLinesFunction.apply function
     */
    @Test
    void testHoughDetection() {
        // given
        // Load CannyEdgeDetector
        // Load the drivingLicense
        ImageWrapper<Mat> source = loadImageFromClassPath("/drivingLicenses/drivingLicense.tif");
        // Load the compare image
        ImageWrapper<Mat> hough = loadImageFromClassPath("/drivingLicenses/drivingLicenseHough.tif");
        // Calculate the edges using Canny.
        cannyEdge.setThresholdHigh(255);
        cannyEdge.setL2Gradient(true);
        ImageWrapper<Mat> edges = cannyEdge.apply(source);

        OpenCVHoughLinesFunction f = new OpenCVHoughLinesFunction();
        f.setMaxLineGap(10);
        f.setMinLineLength(150);
        f.setThreshold(80);
        f.setTheta(Math.PI / 180);
        f.setRho(1);

        // when
        // Find the strongest lines using Hough lines.
        Collection<LineWrapper<Point>> lines = f.apply(edges);

        // then
        // apply the lines to the Image
        cannyEdge.setThresholdHigh(200);
        cannyEdge.setL2Gradient(false);
        OpenCVDrawLine consumer = new OpenCVDrawLine();
        consumer.setColor(RGBColor.RED);
        consumer.setThickness(3);
        lines.forEach(line -> consumer.accept(source, line));
        Assert.assertTrue(compareFunction.test(hough, source));
    }
}
