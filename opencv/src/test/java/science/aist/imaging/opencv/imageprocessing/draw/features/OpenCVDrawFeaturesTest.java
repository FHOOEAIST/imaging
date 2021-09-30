/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.draw.features;

import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVDrawFeaturesTest extends OpenCVTest {
    /**
     * Tests OpenCVDrawFeatures.accept function
     */
    @Test
    void testDrawFeaturesOnImage() {
        // given
        ImageWrapper<Mat> ref = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> compare = loadImageFromClassPath("/logo/features.tif");

        OpenCVDrawFeatures consumer = new OpenCVDrawFeatures();
        FeatureWrapper<KeyPoint> features = extractor.getFeature(ref, null);

        // when
        consumer.accept(ref, features);

        // then
        Assert.assertTrue(compareFunction.test(ref, compare));
    }
}

