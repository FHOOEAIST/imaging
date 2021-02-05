/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.offset.OrientationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.transformation.OpenCVResizeFunction;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVOrientationOffsetFunctionTest extends OpenCVTest {

    /**
     * ImageCompare.calculateOrientationOffset
     */
    @Test
    void testCalculateOrientationOffset1() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/realLifeImages/test1.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/realLifeImages/test2.jpg");
        OpenCVResizeFunction resizeFunction = new OpenCVResizeFunction();


        int width = 200;
        double factor = (double) width / (double) img1.getWidth();
        int height = (int) (img1.getHeight() * factor);
        resizeFunction.setWidth(width);
        resizeFunction.setHeight(height);

        img1 = resizeFunction.apply(img1);
        img2 = resizeFunction.apply(img2);

        OpenCVOrientationOffsetFunction orientationOffsetFunction = new OpenCVOrientationOffsetFunction();
        orientationOffsetFunction.setHorizontalAngle(73.48);
        orientationOffsetFunction.setVerticalAngle(58.49);
        orientationOffsetFunction.setOptimizer(optimizer);

        // when
        // 73.48x58.49 = (should be the) Field of View of iPhone 5s (not magic numbers :))
        // Source: http://www.caramba-apps.com/blog/files/field-of-view-angles-ipad-iphone.html
        // calculated horizontal with 2*atan(horizontal resolution / vertical resolution * tan(0.5*vertical FOV))
        OrientationOffset res = orientationOffsetFunction.apply(img1, img2);

        // then - check if horizontalOffset is more or less -26 degrees
        log.debug(res + "");
        Assert.assertEquals(res.getHorizontalAngleOffset(), 0.874, 0.01);
        // also check if verticalOffset is more or less 0.7 degrees
        Assert.assertEquals(res.getVerticalAngleOffset(), -20.822, 0.01);


    }

    /**
     * ImageCompare.calculateOrientationOffset
     */
    @Test
    void testCalculateOrientationOffset2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/realLifeImages/test3.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/realLifeImages/test4.jpg");
        OpenCVResizeFunction resizeFunction = new OpenCVResizeFunction();

        int width = 200;
        double factor = (double) width / (double) img1.getWidth();
        int height = (int) (img1.getHeight() * factor);
        resizeFunction.setWidth(width);
        resizeFunction.setHeight(height);

        img1 = resizeFunction.apply(img1);
        img2 = resizeFunction.apply(img2);

        OpenCVOrientationOffsetFunction orientationOffsetFunction = new OpenCVOrientationOffsetFunction();
        orientationOffsetFunction.setHorizontalAngle(73.48);
        orientationOffsetFunction.setVerticalAngle(58.49);
        orientationOffsetFunction.setOptimizer(optimizer);

        // when
        // 73.48x58.49 = (should be the) Field of View of iPhone 5s (not magic numbers :))
        // Source: http://www.caramba-apps.com/blog/files/field-of-view-angles-ipad-iphone.html
        // calculated horizontal with 2*atan(horizontal resolution / vertical resolution * tan(0.5*vertical FOV))
        OrientationOffset res = orientationOffsetFunction.apply(img1, img2);

        // then - check if horizontalOffset is more or less -16 degrees
        log.debug(res + "");
        double resHorizontalOffset = Math.abs(res.getHorizontalAngleOffset() + 6.0);
        Assert.assertTrue(resHorizontalOffset <= 17);
        // also check if horizontalOffset is more or less 2.2 degrees
        double resVerticalOffset = Math.abs(res.getVerticalAngleOffset());
        Assert.assertTrue(resVerticalOffset <= 2.2);
    }

    /**
     * ImageCompare.calculateOrientationOffset
     */
    @Test
    void testCalculateOrientationOffset3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/hololens/originalRotationTest.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/hololens/rotatedRotationTest.jpg");
        OpenCVResizeFunction resizeFunction = new OpenCVResizeFunction();

        int width = 200;
        double factor = (double) width / (double) img1.getWidth();
        int height = (int) (img1.getHeight() * factor);
        resizeFunction.setWidth(width);
        resizeFunction.setHeight(height);

        img1 = resizeFunction.apply(img1);
        img2 = resizeFunction.apply(img2);

        OpenCVOrientationOffsetFunction orientationOffsetFunction = new OpenCVOrientationOffsetFunction();
        orientationOffsetFunction.setHorizontalAngle(48);
        orientationOffsetFunction.setVerticalAngle(26.46);
        orientationOffsetFunction.setOptimizer(optimizer);

        // when
        // 73.48x58.49 = (should be the) Field of View of iPhone 5s (not magic numbers :))
        // Source: http://www.caramba-apps.com/blog/files/field-of-view-angles-ipad-iphone.html
        // calculated horizontal with 2*atan(horizontal resolution / vertical resolution * tan(0.5*vertical FOV))
        OrientationOffset res = orientationOffsetFunction.apply(img1, img2);

        // then - check if horizontalOffset is more or less -9 degrees
        log.debug(res + "");
        double resHorizontalOffset = Math.abs(res.getHorizontalAngleOffset() + 9.0);
        Assert.assertTrue(resHorizontalOffset <= 1);
        // also check if horizontalOffset is more or less 0 degrees
        double resVerticalOffset = Math.abs(res.getVerticalAngleOffset());
        Assert.assertTrue(resVerticalOffset <= 0.1);
    }

    /**
     * ImageCompare.calculateOrientationOffset
     */
    @Test
    void testCalculateOrientationOffset4() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/hololens/rotation1.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/hololens/rotation3.jpg");
        OpenCVResizeFunction resizeFunction = new OpenCVResizeFunction();

        int width = 200;
        double factor = (double) width / (double) img1.getWidth();
        int height = (int) (img1.getHeight() * factor);
        resizeFunction.setWidth(width);
        resizeFunction.setHeight(height);

        img1 = resizeFunction.apply(img1);
        img2 = resizeFunction.apply(img2);

        OpenCVOrientationOffsetFunction orientationOffsetFunction = new OpenCVOrientationOffsetFunction();
        orientationOffsetFunction.setHorizontalAngle(48);
        orientationOffsetFunction.setVerticalAngle(26.46);
        orientationOffsetFunction.setOptimizer(optimizer);

        // when
        // 73.48x58.49 = (should be the) Field of View of iPhone 5s (not magic numbers :))
        // Source: http://www.caramba-apps.com/blog/files/field-of-view-angles-ipad-iphone.html
        // calculated horizontal with 2*atan(horizontal resolution / vertical resolution * tan(0.5*vertical FOV))
        OrientationOffset res = orientationOffsetFunction.apply(img1, img2);
        // then - check if horizontalOffset is more or less -7 degrees
        log.debug(res + "");
        double resHorizontalOffset = Math.abs(res.getHorizontalAngleOffset() + 7);
        Assert.assertTrue(resHorizontalOffset <= 1);
        // also check if horizontalOffset is more or less 0 degrees
        double resVerticalOffset = Math.abs(res.getVerticalAngleOffset());
        Assert.assertTrue(resVerticalOffset <= 0.1);
    }

    /**
     * ImageCompare.calculateOrientationOffset
     */
    @Test
    void testCalculateOrientationOffset5() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/hololens/rotation1.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/hololens/rotation2.jpg");
        OpenCVResizeFunction resizeFunction = new OpenCVResizeFunction();

        int width = 200;
        double factor = (double) width / (double) img1.getWidth();
        int height = (int) (img1.getHeight() * factor);
        resizeFunction.setWidth(width);
        resizeFunction.setHeight(height);

        img1 = resizeFunction.apply(img1);
        img2 = resizeFunction.apply(img2);

        OpenCVOrientationOffsetFunction orientationOffsetFunction = new OpenCVOrientationOffsetFunction();
        orientationOffsetFunction.setHorizontalAngle(48);
        orientationOffsetFunction.setVerticalAngle(26.46);
        orientationOffsetFunction.setOptimizer(optimizer);

        // when
        // 73.48x58.49 = (should be the) Field of View of iPhone 5s (not magic numbers :))
        // Source: http://www.caramba-apps.com/blog/files/field-of-view-angles-ipad-iphone.html
        // calculated horizontal with 2*atan(horizontal resolution / vertical resolution * tan(0.5*vertical FOV))
        OrientationOffset res = orientationOffsetFunction.apply(img1, img2);

        // then - check if horizontalOffset is more or less -17 degrees
        log.debug(res + "");
        double resHorizontalOffset = Math.abs(res.getHorizontalAngleOffset() + 17);
        Assert.assertTrue(resHorizontalOffset <= 1);
        // also check if horizontalOffset is more or less 0 degrees
        double resVerticalOffset = Math.abs(res.getVerticalAngleOffset());
        Assert.assertTrue(resVerticalOffset <= 0.1);
    }
}
