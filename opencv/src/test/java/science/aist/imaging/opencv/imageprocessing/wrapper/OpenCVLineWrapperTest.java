/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Testing OpenCVLineWrapper
 *
 * @author Andreas Pointner
 */
public class OpenCVLineWrapperTest extends OpenCVTest {
    /**
     * Test the int constructor of OpenCVLineWrapper
     */
    @Test
    void testConstructorInt() {
        // given
        int x1 = 10, x2 = 20, y1 = 10, y2 = 20;

        // when
        LineWrapper<Point> lineWrapper = new OpenCVLineWrapper(x1, y1, x2, y2);

        // then
        Assert.assertEquals(lineWrapper.getStartPoint().getX(), (double) x1);
        Assert.assertEquals(lineWrapper.getStartPoint().getY(), (double) y1);
        Assert.assertEquals(lineWrapper.getEndPoint().getX(), (double) x2);
        Assert.assertEquals(lineWrapper.getEndPoint().getY(), (double) y2);
    }
}
