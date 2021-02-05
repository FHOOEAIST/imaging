/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link Image8Byte}</p>
 *
 * @author Andreas Pointner
 */
public class Image8ByteTest {
    @Test
    void testGetValue() {
        // given
        ImageFactory<double[][][]> byteProvider = Image8ByteFactory.getInstance();
        ImageWrapper<double[][][]> imageWrapper = byteProvider.getImage(10, 10, ChannelType.RGB);
        imageWrapper.getImage()[5][6][2] = 42;

        // when
        double val = imageWrapper.getValue(6, 5, 2);

        // then
        Assert.assertEquals(val, 42.0);
    }

    @Test
    void testSetValue() {
        // given
        ImageWrapper<double[][][]> imageWrapper = Image8ByteFactory.getInstance().getImage(10, 10, ChannelType.RGB);

        // when
        imageWrapper.setValue(6, 5, 2, 42);

        // then
        Assert.assertEquals(imageWrapper.getImage()[5][6][2], 42.0);
    }
}
