/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.color.RGBColor;
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
 * Testclass for testing methods of opencv RGB Implementation for AbstractColorbasedObjectDetector
 *
 * @author Christoph Praschl
 */
public class OpenCVRGBColorbasedObjectDetectorTest extends OpenCVTest {

    @Autowired
    private AbstractColorbasedObjectDetector<Mat, Point, Rect, RGBColor> colorbasedObjectDetector;

    @BeforeMethod
    void prepareDetector() {
        colorbasedObjectDetector.setLowerBound(new RGBColor(11, 52, 34));
        colorbasedObjectDetector.setUpperBound(new RGBColor(49, 109, 83));
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
        Assert.assertEquals(centerPoint.getX(), 1640.5);
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
        Assert.assertEquals(centerPoint.getX(), 1856.0);
        Assert.assertEquals(centerPoint.getY(), 1281.0);
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
        Assert.assertEquals(centerPoint.getX(), 1761.5);
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
        Assert.assertEquals(centerPoint.getY(), 1468.0);
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
        Assert.assertEquals(centerPoint.getX(), 2262.5);
        Assert.assertEquals(centerPoint.getY(), 1461.5);
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
        Assert.assertEquals(centerPoint.getX(), 2143.5);
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
        Assert.assertEquals(centerPoint.getX(), 1756.0);
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
        Assert.assertEquals(centerPoint.getX(), 1699.5);
        Assert.assertEquals(centerPoint.getY(), 1132.5);
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
        Assert.assertEquals(centerPoint.getX(), 1970.0);
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
        Assert.assertEquals(centerPoint.getY(), 1284.0);
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
