/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.fitnessfunction;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;
import science.aist.imaging.core.imageprocessing.BaseTestUtil;

/**
 * <p>Test class for {@link MIFitnessFunction}</p>
 *
 * @author Christoph Praschl
 */
public class MIFitnessFunctionTest {

    @Test
    public void testApplyAsDouble1() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4, 4});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4, 4});
        AbstractFitnessFunction fitnessFunction = new MIFitnessFunction();

        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 1.0397, 0.0001);
    }


    @Test
    public void testApplyAsDouble2() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4, 4});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{4, 5, 9, 1, 6, 5, 9, 5, 3, 7, 8, 2});
        AbstractFitnessFunction fitnessFunction = new MIFitnessFunction();

        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 0.76506, 0.0001);
    }
}