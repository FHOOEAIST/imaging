/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.helper;

import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;

/**
 * <p>Tests {@link FindMaxFunction}</p>
 *
 * @author Andreas Pointner
 */
public class FindMaxFunctionTest {

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    public void testApply() {
        // given
        FindMaxFunction maxFinder = new FindMaxFunction();
        ImageWrapper<short[][][]> imageWrapper = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(1, 3, ChannelType.GREYSCALE, new short[][][]{
                new short[][]{
                        new short[]{59},
                        new short[]{17},
                        new short[]{38}
                }
        });

        // when
        double max = maxFinder.applyAsDouble(imageWrapper);

        // then
        Assert.assertEquals(max, 59.0);
    }
}
