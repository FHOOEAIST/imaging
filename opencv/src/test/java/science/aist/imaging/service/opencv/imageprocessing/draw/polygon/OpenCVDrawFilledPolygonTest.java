/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.polygon;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVImageWrapper;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link OpenCVDrawFilledPolygon}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVDrawFilledPolygonTest extends OpenCVTest {
    @Test
    void testDraw() {
        // given
        OpenCVImageWrapper compare = loadImageFromClassPath("/filledPolygonCompare.tif", true);
        OpenCVDrawFilledPolygon drawFilledPolygon = new OpenCVDrawFilledPolygon();
        ImageWrapper<Mat> imageWrapper = ImageFactoryFactory.getImageFactory(Mat.class).getImage(Mat.zeros(205, 105, CvType.CV_8UC3));
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(50, 1),
                new JavaPoint2D(100, 100),
                new JavaPoint2D(50, 200),
                new JavaPoint2D(1, 100)
        );

        // when
        drawFilledPolygon.accept(imageWrapper, jp);

        // then
        Assert.assertEquals(imageWrapper, compare);
    }
}
