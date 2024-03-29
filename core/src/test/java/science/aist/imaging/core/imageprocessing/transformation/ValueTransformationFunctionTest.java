/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformation;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Test class for {@link ValueTransformationFunction}</p>
 *
 * @author Andreas Pointner
 */

public class ValueTransformationFunctionTest {

    @Test
    public void testApply() {
        // given
        ValueTransformationFunction<short[][][], short[][][]> valueTransformationFunction = new ValueTransformationFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
        ImageWrapper<short[][][]> imageWrapper = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(1, 3, ChannelType.GREYSCALE, new short[][][]{
                new short[][]{
                        new short[]{1},
                        new short[]{2},
                        new short[]{3}
                }
        });
        int[] transformationFunction = new int[]{0, 2, 3, 4};

        // when
        ImageWrapper<short[][][]> apply = valueTransformationFunction.apply(imageWrapper, transformationFunction);

        // then
        Assert.assertEquals(apply.getImage()[0][0][0], 2);
        Assert.assertEquals(apply.getImage()[0][1][0], 3);
        Assert.assertEquals(apply.getImage()[0][2][0], 4);
    }
}