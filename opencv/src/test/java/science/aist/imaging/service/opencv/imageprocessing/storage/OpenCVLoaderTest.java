/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.storage;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

/**
 * @author Christoph Praschl
 */
public class OpenCVLoaderTest extends OpenCVTest {
    /**
     * Tests ImageWrapper.loadImage function
     */
    @Test
    void testLoadGreyscale() {
        // given
        InputStream is = OpenCVLoaderTest.class.getResourceAsStream("/logo/original.tif");
        OpenCVLoader loader = new OpenCVLoader();

        // when
        loader.setColored(false);
        ImageWrapper<Mat> wrapper = loader.apply(is);

        // then
        Assert.assertEquals(wrapper.getImage().channels(), 1);
    }

    /**
     * Tests ImageWrapper.loadImage function
     */
    @Test
    void testLoadColored() {
        // given
        InputStream is = OpenCVLoaderTest.class.getResourceAsStream("/logo/original.tif");
        OpenCVLoader loader = new OpenCVLoader();

        // when
        loader.setColored(true);
        ImageWrapper<Mat> wrapper = loader.apply(is);

        // then
        Assert.assertEquals(wrapper.getImage().channels(), 3);
    }

}
