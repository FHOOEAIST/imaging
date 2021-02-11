/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Tests {@link Image2Byte}</p>
 *
 * @author Andreas Pointner
 */
public class Image2ByteTest {
    @Test
    void testGetValue() {
        // given
        ImageWrapper<short[][][]> imageWrapper = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);
        imageWrapper.getImage()[5][6][2] = 42;

        // when
        double val = imageWrapper.getValue(6, 5, 2);

        // then
        Assert.assertEquals(val, 42.0);
    }

    @Test
    void testSetValue() {
        // given
        ImageWrapper<short[][][]> imageWrapper = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);

        // when
        imageWrapper.setValue(6, 5, 2, 42);

        // then
        Assert.assertEquals(imageWrapper.getImage()[5][6][2], 42);
    }

    @Test
    void testApplyFunctionWithStride1() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.GREYSCALE, 1);

        // when
        image.applyFunction((image1, x, y, c) -> {
            double d = image1.getValue(x, y, c);
            image1.setValue(x, y, c, d + 1);
        }, 0, 0, image.getWidth(), image.getHeight(), 2, 3, image.supportsParallelAccess());

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int value = (int) image.getValue(x, y, 0);
                if (x % 2 == 0 && y % 3 == 0) {
                    Assert.assertEquals(value, 2);
                } else {
                    Assert.assertEquals(value, 1);
                }
            }
        }
    }

    @Test
    void testApplyFunctionWithStride() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(12, 12, ChannelType.GREYSCALE, 1);

        // when
        image.applyFunction((image1, x, y, c) -> {
            double d = image1.getValue(x, y, c);
            image1.setValue(x, y, c, d + 1);
        }, 0, 0, image.getWidth(), image.getHeight(), 2, 3, image.supportsParallelAccess());

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int value = (int) image.getValue(x, y, 0);
                if (x % 2 == 0 && y % 3 == 0) {
                    Assert.assertEquals(value, 2);
                } else {
                    Assert.assertEquals(value, 1);
                }
            }
        }
    }
}
