/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.service.opencv.imageprocessing.storage.OpenCVSaver;

/**
 * @author Christoph Praschl
 */
public class OpenCVPerspectiveTransformationTest extends OpenCVTest {
    /**
     * Test OpenCVPerspectiveTransformation.apply
     */
    @Test
    void testPerspectiveTransformation() {
        // given
        ImageWrapper<Mat> wrapper = loadImageFromClassPath("/drivingLicenses/drivingLicense.tif");
        // Load reference image
        ImageWrapper<Mat> perspectiveTransformation = loadImageFromClassPath("/drivingLicenses/drivingLicensePerspectiveTransformation.tif");

        OpenCVPerspectiveTransformation f = new OpenCVPerspectiveTransformation();
        f.setBottomLeft(new JavaPoint2D(259, 226));
        f.setTopLeft(new JavaPoint2D(1072, 229));
        f.setTopRight(new JavaPoint2D(1241, 714));
        f.setBottomRight(new JavaPoint2D(105, 705));

        f.setBottomLeftTarget(new JavaPoint2D(0, 0));
        f.setTopLeftTarget(new JavaPoint2D(wrapper.getWidth(), 0));
        f.setTopRightTarget(new JavaPoint2D(wrapper.getWidth(), wrapper.getHeight()));
        f.setBottomRightTarget(new JavaPoint2D(0, wrapper.getHeight()));

        f.setBorderValue(RGBColor.BLACK);

        // when
        // fixed values which fits to the given drivingLicense to cut it out "perfectly"
        ImageWrapper<Mat> res = f.apply(wrapper);

        // then
        // check if image fits to reference.
        Assert.assertTrue(compareFunction.test(perspectiveTransformation, res));
    }
}
