/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenCVBackgroundSubtractionTest extends OpenCVTest {

    private int i = 0;

    @Test
    public void testApply1() {
        ImageWrapper<Mat> compareImage = loadImageFromClassPath("/backgroundsubtraction/1/objectMask.tif", false);
        ImageWrapper<Mat> model = loadImageFromClassPath("/backgroundsubtraction/1/backgroundMedian.tif");
        ImageWrapper<Mat> img = loadImageFromClassPath("/backgroundsubtraction/1/background-0-next.tif");
        test(compareImage, model, img);
    }

    @Test
    public void testApply2() {
        ImageWrapper<Mat> compareImage = loadImageFromClassPath("/backgroundsubtraction/2/compare.tif", false);
        ImageWrapper<Mat> model = loadImageFromClassPath("/backgroundsubtraction/2/model.tif");
        ImageWrapper<Mat> img = loadImageFromClassPath("/backgroundsubtraction/2/image.tif");

        test(compareImage, model, img);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testApplyFail() {
        // given
        ImageWrapper<Mat> model = loadImageFromClassPath("/backgroundsubtraction/2/model.tif");
        ImageWrapper<Mat> img = loadImageFromClassPath("/backgroundsubtraction/2/image.tif", false);
        OpenCVBackgroundSubtraction openCVBackgroundSubtraction = new OpenCVBackgroundSubtraction(model);

        // when
        ImageWrapper<Mat> apply = openCVBackgroundSubtraction.apply(img);

        // then
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testApplyFail2() {
        // given
        ImageWrapper<Mat> model = null;
        ImageWrapper<Mat> img = loadImageFromClassPath("/backgroundsubtraction/2/image.tif");
        OpenCVBackgroundSubtraction openCVBackgroundSubtraction = new OpenCVBackgroundSubtraction(model);

        // when
        ImageWrapper<Mat> apply = openCVBackgroundSubtraction.apply(img);

        // then
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testApplyFail3() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/backgroundsubtraction/2/image.tif");
        OpenCVBackgroundSubtraction openCVBackgroundSubtraction = new OpenCVBackgroundSubtraction();

        // when
        ImageWrapper<Mat> apply = openCVBackgroundSubtraction.apply(img);

        // then
    }

    @Test
    public void testSetModel() {
        // given
        ImageWrapper<Mat> model = loadImageFromClassPath("/backgroundsubtraction/2/model.tif");
        ImageWrapper<Mat> img = loadImageFromClassPath("/backgroundsubtraction/2/image.tif");
        OpenCVBackgroundSubtraction openCVBackgroundSubtraction = new OpenCVBackgroundSubtraction();

        // when
        openCVBackgroundSubtraction.setModel(model);

        // then
        Assert.assertNotNull(openCVBackgroundSubtraction.apply(img));
    }

    private void test(ImageWrapper<Mat> compareImage, ImageWrapper<Mat> model, ImageWrapper<Mat> img) {
        // given + parameters :D
        OpenCVBackgroundSubtraction openCVBackgroundSubtraction = new OpenCVBackgroundSubtraction(model);

        // when
        ImageWrapper<Mat> apply = openCVBackgroundSubtraction.apply(img);

        // then
        Assert.assertTrue(compareFunction.test(apply, compareImage));
    }
}
