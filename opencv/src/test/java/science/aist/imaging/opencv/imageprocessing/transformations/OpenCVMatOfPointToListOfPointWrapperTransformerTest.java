/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformations;

import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVMatOfPointToListOfPointWrapperTransformer;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Tests {@link OpenCVMatOfPointToListOfPointWrapperTransformer}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVMatOfPointToListOfPointWrapperTransformerTest extends OpenCVTest {

    @Test
    void testFrom() {
        // given
        OpenCVMatOfPointToListOfPointWrapperTransformer openCVMatOfPointToListOfPointWrapperTransformer = new OpenCVMatOfPointToListOfPointWrapperTransformer();
        MatOfPoint mop = new MatOfPoint(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 4)
        );

        // when
        List<Point2Wrapper<Point>> from = openCVMatOfPointToListOfPointWrapperTransformer.transformTo(mop);

        // then
        Assert.assertNotNull(from);
        Assert.assertEquals(from.size(), 3);
        Assert.assertEquals(from.get(0), new OpenCVPoint2Wrapper(1, 1));
        Assert.assertEquals(from.get(1), new OpenCVPoint2Wrapper(2, 2));
        Assert.assertEquals(from.get(2), new OpenCVPoint2Wrapper(3, 4));
    }

    @Test
    void testTo() {
        // given
        OpenCVMatOfPointToListOfPointWrapperTransformer openCVMatOfPointToListOfPointWrapperTransformer = new OpenCVMatOfPointToListOfPointWrapperTransformer();
        List<Point2Wrapper<Point>> lpp = Arrays.asList(
                new OpenCVPoint2Wrapper(1, 1),
                new OpenCVPoint2Wrapper(2, 2),
                new OpenCVPoint2Wrapper(3, 4)
        );

        // when
        MatOfPoint to = openCVMatOfPointToListOfPointWrapperTransformer.transformFrom(lpp);

        // then
        Assert.assertNotNull(to);
        Assert.assertEquals(to.rows(), 3);
    }
}
