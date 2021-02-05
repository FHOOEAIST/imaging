/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andreas Pointner
 */
public class OpenCVPoint2WrapperTest extends OpenCVTest {
    /**
     * test OpenCVPoint2Wrapper constructor with x,y int
     */
    @Test
    void testConstructorInt() {
        // given
        int x = 100;
        int y = 150;

        // when
        Point2Wrapper<Point> wrapper = new OpenCVPoint2Wrapper(x, y);

        // then
        Assert.assertEquals((int) wrapper.getPoint().x, x);
        Assert.assertEquals((int) wrapper.getPoint().y, y);
    }

    /**
     * test OpenCVPoint2Wrapper constructor with x,y double
     */
    @Test
    void testConstructorDouble() {
        // given
        double x = 100;
        double y = 150;

        // when
        Point2Wrapper<Point> wrapper = new OpenCVPoint2Wrapper(x, y);

        // then
        Assert.assertEquals(wrapper.getPoint().x, x);
        Assert.assertEquals(wrapper.getPoint().y, y);
    }
}

