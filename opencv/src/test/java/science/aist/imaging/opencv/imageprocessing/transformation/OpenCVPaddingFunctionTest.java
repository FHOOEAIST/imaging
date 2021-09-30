/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVPaddingFunctionTest extends OpenCVTest {
    /**
     * Tests OpenCVPaddingFunction.apply function
     * Padding test with padding values 10, 5, 2, 3 with color blue
     */
    @Test
    void testPadding1() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/padding1.tif");

        OpenCVPaddingFunction f = new OpenCVPaddingFunction();
        f.setColor(new RGBColor(233, 150, 25));
        f.setPaddings(10, 5, 2, 3);

        // when
        ImageWrapper<Mat> paddedWrapper = f.apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(paddedWrapper, compare));
    }

    /**
     * Tests OpenCVPaddingFunction.apply function
     * Padding test with padding values 10, 50, 10, 50 with color white
     */
    @Test
    void testPadding2() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/padding2.tif");

        OpenCVPaddingFunction f = new OpenCVPaddingFunction();
        f.setColor(RGBColor.WHITE);
        f.setPaddingTop(10);
        f.setPaddingBottom(10);
        f.setPaddingLeft(50);
        f.setPaddingRight(50);

        // when
        ImageWrapper<Mat> paddedWrapper = f.apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(paddedWrapper, compare));
    }

    /**
     * Tests OpenCVPaddingFunction.apply function
     * Padding test with padding values 20, 20, 20, 20 with color black
     */
    @Test
    void testPaddingTest3() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/padding3.tif");

        OpenCVPaddingFunction f = new OpenCVPaddingFunction();
        f.setColor(RGBColor.BLACK);
        f.setPaddings(20, 20, 20, 20);

        // when
        ImageWrapper<Mat> paddedWrapper = f.apply(wrapper);


        // then
        Assert.assertTrue(compareFunction.test(paddedWrapper, compare));
    }

    /**
     * Tests OpenCVPaddingFunction.apply function
     * Padding test with padding values 10, 5, 2, 3 with color black
     */
    @Test
    void testPadding4() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/padding4.tif");

        OpenCVPaddingFunction f = new OpenCVPaddingFunction();
        f.setPaddings(10, 5, 2, 3);

        // when
        ImageWrapper<Mat> paddedWrapper = f.apply(wrapper);


        // then
        Assert.assertTrue(compareFunction.test(paddedWrapper, compare));
    }

    /**
     * Tests OpenCVPaddingFunction.apply function
     * Padding test with padding values 10, 50, 10, 50
     */
    @Test
    void testPadding5() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/padding5.tif");

        OpenCVPaddingFunction f = new OpenCVPaddingFunction();
        f.setPaddingTop(10);
        f.setPaddingBottom(10);
        f.setPaddingLeft(50);
        f.setPaddingRight(50);

        // when
        ImageWrapper<Mat> paddedWrapper = f.apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(paddedWrapper, compare));
    }

    /**
     * Tests OpenCVPaddingFunction.apply function
     * Padding test with padding values 20, 20, 20, 20
     */
    @Test
    void testPadding6() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/padding3.tif");

        OpenCVPaddingFunction f = new OpenCVPaddingFunction();
        f.setPaddings(20, 20, 20, 20);

        // when
        ImageWrapper<Mat> paddedWrapper = f.apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(paddedWrapper, compare));
    }
}
