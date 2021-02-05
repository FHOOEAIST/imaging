/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.fitnessfunction;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.service.core.imageprocessing.BaseTestUtil;
import science.aist.imaging.service.core.imageprocessing.distance.ChamferDistanceMapFunction;
import science.aist.imaging.service.core.imageprocessing.distance.ManhattanDistanceMetric;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link DistanceMapFitnessFunction}</p>
 *
 * @author Christoph Praschl
 */
public class DistanceMapFitnessFunctionTest {

    @Test
    public void testApplyAsDouble1() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1}, false);
        AbstractFitnessFunction fitnessFunction = getFitnessFunction();

        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 0, 0);
    }

    @Test
    public void testApplyAsDouble2() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{10, 1, 5, 10, 1, 1, 10, 5, 10, 20, 30, 40});
        ImageWrapper<double[][][]> greyScaleSample2 = BaseTestUtil.getGreyScaleSample(3, 4, new double[]{1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1}, false);
        AbstractFitnessFunction fitnessFunction = getFitnessFunction();

        // when
        double v = fitnessFunction.applyAsDouble(greyScaleSample, greyScaleSample2);

        // then
        Assert.assertEquals(v, 250, 0);
    }

    protected AbstractFitnessFunction getFitnessFunction() {
        return new DistanceMapFitnessFunction(new ChamferDistanceMapFunction<>(0.0, new ManhattanDistanceMetric(), Image2ByteFactory.getInstance()));
    }
}