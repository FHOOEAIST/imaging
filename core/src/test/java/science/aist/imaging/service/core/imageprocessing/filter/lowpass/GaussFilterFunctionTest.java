/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.lowpass;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;

/**
 * <p>Test class for {@link GaussFilterFunction}</p>
 *
 * @author Andreas Pointner
 */

public class GaussFilterFunctionTest {

    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final GaussFilterFunction<short[][][], short[][][]> gaussFilterFunction = new GaussFilterFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

    @Test
    public void testApply() {
        // given
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = loader.apply(getClass().getResourceAsStream("/logo/original_gauss.png"));

        // when
        ImageWrapper<short[][][]> result = gaussFilterFunction.apply(input);

        // then
        imageCompare.setEpsilon(1.0);
        Assert.assertTrue(imageCompare.applyAsBoolean(compare, result));
    }
}