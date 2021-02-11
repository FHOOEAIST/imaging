/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.distance;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.BaseTestUtil;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link ChamferDistanceMapFunction}</p>
 *
 * @author Christoph Praschl
 */
public class ChamferDistanceMapFunctionTest {

    @Test
    public void testApply() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(6, 5, new double[]{255, 255, 255, 255, 255, 255,
                0, 0, 0, 0, 0, 255,
                255, 255, 0, 255, 255, 255,
                0, 0, 0, 255, 255, 255,
                255, 255, 255, 255, 255, 255}, false);

        ImageWrapper<double[][][]> compareValue = BaseTestUtil.getGreyScaleSample(6, 5, new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 2.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
                1.0, 1.0, 0.0, 1.0, 1.0, 2.0,
                0.0, 0.0, 0.0, 1.0, 2.0, 3.0,
                1.0, 1.0, 1.0, 2.0, 3.0, 4.0});

        ChamferDistanceMapFunction<double[][][]> distanceMapFunction = new ChamferDistanceMapFunction<>(0, new ManhattanDistanceMetric(), TypeBasedImageFactoryFactory.getImageFactory(double[][][].class));


        // when
        ImageWrapper<?> apply = distanceMapFunction.apply(greyScaleSample);

        // then
        GenericImageCompareFunction compare = new GenericImageCompareFunction();
        Assert.assertTrue(compare.applyAsBoolean(apply, compareValue));
    }

    @Test
    public void testApply2() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(6, 5, new double[]{255, 255, 255, 255, 255, 255,
                0, 0, 0, 0, 0, 255,
                255, 255, 0, 255, 255, 255,
                0, 0, 0, 255, 255, 255,
                255, 255, 255, 255, 255, 255}, false);

        ImageWrapper<double[][][]> compareValue = BaseTestUtil.getGreyScaleSample(6, 5, new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.4142135623730951,
                0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
                1.0, 1.0, 0.0, 1.0, 1.0, 1.4142135623730951,
                0.0, 0.0, 0.0, 1.0, 2.0, 2.414213562373095,
                1.0, 1.0, 1.0, 1.4142135623730951, 2.414213562373095, 3.414213562373095});

        ChamferDistanceMapFunction<double[][][]> distanceMapFunction = new ChamferDistanceMapFunction<>(0, new EuclidianDistanceMetric(), TypeBasedImageFactoryFactory.getImageFactory(double[][][].class));


        // when
        ImageWrapper<?> apply = distanceMapFunction.apply(greyScaleSample);

        // then
        GenericImageCompareFunction compare = new GenericImageCompareFunction();
        Assert.assertTrue(compare.applyAsBoolean(apply, compareValue));
    }

    @Test
    public void testApply3() {
        // given
        ImageWrapper<double[][][]> greyScaleSample = BaseTestUtil.getGreyScaleSample(6, 5, new double[]{255, 255, 255, 255, 255, 255,
                0, 0, 0, 0, 0, 255,
                255, 255, 0, 255, 255, 255,
                0, 0, 0, 255, 255, 255,
                255, 255, 255, 255, 255, 255}, false);

        ImageWrapper<double[][][]> compareValue = BaseTestUtil.getGreyScaleSample(6, 5, new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
                1.0, 1.0, 0.0, 1.0, 1.0, 1.0,
                0.0, 0.0, 0.0, 1.0, 2.0, 2.0,
                1.0, 1.0, 1.0, 1.0, 2.0, 3.0});

        ChamferDistanceMapFunction<double[][][]> distanceMapFunction = new ChamferDistanceMapFunction<>(0, new ChessboardDistanceMetric(), TypeBasedImageFactoryFactory.getImageFactory(double[][][].class));


        // when
        ImageWrapper<?> apply = distanceMapFunction.apply(greyScaleSample);

        // then
        GenericImageCompareFunction compare = new GenericImageCompareFunction();
        Assert.assertTrue(compare.applyAsBoolean(apply, compareValue));
    }
}