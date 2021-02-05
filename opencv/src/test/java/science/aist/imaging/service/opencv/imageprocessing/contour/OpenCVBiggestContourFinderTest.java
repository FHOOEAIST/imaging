/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVBiggestContourFinderTest extends OpenCVTest {
    /**
     * tests OpenCVBiggestContourFinder.apply function
     */
    @Test
    void testGetBiggestContour() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/logoBinary.tif");

        // when
        RectangleWrapper<Rect, Point> jr = new OpenCVBiggestContourFinder().apply(wrapper);

        // then
        Assert.assertEquals(jr.getTopLeftPoint().getX(), 36.0);
        Assert.assertEquals(jr.getTopLeftPoint().getY(), 32.0);
        Assert.assertEquals(jr.getBottomRightPoint().getX(), 180.0);
        Assert.assertEquals(jr.getBottomRightPoint().getY(), 117.0);
    }
}
