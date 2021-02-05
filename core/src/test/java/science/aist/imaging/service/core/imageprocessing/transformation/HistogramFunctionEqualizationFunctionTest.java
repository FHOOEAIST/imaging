/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.service.core.imageprocessing.contrast.HistogramEqualizationFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;

/**
 * <p>Test class for {@link HistogramEqualizationFunction}</p>
 *
 * @author Andreas Pointner
 */
public class HistogramFunctionEqualizationFunctionTest {
    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final HistogramEqualizationFunction<short[][][], short[][][]> histogramEqualizationFunction = new HistogramEqualizationFunction<>(Image2ByteFactory.getInstance());

    @Test
    public void testApply() {
        // given
        imageCompare.setEpsilon(3);
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = loader.apply(getClass().getResourceAsStream("/logo/histogram_equalization.bmp"));

        // when
        ImageWrapper<short[][][]> result = histogramEqualizationFunction.apply(input);

        // then
        Assert.assertTrue(imageCompare.applyAsBoolean(result, compare));
    }
}