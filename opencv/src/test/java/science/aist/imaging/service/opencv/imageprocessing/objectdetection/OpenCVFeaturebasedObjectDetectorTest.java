/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.objectdetection.AbstractFeaturebasedObjectDetector;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testclass for testing methods of opencv Implementation for AbstractFeaturebasedObjectDetector
 *
 * @author Christoph Praschl
 */
public class OpenCVFeaturebasedObjectDetectorTest extends OpenCVTest {
    @Autowired
    private AbstractFeaturebasedObjectDetector<Mat, Point, Rect> featurebasedObjectDetector;

    @BeforeMethod
    void prepareDetector() {
        featurebasedObjectDetector.setObjectReferenceImage(loadImageFromClassPath("/cubes/ref2.jpg"));
        featurebasedObjectDetector.setMinNumberMatchingFeatures(5);
        featurebasedObjectDetector.setThreshold(2.1);
    }

    /**
     * Tests AbstractFeaturebasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter1() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/2.jpg");

        // when
        Point2Wrapper<Point> centerPoint = featurebasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1857.0);
        Assert.assertEquals(centerPoint.getY(), 1287.0);
    }

    /**
     * Tests AbstractFeaturebasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetObjectCenter2() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/3.jpg");

        // when
        Point2Wrapper<Point> centerPoint = featurebasedObjectDetector.getObjectCenter(img);

        // then
        Assert.assertEquals(centerPoint.getX(), 1770.0);
        Assert.assertEquals(centerPoint.getY(), 1499.5);
    }

    /**
     * Tests AbstractFeaturebasedObjectDetector.getObjectCenter function
     */
    @Test
    void testGetBoundingBox1() {
        // given
        ImageWrapper<Mat> img = loadImageFromClassPath("/cubes/9.jpg");

        // when
        RectangleWrapper<Rect, Point> boundingBox = featurebasedObjectDetector.getBoundingBox(img);

        // then
        Assert.assertEquals(boundingBox.getBottomRightPoint().getX(), 2156.0);
        Assert.assertEquals(boundingBox.getBottomRightPoint().getY(), 1412.0);
        Assert.assertEquals(boundingBox.getTopLeftPoint().getX(), 1783.0);
        Assert.assertEquals(boundingBox.getTopLeftPoint().getY(), 1009.0);
    }
}
