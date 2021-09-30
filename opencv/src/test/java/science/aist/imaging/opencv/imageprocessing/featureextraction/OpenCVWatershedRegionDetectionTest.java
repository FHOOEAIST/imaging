/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.featureextraction;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Unittest-class for {@link OpenCVWatershedRegionDetection}
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 */
public class OpenCVWatershedRegionDetectionTest extends OpenCVTest {

    @Test
    public void testOpenCVWatershed() {
        //GIVEN
        OpenCVWatershedRegionDetection detection = new OpenCVWatershedRegionDetection();
        ImageWrapper<Mat> image = loadImageFromClassPath("/logo/original.tif");

        //WHEN
        List<RecognizedObject<Mat, Double>> objects = detection.recognizeRegion(image.getImage(), 150);

        //THEN
        Assert.assertNotNull(objects);
        Assert.assertEquals(objects.size(), 1);
        Assert.assertEquals(objects.get(0).getCoordinates().size(), 11464);
    }
}
