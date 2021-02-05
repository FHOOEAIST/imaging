/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.conversion;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVBGR2GrayscaleFunctionTest extends OpenCVTest {
    /**
     * Tests OpenCVBGR2GrayscaleFunction.apply function
     */
    @Test
    void testConvertRGB2Greyscale() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        // check if loaded image is an RGB Image
        Assert.assertEquals(wrapper.getImage().channels(), 3);

        // when
        ImageWrapper<Mat> greyWrapper = new OpenCVBGR2GrayscaleFunction().apply(wrapper);

        // then
        // check if converted image has only one channel
        Assert.assertEquals(greyWrapper.getImage().channels(), 1);
    }
}
