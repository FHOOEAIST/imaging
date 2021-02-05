/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.service.core.storage.Image2ByteSaver;

/**
 * <p>Test class for {@link AnisotropicDiffusionFilterFunction}</p>
 *
 * @author Andreas Pointner
 */

public class AnisotropicDiffusionFilterFunctionTest {

    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final AnisotropicDiffusionFilterFunction<short[][][], short[][][]> anisotropicDiffusionFilterFunction = new AnisotropicDiffusionFilterFunction<>(Image2ByteFactory.getInstance());

    @Test
    public void testApplyType1() {
        // given
        anisotropicDiffusionFilterFunction.setDiffusionType(AnisotropicDiffusionFilterFunction.DiffusionType.TYPE_1);
        anisotropicDiffusionFilterFunction.setNumberOfIterations(5);
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = loader.apply(getClass().getResourceAsStream("/logo/original_anisotropic.bmp"));

        // when
        ImageWrapper<short[][][]> result = anisotropicDiffusionFilterFunction.apply(input);

        // then
        imageCompare.setEpsilon(1.0);
        Assert.assertTrue(imageCompare.applyAsBoolean(result, compare));
    }

    @Test
    public void testApplyType2() {
        // given
        anisotropicDiffusionFilterFunction.setDiffusionType(AnisotropicDiffusionFilterFunction.DiffusionType.TYPE_2);
        anisotropicDiffusionFilterFunction.setNumberOfIterations(5);
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = loader.apply(getClass().getResourceAsStream("/logo/original_anisotropic2.bmp"));

        // when
        ImageWrapper<short[][][]> result = anisotropicDiffusionFilterFunction.apply(input);


        // then
        imageCompare.setEpsilon(1.0);
        Assert.assertTrue(imageCompare.applyAsBoolean(result, compare));
    }
}