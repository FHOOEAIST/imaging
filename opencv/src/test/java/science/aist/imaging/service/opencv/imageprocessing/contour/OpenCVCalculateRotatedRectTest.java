/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.wrapper.RotatedRectangleWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * <p>Tests {@link OpenCVCalculateRotatedRect}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVCalculateRotatedRectTest extends OpenCVTest {
    @Test
    public void testCalculateRotatedRect() {
        // given
        OpenCVCalculateRotatedRect calculateRotatedRect = new OpenCVCalculateRotatedRect();

        // when
        RotatedRectangleWrapper<RotatedRect, Point> rotatedRectangle = calculateRotatedRect.apply(Arrays.asList(
                new OpenCVPoint2Wrapper(1, 3),
                new OpenCVPoint2Wrapper(2, 2),
                new OpenCVPoint2Wrapper(1, 2),
                new OpenCVPoint2Wrapper(3, 2),
                new OpenCVPoint2Wrapper(2, 3),
                new OpenCVPoint2Wrapper(2, 4)
        ));

        // then
        Assert.assertEquals(rotatedRectangle.getCenterPoint().getX(), 1.7, 0.001);
        Assert.assertEquals(rotatedRectangle.getCenterPoint().getY(), 2.6, 0.001);
        Assert.assertEquals(rotatedRectangle.getWidth(), 1.788, 0.001);
        Assert.assertEquals(rotatedRectangle.getHeight(), 2.236, 0.001);
        Assert.assertEquals(Math.toDegrees(rotatedRectangle.getRotation()), 26.56505, 0.001);
    }
}
