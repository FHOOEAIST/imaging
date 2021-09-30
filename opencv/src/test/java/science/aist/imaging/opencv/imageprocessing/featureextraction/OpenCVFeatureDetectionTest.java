/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.featureextraction;

import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Testclass for testing methods of opencv Implementation for FeatureDetection</p>
 *
 * @author Christoph Praschl
 */
public class OpenCVFeatureDetectionTest extends OpenCVTest {
    /**
     * FeatureDetection.getFeature
     * I have no real idea how to test the getFeature method, so I assert if 93 features were found
     * (like in the first try). Also if this test does not make really sense. I am open towards to better ideas :)
     */
    @Test
    void testGetFeature() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");

        // when
        FeatureWrapper<KeyPoint> feature = extractor.getFeature(wrapper, null);

        // then
        Assert.assertEquals(feature.getFeatures().size(), 92);
    }

    /**
     * FeatureDetection.getEdgeFeature
     * I have no real idea how to test the getFeature method, so I assert if 1285 features were found
     * (like in the first try). Also if this test does not make really sense. I am open towards to better ideas :)
     */
    @Test
    void testGetEdgeFeature() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/logo/original.tif");
        edgeExtractor.setStepsOfInterestingPoints(1);

        // when
        FeatureWrapper<KeyPoint> feature = edgeExtractor.getFeature(wrapper, null);

        // then
        edgeExtractor.setStepsOfInterestingPoints(5);
        Assert.assertEquals(feature.getFeatures().size(), 1189);
    }
}
