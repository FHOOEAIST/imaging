/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.fitnessfunction;

import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.core.imageprocessing.BaseTestUtil;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link SSEFitnessFunction}</p>
 *
 * @author Christoph Praschl
 */
public class SSEFitnessFunctionTest {

    @Test
    public void testApplyAsDouble1() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4});
        AbstractFitnessFunction fitnessFunction = new SSEFitnessFunction();

        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 0, 0);
    }

    @Test
    public void testApplyAsDouble2() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{5, 5, 5, 3, 3, 3, 2, 2, 2, 1, 1, 1});
        AbstractFitnessFunction fitnessFunction = new SSEFitnessFunction();


        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 81, 0);
    }

    @Test
    public void testApplyAsDouble3() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{2, 2, 2, 99, 99, 99, 42, 42, 42, 10, 10, 10});
        AbstractFitnessFunction fitnessFunction = new SSEFitnessFunction();

        fitnessFunction.setRoi(new JavaRectangle2D(1, 1, 2, 2));

        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 9409, 0);
    }
}