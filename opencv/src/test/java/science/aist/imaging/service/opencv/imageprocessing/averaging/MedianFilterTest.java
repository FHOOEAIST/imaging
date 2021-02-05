/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.averaging;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Christoph Praschl
 */
public class MedianFilterTest extends OpenCVTest {
    @Test
    public void testApply() {
        // given
        ImageWrapper<Mat> compareImage = loadImageFromClassPath("/backgroundsubtraction/1/backgroundMedian.tif");
        List<ImageWrapper<Mat>> images = IntStream.range(0, 4)
                .mapToObj(operand ->
                        loadImageFromClassPath("/backgroundsubtraction/1/image_" + operand + ".tif")
                )
                .map(openCVImageWrapper -> (ImageWrapper<Mat>) openCVImageWrapper)
                .collect(Collectors.toList());

        AbstractAveragingFilter abstractAveragingFilter = new MedianFilter();

        // when
        ImageWrapper<Mat> apply = abstractAveragingFilter.apply(images);

        // then
        Assert.assertTrue(compareFunction.test(apply, compareImage));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testApplyFail() {
        // given
        AbstractAveragingFilter abstractAveragingFilter = new MedianFilter();

        // when
        abstractAveragingFilter.apply(new ArrayList<>());

        // then

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testApplyFail2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/backgroundsubtraction/1/backgroundMedian.tif");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/logo/canny.tif");
        AbstractAveragingFilter abstractAveragingFilter = new MedianFilter();

        // when
        abstractAveragingFilter.apply(Arrays.asList(img1, img2));

        // then
    }
}