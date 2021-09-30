/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVLineWrapper;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import org.opencv.core.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andreas Pointner
 */
public class OpenCVImageConvertTest extends OpenCVTest {
    /**
     * Tests ImageConvert.convertPoint2WrapperToJavaPoint function
     */
    @Test
    void testConvertPoint2WrapperToJavaPoint() {
        // given
        Point p = new Point(12, 13);
        Point2Wrapper<Point> wrapper = new OpenCVPoint2Wrapper(p);

        // when
        JavaPoint2D jp = pointTransformer.transformFrom(wrapper);

        // then
        Assert.assertEquals(jp.getX(), p.x);
        Assert.assertEquals(jp.getY(), p.y);
    }

    /**
     * Tests ImageConvert.convertJavaPointToPoint2Wrapper function
     */
    @Test
    void testConvertJavaPointToPoint2Wrapper() {
        // given
        JavaPoint2D jp = new JavaPoint2D(14, 15);

        // when
        Point2Wrapper<Point> wrapper = pointTransformer.transformTo(jp);

        // then
        Assert.assertEquals(wrapper.getPoint().x, jp.getX());
        Assert.assertEquals(wrapper.getPoint().y, jp.getY());
    }

    /**
     * Tests ImageConvert.convertLineWrapperToJavaLine function
     */
    @Test
    void testConvertLineWrapperToJavaLine() {
        // given
        Point startPoint = new Point(12, 13);
        Point endPoint = new Point(13, 14);
        LineWrapper<Point> lineWrapper = new OpenCVLineWrapper(startPoint, endPoint);

        // when
        JavaLine2D line = lineTransformer.transformFrom(lineWrapper);

        // then
        Assert.assertEquals(line.getStartPoint().getX(), startPoint.x);
        Assert.assertEquals(line.getStartPoint().getY(), startPoint.y);
        Assert.assertEquals(line.getEndPoint().getX(), endPoint.x);
        Assert.assertEquals(line.getEndPoint().getY(), endPoint.y);
    }

    /**
     * Tests ImageConvert.convertJavaLineToLineWrapper function
     */
    @Test
    void testConvertJavaLineToLineWrapper() {
        // given
        JavaLine2D line = new JavaLine2D(12, 13, 14, 15);

        // when
        LineWrapper<Point> lineWrapper = lineTransformer.transformTo(line);

        // then
        Point startPoint = lineWrapper.getStartPoint().getPoint();
        Point endPoint = lineWrapper.getEndPoint().getPoint();
        Assert.assertEquals(startPoint.x, line.getStartPoint().getX());
        Assert.assertEquals(startPoint.y, line.getStartPoint().getY());
        Assert.assertEquals(endPoint.x, line.getEndPoint().getX());
        Assert.assertEquals(endPoint.y, line.getEndPoint().getY());
    }
}
