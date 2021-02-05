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
public class OpenCVResizeFunctionTest extends OpenCVTest {

    /**
     * Test OpenCVResizeFunction.apply
     */
    @Test
    void testResize() {
        // given
        // Load the image to resize
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        // Load a compare image
        ImageWrapper<Mat> imageResized = loadImageFromClassPath("/logo/resized.tif");

        OpenCVResizeFunction f = new OpenCVResizeFunction();
        f.setWidth(100);
        f.setHeight(100);

        // when
        // Resize the image
        ImageWrapper<Mat> newImg = f.apply(wrapper);

        // then
        // check for the new width
        Assert.assertEquals(newImg.getImage().width(), 100);
        // check for the new height
        Assert.assertEquals(newImg.getImage().height(), 100);
        // check if the image is the same as the one to compare with
        Assert.assertTrue(compareFunction.test(imageResized, newImg));
    }

}
