/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.optimization;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.optimization.Optimizer;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testclass for the opencv Implementation of the Optimizer interface.
 *
 * @author Christoph Praschl
 */
public class OpenCVOptimizerTest extends OpenCVTest {
    @Autowired
    private Optimizer<Mat> optimizer;

    /**
     * Optimizer.optimize
     */
    @Test
    void testOptimize1() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> translatedImg = loadImageFromClassPath("/logo/translated4.tif");

        // when
        RotationOffset o = optimizer.optimize(img1, translatedImg);

        // then
        Assert.assertEquals(o.getRotationalOffset(), 0.0);
        double xOffset = Math.abs(o.getXOffset() - 20);
        Assert.assertTrue(xOffset < 2.0);
        double yOffset = Math.abs(o.getYOffset() + 10);
        Assert.assertTrue(yOffset < 2.0);
    }

    /**
     * Optimizer.optimize
     */
    @Test
    void testOptimize2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> translatedImg = loadImageFromClassPath("/logo/translated4.tif");

        // when
        RotationOffset o = optimizer.optimize(img1, translatedImg, 3, 25, 1, 3, 0.75, RGBColor.WHITE);
        // then
        Assert.assertEquals(o.getRotationalOffset(), 0.0);
        double xOffset = Math.abs(o.getXOffset() - 20);
        Assert.assertTrue(xOffset < 2.0);
        double yOffset = Math.abs(o.getYOffset() + 10);
        Assert.assertTrue(yOffset < 2.0);
    }
}
