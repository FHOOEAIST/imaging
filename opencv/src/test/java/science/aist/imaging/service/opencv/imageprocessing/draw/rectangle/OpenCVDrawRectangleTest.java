/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.rectangle;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVDrawRectangleTest extends OpenCVTest {
    /**
     * Tests OpenCVDrawRectangle.accept function
     */
    @Test
    void testAddRectangle() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> logoRectangleComp = loadImageFromClassPath("/logo/rectangle.tif");

        OpenCVDrawRectangle consumer = new OpenCVDrawRectangle();
        consumer.setColor(RGBColor.RED);
        consumer.setThickness(3);

        // when
        consumer.accept(wrapper, new JavaRectangle2D(new JavaPoint2D(10, 10), new JavaPoint2D(60, 60)));

        // then
        Assert.assertTrue(compareFunction.test(wrapper, logoRectangleComp));
    }
}