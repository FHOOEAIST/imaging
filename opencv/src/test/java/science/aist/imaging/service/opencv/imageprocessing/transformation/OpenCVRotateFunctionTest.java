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
public class OpenCVRotateFunctionTest extends OpenCVTest {
    /**
     * Tests ImageUtil.rotate function
     */
    @Test
    void testRotate() {
        // given
        // Load image
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        // load pre-rotated image for comparison
        ImageWrapper<Mat> compareWrapper = loadImageFromClassPath("/logo/rotated.tif");

        OpenCVRotateFunction f = new OpenCVRotateFunction();
        f.setRotation(90);

        // when
        // rotate image
        ImageWrapper<Mat> rotateWrapper = f.apply(wrapper);

        // then
        // compare the rotated and the compare-image
        Assert.assertTrue(compareFunction.test(rotateWrapper, compareWrapper));
    }
}
