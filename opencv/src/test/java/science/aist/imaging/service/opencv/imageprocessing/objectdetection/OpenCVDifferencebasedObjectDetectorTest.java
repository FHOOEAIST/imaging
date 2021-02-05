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
import science.aist.imaging.api.objectdetection.AbstractDifferencebasedObjectDetector;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testclass for testing methods of opencv Implementation for AbstractDifferencebasedObjectDetector
 *
 * @author Christoph Praschl
 */
public class OpenCVDifferencebasedObjectDetectorTest extends OpenCVTest {
    @Autowired
    private AbstractDifferencebasedObjectDetector<Mat, Point, Rect> diffferencebasedObjectDetector;

    @BeforeMethod
    void prepareDetector() {
        diffferencebasedObjectDetector.setReferenceImage(loadImageFromClassPath("/grid/3_ref.jpg"));
    }

    @Test
    void testGetObjectCenter1() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3_fakepos1.jpg");

        // when
        Point2Wrapper<Point> center = diffferencebasedObjectDetector.getObjectCenter(img1);

        // then
        Assert.assertEquals(center.getX(), 742.5);
        Assert.assertEquals(center.getY(), 515.5);
    }

    @Test
    void testGetObjectCenter2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3_ref.jpg");

        // when
        Point2Wrapper<Point> center = diffferencebasedObjectDetector.getObjectCenter(img1);

        // then
        Assert.assertEquals(center.getX(), -1.0);
        Assert.assertEquals(center.getY(), -1.0);
    }

    @Test
    void testGetObjectCenter3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3_fakepos3.jpg");

        // when
        Point2Wrapper<Point> center = diffferencebasedObjectDetector.getObjectCenter(img1);

        // then
        Assert.assertEquals(center.getX(), 825.0);
        Assert.assertEquals(center.getY(), 432.0);
    }


    @Test
    void testGetBoundingBox1() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3_fakepos1.jpg");

        // when
        RectangleWrapper<Rect, Point> rectangle = diffferencebasedObjectDetector.getBoundingBox(img1);

        // then
        Assert.assertEquals(rectangle.getTopLeftPoint().getX(), 733.0);
        Assert.assertEquals(rectangle.getTopLeftPoint().getY(), 505.0);
        Assert.assertEquals(rectangle.getBottomRightPoint().getX(), 752.0);
        Assert.assertEquals(rectangle.getBottomRightPoint().getY(), 526.0);
    }
}
