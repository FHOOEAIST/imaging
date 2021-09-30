/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.draw.polygon;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.domain.OpenCVLineType;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVDrawPolygonTest extends OpenCVTest {
    /**
     * Tests OpenCVDrawPolygon.accept function
     */
    @Test
    void testDrawPolygonOnImage() {
        // given
        ImageWrapper<Mat> ref = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/drawpolygonTest.tif");

        JavaPolygon2D poly = new JavaPolygon2D(new JavaPoint2D(1, 3), new JavaPoint2D(100, 50), new JavaPoint2D(750, 90), new JavaPoint2D(0, 100));

        OpenCVDrawPolygon consumer = new OpenCVDrawPolygon();
        consumer.setThickness(3);
        consumer.setColor(RGBColor.RED);
        consumer.setLineType(OpenCVLineType.LINE_4);
        consumer.setShift(2);
        consumer.setCircleRadius(2);

        // when
        consumer.accept(ref, poly);

        // then
        Assert.assertTrue(compareFunction.test(ref, compare));
    }
}