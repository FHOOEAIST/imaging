/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation.morph;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.transformation.ThresholdFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;

/**
 * <p>Test class for {@link DilateFunction}</p>
 *
 * @author Andreas Pointner
 */

public class DilateFunctionTest {

    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final ThresholdFunction<short[][][], short[][][]> threshold = new ThresholdFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
    private final DilateFunction<short[][][], short[][][]> dilate = new DilateFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

    @Test
    public void testApply() {
        // given
        threshold.setLowerThresh((short) 250);
        ImageWrapper<short[][][]> input = threshold.apply(loader.apply(getClass().getResourceAsStream("/logo/logoBinary.bmp")));
        ImageWrapper<short[][][]> compare = threshold.apply(loader.apply(getClass().getResourceAsStream("/logo/logo_dilated.png")));

        // when
        ImageWrapper<short[][][]> result = dilate.andThenCloseInput()
                .andThen(dilate.andThenCloseInput())
                .andThen(dilate.andThenCloseInput())
                .andThen(dilate.andThenCloseInput())
                .andThen(dilate.andThenCloseInput())
                .apply(input);

        // then
        Assert.assertTrue(imageCompare.applyAsBoolean(result, compare));
    }
}