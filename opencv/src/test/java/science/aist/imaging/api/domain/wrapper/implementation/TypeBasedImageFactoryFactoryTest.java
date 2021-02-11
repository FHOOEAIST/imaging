/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;

import java.awt.image.BufferedImage;

/**
 * <p>Test class for {@link TypeBasedImageFactoryFactory}</p>
 *
 * @author Andreas Pointner
 */

public class TypeBasedImageFactoryFactoryTest {

    @Test
    public void testGetImageFactoryMat() {
        // given

        // when
        ImageFactory<Mat> imageFactory = TypeBasedImageFactoryFactory.getImageFactory(Mat.class);

        // then
        Assert.assertEquals(imageFactory.getClass(), OpenCVFactory.class);
    }

    @Test
    public void testGetImageFactoryDouble() {
        // given

        // when
        ImageFactory<double[][][]> imageFactory = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class);

        // then
        Assert.assertEquals(imageFactory.getClass(), Image8ByteFactory.class);
    }

    @Test
    public void testGetImageFactoryBuffered() {
        // given

        // when
        ImageFactory<BufferedImage> imageFactory = TypeBasedImageFactoryFactory.getImageFactory(BufferedImage.class);

        // then
        Assert.assertEquals(imageFactory.getClass(), BufferedImageFactory.class);
    }
}