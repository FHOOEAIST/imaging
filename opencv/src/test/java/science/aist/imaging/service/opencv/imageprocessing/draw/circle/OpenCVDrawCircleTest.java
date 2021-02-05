/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.circle;


import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVDrawCircleTest extends OpenCVTest {
    /**
     * Tests OpenCVDrawCircle.accept function
     */
    @Test
    void testDrawCircleOnImage() {
        // given
        ImageWrapper<Mat> ref = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/circleTest.tif");

        OpenCVDrawCircle consumer = new OpenCVDrawCircle();
        consumer.setRadius(8);
        consumer.setThickness(3);
        consumer.setColor(RGBColor.GREEN);

        // when
        consumer.accept(ref, new JavaPoint2D(ref.getWidth() / 2.0, ref.getHeight() / 2.0));

        // then
        Assert.assertTrue(compareFunction.test(ref, compare));
    }
}
