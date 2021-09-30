/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link OpenCVImageWrapper}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVImageWrapperTest extends OpenCVTest {
    @Test
    void testEqualsSuccess() {
        // given
        OpenCVImageWrapper openCVImageWrapper = loadImageFromClassPath("/logo/original.tif");
        OpenCVImageWrapper openCVImageWrapper2 = loadImageFromClassPath("/logo/original.tif");

        // when
        boolean equals = openCVImageWrapper.equals(openCVImageWrapper2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    void testEqualsFail() {
        // given
        OpenCVImageWrapper openCVImageWrapper = loadImageFromClassPath("/logo/original.tif");
        OpenCVImageWrapper openCVImageWrapper2 = loadImageFromClassPath("/logo/canny.tif");

        // when
        boolean equals = openCVImageWrapper.equals(openCVImageWrapper2);

        // then
        Assert.assertFalse(equals);
    }

    @Test
    void testSetValue() {
        // given
        ImageFactory<Mat> matProvider = ImageFactoryFactory.getImageFactory(Mat.class);
        ImageWrapper<Mat> image = matProvider.getImage(10, 10, ChannelType.RGB);

        // when
        image.setValue(3, 7, 1, 42);

        // then
        Assert.assertEquals(image.getImage().get(7, 3)[1], 42.0);
    }

    @Test
    void testGetValue() {
        // given
        ImageWrapper<Mat> image = ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(10, 10, CvType.CV_8UC(3), ChannelType.RGB);
        image.getImage().put(7, 3, 0, 42.0, 0);

        // when
        double val = image.getValue(3, 7, 1);

        // then
        Assert.assertEquals(val, 42.0);
    }
}
