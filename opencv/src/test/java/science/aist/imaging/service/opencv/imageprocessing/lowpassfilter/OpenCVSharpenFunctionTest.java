/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.lowpassfilter;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVSharpenFunctionTest extends OpenCVTest {
    /**
     * test ImageUtil.sharpenImage function
     */
    @Test
    void testSharpenImage() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> logoSharpenComp = loadImageFromClassPath("/logo/sharpen.tif");

        // when
        ImageWrapper<Mat> res = new OpenCVSharpenFunction().apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(res, logoSharpenComp));
    }
}
