/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.edgedetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andreas Pointner
 */
public class OpenCVImageEdgeSobelTest extends OpenCVTest {
    /**
     * Test sobel Edge detection
     */
    @Test
    void testDetectEdgesSobel() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> sobel = loadImageFromClassPath("/logo/sobel.tif", false);

        // when
        ImageWrapper<Mat> res = sobelEdge.apply(img);

        // then
        Assert.assertTrue(compareFunction.test(res, sobel));
    }
}
