/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVMorphologicalSkeletonFunctionTest extends OpenCVTest {
    /**
     * tests OpenCVMorphologicalSkeletonFunction.apply function
     */
    @Test
    void testSkeleton() {
        // given
        ImageWrapper<Mat> wrapper = ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(loadImageFromClassPath("/passport/r_segmented.bmp", false).getImage(), ChannelType.BINARY);
        ImageWrapper<Mat> skeleton = loadImageFromClassPath("/passport/r_skeleton.bmp", false);

        // when
        ImageWrapper<Mat> res = new OpenCVMorphologicalSkeletonFunction().apply(wrapper);

        // then
        Assert.assertTrue(compareFunction.test(skeleton, res));
    }
}
