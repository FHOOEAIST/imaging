/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.RotatedRectangleWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link OpenCVRotatedRectangleWrapper}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVRotatedRectangleWrapperTest extends OpenCVTest {
    @Test
    public void testCreate1() {
        // given
        RotatedRect rr = new RotatedRect(new Point(1, 2), new Size(3, 4), 5);

        // when
        OpenCVRotatedRectangleWrapper openCVRotatedRectangleWrapper = new OpenCVRotatedRectangleWrapper(rr);

        // then
        Assert.assertEquals(openCVRotatedRectangleWrapper.getHeight(), 4.0, 0.001);
        Assert.assertEquals(openCVRotatedRectangleWrapper.getWidth(), 3.0, 0.001);
        Assert.assertEquals(openCVRotatedRectangleWrapper.getCenterPoint().getX(), 1.0, 0.001);
        Assert.assertEquals(openCVRotatedRectangleWrapper.getCenterPoint().getY(), 2.0, 0.001);
        Assert.assertEquals(openCVRotatedRectangleWrapper.getRotation(), Math.toRadians(5), 0.001);
    }

    @Test
    public void testCreate2() {
        // given

        // when
        RotatedRectangleWrapper<RotatedRect, Point> rotatedRectPointRotatedRectangleWrapper = OpenCVRotatedRectangleWrapper.create(new OpenCVPoint2Wrapper(1, 2), 3, 4, Math.toRadians(5));

        // then
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getHeight(), 4.0, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getWidth(), 3.0, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getCenterPoint().getX(), 1.0, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getCenterPoint().getY(), 2.0, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getRotation(), Math.toRadians(5), 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getPoints().size(), 4);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getBottomLeftPoint().getX(), -0.668, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getBottomLeftPoint().getY(), 3.861, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getTopLeftPoint().getX(), -0.319, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getTopLeftPoint().getY(), -0.123, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getTopRightPoint().getX(), 2.668, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getTopRightPoint().getY(), 0.138, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getBottomRightPoint().getX(), 2.319, 0.001);
        Assert.assertEquals(rotatedRectPointRotatedRectangleWrapper.getBottomRightPoint().getY(), 4.123, 0.001);
    }
}
