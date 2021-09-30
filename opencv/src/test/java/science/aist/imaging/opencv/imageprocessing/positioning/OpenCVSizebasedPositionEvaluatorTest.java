/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.positioning;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.offset.TranslationOffsetInMM;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testing OpenCVSizebasedPositionEvaluator
 *
 * @author Christoph Praschl
 */
public class OpenCVSizebasedPositionEvaluatorTest extends OpenCVTest {
    @Autowired
    @Qualifier("sizebasedPositionEvaluator")
    private OpenCVSizebasedPositionEvaluator positionEvaluator;


    @Test
    void testCalibration() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/cubes/evaluationRef.jpg");

        // when
        positionEvaluator.calibrate(img1);

        // then
        RGBColor lowerBound = positionEvaluator.getObjectDetector().getLowerBound();
        Assert.assertTrue(Math.abs(lowerBound.getRed() - 59.0) < 1.0);
        Assert.assertTrue(Math.abs(lowerBound.getGreen() - 177.0) < 1.0);
        Assert.assertTrue(Math.abs(lowerBound.getBlue() - 175.0) < 1.0);

        RGBColor upperBound = positionEvaluator.getObjectDetector().getUpperBound();
        Assert.assertTrue(Math.abs(upperBound.getRed() - 136.0) < 1.0);
        Assert.assertTrue(Math.abs(upperBound.getGreen() - 254.0) < 1.0);
        Assert.assertTrue(Math.abs(upperBound.getBlue() - 252.0) < 1.0);
    }

    @Test
    void testGetPosition() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/cubes/evaluationRef.jpg");
        positionEvaluator.calibrate(img1);
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/cubes/13.jpg");

        // when
        Point2Wrapper<Point> centerpoint = positionEvaluator.getPosition(img2);

        // then
        Assert.assertEquals(centerpoint.getX(), 1559.5);
        Assert.assertEquals(centerpoint.getY(), 1403.5);
    }

    @Test
    void testGetOffset() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/cubes/evaluationRef.jpg");
        positionEvaluator.calibrate(img1);
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/cubes/13.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/cubes/14.jpg");

        positionEvaluator.setObjectWidthInMM(61.0);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img2, img3);

        // then
        log.debug(offset + "");
        double calculationDifference = Math.abs(offset.getxOffsetInMM() - 30.0); // image was translated by 30mm in real
        Assert.assertTrue(calculationDifference < 1);
    }

    @Test
    void testGetOffset2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/cubes/evaluationRef2.jpg");
        positionEvaluator.calibrate(img1);
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/cubes/15.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/cubes/16.jpg");

        positionEvaluator.setObjectWidthInMM(61.0);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img2, img3);

        // then
        log.debug(offset + "");
        double calculationDifference = Math.abs(offset.getxOffsetInMM() - 130.0); // image was translated by 130 mm in real
        Assert.assertTrue(calculationDifference < 1);
    }

    @Test
    void testGetOffset3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/cubes/evaluationRef2.jpg");
        positionEvaluator.calibrate(img1);
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/cubes/17.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/cubes/18.jpg");

        positionEvaluator.setObjectWidthInMM(61.0);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img2, img3);

        // then
        log.debug(offset + "");
        double calculationDifference = Math.abs(offset.getxOffsetInMM() - 200.0); // image was translated by 200 mm in real
        Assert.assertTrue(calculationDifference < 3);
    }
}
