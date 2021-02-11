/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion;

import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link ColorToGreyScaleConverter}</p>
 *
 * @author Andreas Pointner
 */
public class ColoredToGreyscaleFunctionTest {
    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    void testApply() {
        // given
        ColoredToGreyscaleFunction<short[][][], short[][][]> function = new ColoredToGreyscaleFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));
        function.setColorToGreyScale(new GreyscaleAverageConverter());
        ImageWrapper<short[][][]> input = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(1, 3, ChannelType.RGB, new short[][][]{
                new short[][]{
                        new short[]{255, 0, 0},
                        new short[]{0, 138, 0},
                        new short[]{0, 0, 153},
                }
        });

        // then
        ImageWrapper<short[][][]> result = function.apply(input);

        // then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getWidth(), 3);
        Assert.assertEquals(result.getHeight(), 1);
        Assert.assertEquals(result.getChannelType(), ChannelType.GREYSCALE);
        Assert.assertEquals(result.getImage()[0][0][0], 85);
        Assert.assertEquals(result.getImage()[0][1][0], 46);
        Assert.assertEquals(result.getImage()[0][2][0], 51);
    }
}
