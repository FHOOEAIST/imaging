/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVCropFunctionTest extends OpenCVTest {
    /**
     * Test ImageUtil.crop(img, point, x, y)
     */
    @Test
    void testCrop1() {
        // given
        // Load to image to crop
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        // load a reference image, to which it can be compared
        ImageWrapper<Mat> wrapperCrop = loadImageFromClassPath("/logo/crop.tif");

        OpenCVCropFunction f = new OpenCVCropFunction();
        f.setFrom(10, 10);
        f.setTo(30, 110);

        // when
        // crop the image
        ImageWrapper<Mat> newImg = f.apply(wrapper);

        // then
        // Check if the new width fits.
        Assert.assertEquals(newImg.getImage().width(), 20);
        // Check if the new height fits.
        Assert.assertEquals(newImg.getImage().height(), 100);
        // Check if the image is the same as the one to compare with
        Assert.assertTrue(compareFunction.test(wrapperCrop, newImg));
    }


    /**
     * Test ImageUtil.crop(img, point, point) to throw exception
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void testCrop2() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");

        OpenCVCropFunction f = new OpenCVCropFunction();
        f.setFrom(-1, -1);
        f.setTo(5, 5);

        // when
        f.apply(wrapper);

        // then
        // should not be reach, crop should throw an exception. therefore assert true == false (if this is reach, the assertion fails, because no exception was thrown
        Assert.fail();
    }
}
