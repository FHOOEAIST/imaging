/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.registration;

import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.registration.Registration;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.jack.general.util.CastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Andreas Pointner
 */
public class OpenCVRegistrationTest extends OpenCVTest {
    @Autowired
    @Qualifier("positionalRegistration")
    private Registration<Mat> positionalRegistration;

    @Autowired
    @Qualifier("positionalAndRotationalRegistration")
    private Registration<Mat> positionalAndRotationalRegistration;

    /**
     * Tests Registration.positionalRegistration function
     */
    @Test
    void testPositionalRegistration() {
        // given
        ImageWrapper<Mat> ref = loadImageFromClassPath("/drivingLicenses/registration/ref.tif", false);
        List<ImageWrapper<Mat>> images = new ArrayList<>();
        List<ImageWrapper<Mat>> expRes = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            images.add(loadImageFromClassPath("/drivingLicenses/registration/img" + i + ".tif"));
            expRes.add(loadImageFromClassPath("/drivingLicenses/registration/drivingLicenseRegistration" + i + ".tif"));
        }

        // when
        Collection<ImageWrapper<Mat>> results = positionalRegistration.register(ref, images);

        // then
        ImageWrapper<Mat>[] resArr = CastUtils.cast(results.toArray(new ImageWrapper[0]));
        for (int i = 0; i < 2; i++) {
            Assert.assertTrue(compareFunction.test(resArr[i], expRes.get(i)));
        }
    }

    /**
     * Tests positionalAndRotationalRegistration function
     */
    @Test
    void testPositionalAndRotationalRegistration() {
        // given
        ImageWrapper<Mat> ref = loadImageFromClassPath("/drivingLicenses/registration/ref.tif", false);
        ImageWrapper<Mat> i1 = loadImageFromClassPath("/drivingLicenses/registration/img1.tif", false);
        ImageWrapper<Mat> i2 = loadImageFromClassPath("/drivingLicenses/registration/img2.tif", false);
        ImageWrapper<Mat> r1 = loadImageFromClassPath("/drivingLicenses/registration/res1.tif", false);
        ImageWrapper<Mat> r2 = loadImageFromClassPath("/drivingLicenses/registration/res2.tif", false);

        // when
        Collection<ImageWrapper<Mat>> results = positionalAndRotationalRegistration.register(ref, Arrays.asList(i1, i2));

        // then
        ImageWrapper<Mat>[] res = CastUtils.cast(results.toArray(new ImageWrapper[0]));
        Assert.assertTrue(compareFunction.test(res[0], r1));
        Assert.assertTrue(compareFunction.test(res[1], r2));
    }
}
