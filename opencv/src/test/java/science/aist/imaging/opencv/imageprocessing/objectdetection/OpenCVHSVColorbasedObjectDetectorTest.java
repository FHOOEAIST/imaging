/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.color.HSVColor;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.objectdetection.AbstractColorbasedObjectDetector;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testclass for testing methods of opencv HSV Implementation for AbstractColorbasedObjectDetector
 *
 * @author Christoph Praschl
 */
public class OpenCVHSVColorbasedObjectDetectorTest extends OpenCVTest {
    @Autowired
    private AbstractColorbasedObjectDetector<Mat, Point, Rect, HSVColor> colorbasedObjectDetector;

    @BeforeMethod
    void prepareDetector() {
        colorbasedObjectDetector.setLowerBound(new HSVColor(100, 0.2, 0.2));
        colorbasedObjectDetector.setUpperBound(new HSVColor(160, 1, 1));
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter1() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/1.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1640.0);
        Assert.assertEquals(centerPoint.getY(), 1450.5);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter2() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/2.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1854.5);
        Assert.assertEquals(centerPoint.getY(), 1281.5);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter3() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/3.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1760.5);
        Assert.assertEquals(centerPoint.getY(), 1490.0);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter4() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/4.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1423.5);
        Assert.assertEquals(centerPoint.getY(), 1469.0);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter5() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/5.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 2263.0);
        Assert.assertEquals(centerPoint.getY(), 1462.0);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter6() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/6.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 2145.0);
        Assert.assertEquals(centerPoint.getY(), 949.0);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter7() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/7.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1755.0);
        Assert.assertEquals(centerPoint.getY(), 724.5);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter8() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/8.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1717.5);
        Assert.assertEquals(centerPoint.getY(), 1130.5);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter9() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/9.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1969.5);
        Assert.assertEquals(centerPoint.getY(), 1205.5);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter10() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/10.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1899.0);
        Assert.assertEquals(centerPoint.getY(), 1282.5);
    }

    /**
     * Tests AbstractColorbasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter11() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/grid/3_ref.jpg");

        // when
        Point2Wrapper<Point> centerPoint = colorbasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), -1.0);
        Assert.assertEquals(centerPoint.getY(), -1.0);
    }
}
