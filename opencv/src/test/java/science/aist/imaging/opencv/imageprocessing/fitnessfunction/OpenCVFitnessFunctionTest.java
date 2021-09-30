/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.fitnessfunction;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.FitnessFunction;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.transformation.OpenCVTranslateFunction;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testclass for testing methods of opencv Implementation for FitnessFunction
 *
 * @author Christoph Praschl
 */
public class OpenCVFitnessFunctionTest extends OpenCVTest {
    @Autowired
    private FitnessFunction<KeyPoint, Mat> fitness;

    /**
     * Tests FitnessFunction.getFitness function
     */
    @Test
    void testGetFitness1() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/logo/original.tif");

        // when
        double value = fitness.getFitness(img1, img2);

        // then
        // test with same image -> result should be 0
        Assert.assertEquals(value, 0.0);
    }

    /**
     * Tests FitnessFunction.getFitness function
     */
    @Test
    void testGetFitness2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        OpenCVTranslateFunction translateFunction = new OpenCVTranslateFunction();
        translateFunction.setXOffset(180);
        ImageWrapper<Mat> translatedImage = translateFunction.apply(img1);

        // when
        double value = fitness.getFitness(img1, translatedImage);

        // then
        // test with different images --> result should be somewhere between 0 and Double.MAX_VALUE
        Assert.assertTrue(value > 0);
        Assert.assertTrue(value < Double.MAX_VALUE);
    }

    /**
     * Tests FitnessFunction.getFitness function
     */
    @Test
    void testGetFitness3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        OpenCVTranslateFunction translateFunction = new OpenCVTranslateFunction();
        translateFunction.setXOffset(3);
        ImageWrapper<Mat> translatedImage = translateFunction.apply(img1);

        // when
        double value = fitness.getFitness(img1, translatedImage);

        // then
        // test with different images --> result should be somewhere between 0 and Double.MAX_VALUE
        Assert.assertTrue(value > 0);
        Assert.assertTrue(value < Double.MAX_VALUE);
    }

    /**
     * Tests FitnessFunction.getFitness function
     */
    @Test
    void testGetFitness4() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/logo/original.tif");
        FeatureWrapper<KeyPoint> featuresImg1 = extractor.getFeature(img1, null);
        FeatureWrapper<KeyPoint> featuresImg2 = extractor.getFeature(img2, null);

        // when
        double value = fitness.getFitness(featuresImg1, featuresImg2);

        // then
        // test with same image -> result should be 0
        Assert.assertEquals(value, 0.0);
    }

    /**
     * Tests FitnessFunction.getFitness function
     */
    @Test
    void testGetFitness5() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/translated.tif");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/logo/translated.tif");

        // when
        double value = fitness.getFitness(img1, img2, RGBColor.BLACK);

        // then
        // test with same image but ignoring black -> result should be 0
        Assert.assertEquals(value, 0.0);
    }
}
