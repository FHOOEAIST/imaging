/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.line;


import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVLineType;

/**
 * @author Christoph Praschl
 */
public class OpenCVDrawLineTest extends OpenCVTest {
    /**
     * Tests OpenCVDrawLine.accept function
     */
    @Test
    void testDrawLineOnImage() {
        // given
        ImageWrapper<Mat> ref = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/linedrawTest.tif");

        OpenCVDrawLine consumer = new OpenCVDrawLine();
        consumer.setThickness(3);
        consumer.setColor(RGBColor.RED);
        consumer.setLineType(OpenCVLineType.LINE_4);
        consumer.setShift(2);

        // when
        consumer.accept(ref, new JavaLine2D(new JavaPoint2D(0, 0), new JavaPoint2D(ref.getWidth(), ref.getHeight())));

        // then
        Assert.assertTrue(compareFunction.test(ref, compare));
    }
}
