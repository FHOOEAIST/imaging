/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.morphology;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.opencv.imageprocessing.compare.OpenCVImageCompareFunction;

/**
 * <p>Test class for {@link ThinningFunction}</p>
 *
 * @author Andreas Pointner
 */
public class ThinningFunctionTest extends OpenCVTest {
    @Test
    public void testApply() {
        // given
        ImageWrapper<Mat> imageWrapper = loadImageFromClassPath("/Test.png", false);
        // This is already greyscale so we are not allowed to apply greyscale another time, therefore color=true ....
        ImageWrapper<Mat> compare = loadImageFromClassPath("/TestSkeleton.png", false);
        ThinningFunction function = new ThinningFunction();

        // when
        ImageWrapper<Mat> result = function.apply(imageWrapper);

        // then
        OpenCVImageCompareFunction compareFunction = new OpenCVImageCompareFunction();
        Assert.assertTrue(compareFunction.test(result, compare));
    }
}