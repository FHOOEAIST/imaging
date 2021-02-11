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
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.contrast.GammaCorrectionFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;

/**
 * <p>Test class for {@link GammaCorrectionFunction}</p>
 *
 * @author Andreas Pointner
 */

public class GammaCorrectionFunctionTest {
    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final GammaCorrectionFunction<short[][][], short[][][]> gammaCorrectionFunction = new GammaCorrectionFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

    @Test
    public void testApply() {
        // given
        imageCompare.setEpsilon(1);
        gammaCorrectionFunction.setGamma(.5);
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = loader.apply(getClass().getResourceAsStream("/logo/logo_gamma_corrected.bmp"));

        // when
        ImageWrapper<short[][][]> result = gammaCorrectionFunction.apply(input);

        // then
        Assert.assertTrue(imageCompare.applyAsBoolean(compare, result));
    }
}