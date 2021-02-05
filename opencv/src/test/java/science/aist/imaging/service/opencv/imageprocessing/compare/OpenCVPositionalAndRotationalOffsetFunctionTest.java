/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVPositionalAndRotationalOffsetFunctionTest extends OpenCVTest {

    /**
     * ImageCompare.calculatePositionalAndRotationalOffset
     */
    @Test
    void testCalculatePositionalAndRotationalOffset1() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> translatedImg = loadImageFromClassPath("/logo/translated4.tif");

        OpenCVPositionalAndRotationalOffsetFunction prFunction = new OpenCVPositionalAndRotationalOffsetFunction();
        prFunction.setOptimizer(optimizer);
        prFunction.setPositionalRadius(30);
        prFunction.setRotationalRadius(2);
        prFunction.setStepsOfInterestingPoints(1);

        // when
        RotationOffset o = prFunction.apply(img1, translatedImg);
        log.debug(o.toString());

        // then
        Assert.assertEquals(o.getRotationalOffset(), 0.0);
        double xOffset = Math.abs(o.getXOffset() - 20);
        Assert.assertTrue(xOffset < 2.0);
        double yOffset = Math.abs(o.getYOffset() + 10);
        Assert.assertTrue(yOffset < 2.0);
    }

    /**
     * ImageCompare.calculatePositionalAndRotationalOffset
     */
    @Test
    void testCalculatePositionalAndRotationalOffset2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> translatedImg = loadImageFromClassPath("/logo/translated4.tif");

        OpenCVPositionalAndRotationalOffsetFunction prFunction = new OpenCVPositionalAndRotationalOffsetFunction();
        prFunction.setOptimizer(optimizer);

        // when
        RotationOffset o = prFunction.apply(img1, translatedImg);
        log.debug(o.toString());

        // then
        Assert.assertEquals(o.getRotationalOffset(), 0.0);
        double xOffset = Math.abs(o.getXOffset() - 20);
        Assert.assertTrue(xOffset < 2.0);
        double yOffset = Math.abs(o.getYOffset() + 10);
        Assert.assertTrue(yOffset < 2.0);
    }

}
