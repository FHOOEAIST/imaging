/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.lowpassfilter;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVGaussFilterFunctionTest extends OpenCVTest {
    /**
     * test ImageUtil.gaussianBlur function
     */
    @Test
    void testGaussianBlur() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> logoGaussianBlurComp = loadImageFromClassPath("/logo/gaussianBlur.tif");

        OpenCVGaussFilterFunction f = new OpenCVGaussFilterFunction();
        f.setKernelHeight(3);
        f.setKernelWidth(3);
        f.setSigmaX(3);
        f.setSigmaY(3);

        // when
        ImageWrapper<Mat> res = f.apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(res, logoGaussianBlurComp));
    }
}
