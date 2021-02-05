/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVImageCompareFunctionTest extends OpenCVTest {
    /**
     * tests OpenCVImageCompareFunction.apply function
     */
    @Test
    void testCompare1() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/logo/original.tif");
        OpenCVImageCompareFunction compareFunction = new OpenCVImageCompareFunction();

        // when
        boolean res = compareFunction.test(img, img);

        // then
        Assert.assertTrue(res);
    }

    /**
     * tests OpenCVImageCompareFunction.apply function
     */
    @Test
    void testCompare2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/logo/xTranslated.tif");
        OpenCVImageCompareFunction compareFunction = new OpenCVImageCompareFunction();

        // when
        boolean res = compareFunction.test(img1, img2);

        // then
        Assert.assertFalse(res);
    }
}
