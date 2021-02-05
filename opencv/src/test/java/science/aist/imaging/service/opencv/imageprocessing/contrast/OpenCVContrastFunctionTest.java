/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contrast;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVContrastFunctionTest extends OpenCVTest {
    /**
     * test OpenCVContrastFunction.apply function
     */
    @Test
    void testChangeContrast() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> logoIncreasedContrastComp = loadImageFromClassPath("/logo/increasedContrast.tif");

        OpenCVContrastFunction f = new OpenCVContrastFunction();
        f.setFactor(1.5);

        // when
        ImageWrapper<Mat> res = f.apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(res, logoIncreasedContrastComp));
    }
}
