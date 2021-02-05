/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.distance;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link OpenCVDistanceMap}</p>
 * <p>This class does not test the different settings of the function, as the method behind only delegates to
 * OpenCV and we do not need to test the OpenCV function itself.</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVDistanceMapTest extends OpenCVTest {
    @Test
    void testApply() {
        // given
        OpenCVDistanceMap openCVDistanceMap = new OpenCVDistanceMap();
        ImageWrapper<Mat> img = loadImageFromClassPath("/logo/logoBinary.tif", false);
        ImageWrapper<Mat> ref = loadImageFromClassPath("/logo/distanceMap2.tif", false);

        // when
        ImageWrapper<Mat> res = openCVDistanceMap
                .andThenCloseInput()
                .apply(img);

        // then
        Assert.assertTrue(compareFunction.test(res, ref));
        ref.close();
    }
}
