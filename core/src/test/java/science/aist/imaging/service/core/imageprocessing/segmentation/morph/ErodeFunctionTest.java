/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation.morph;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.service.core.imageprocessing.transformation.ThresholdFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link ErodeFunction}</p>
 *
 * @author Andreas Pointner
 */

public class ErodeFunctionTest {

    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final ThresholdFunction<short[][][], short[][][]> threshold = new ThresholdFunction<>(Image2ByteFactory.getInstance());
    private final ErodeFunction<short[][][], short[][][]> erode = new ErodeFunction<>(Image2ByteFactory.getInstance());

    @Test
    public void testApply() {
        // given
        threshold.setLowerThresh((short) 250);
        ImageWrapper<short[][][]> input = threshold.apply(loader.apply(getClass().getResourceAsStream("/logo/logoBinary.bmp")));
        ImageWrapper<short[][][]> compare = threshold.apply(loader.apply(getClass().getResourceAsStream("/logo/logo_erode.png")));

        // when
        ImageWrapper<short[][][]> result = erode.andThenCloseInput()
                .andThen(erode.andThenCloseInput())
                .andThen(erode.andThenCloseInput())
                .andThen(erode.andThenCloseInput())
                .andThen(erode.andThenCloseInput())
                .apply(input);


        // then
        Assert.assertTrue(imageCompare.applyAsBoolean(result, compare));
    }
}