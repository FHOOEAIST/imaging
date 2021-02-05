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
public class OpenCVInvertFunctionTest extends OpenCVTest {
    /**
     * tests OpenCVInvertFunction.apply function
     */
    @Test
    void testInvert() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif", true);
        ImageWrapper<Mat> comp = loadImageFromClassPath("/logo/inverted.tif", true);

        // when
        ImageWrapper<Mat> res = new OpenCVInvertFunction().apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(comp, res));
    }
}
