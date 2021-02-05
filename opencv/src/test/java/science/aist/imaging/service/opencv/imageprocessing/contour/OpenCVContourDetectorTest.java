/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;

/**
 * <p>This class tests {@link OpenCVContourDetector}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVContourDetectorTest extends OpenCVTest {
    @Test
    void testContourDetection() {
        // given
        OpenCVContourDetector ocd = new OpenCVContourDetector();
        ocd.setMethod(Imgproc.RETR_CCOMP);
        ocd.setMethod(Imgproc.CHAIN_APPROX_NONE);
        ocd.setOffset(new Point(0, 0));
        ImageWrapper<Mat> waterCoins = loadImageFromClassPath("/passport/r_segmented.bmp", false);

        // then
        Collection<JavaPolygon2D> c = ocd.apply(waterCoins);

        // assert
        Assert.assertEquals(c.size(), 2);
        for (JavaPolygon2D contour : c) {
            Assert.assertFalse(contour.isEmpty());
        }
    }
}
