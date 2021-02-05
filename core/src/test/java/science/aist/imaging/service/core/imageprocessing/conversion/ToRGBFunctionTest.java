/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion;


import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link ToRGBFunction}</p>
 *
 * @author Andreas Pointner
 */
public class ToRGBFunctionTest {
    @Test
    void testRgb() {
        // given
        ImageWrapper<short[][][]> rgb = Image2ByteFactory.getInstance().getImage(2, 2, ChannelType.RGB);

        // when
        ImageFactory<short[][][]> byteProvider = Image2ByteFactory.getInstance();
        ImageFactory<short[][][]> byte2Provider = Image2ByteFactory.getInstance();
        ImageWrapper<short[][][]> result = new ToRGBFunction<>(byteProvider, byte2Provider).apply(rgb);

        // then
        Assert.assertEquals(rgb, result);
    }

    @Test
    void testRgba() {
        // given
        ImageWrapper<short[][][]> rgba = Image2ByteFactory.getInstance().getImage(2, 2, ChannelType.RGBA);

        // when
        ImageWrapper<short[][][]> result = new ToRGBFunction<>(Image2ByteFactory.getInstance(), Image2ByteFactory.getInstance()).apply(rgba);

        // then
        Assert.assertEquals(result.getChannelType(), ChannelType.RGB);
    }

    @Test
    void testGreyscale() {
        // given
        ImageWrapper<short[][][]> rgba = Image2ByteFactory.getInstance().getImage(2, 2, ChannelType.GREYSCALE);

        // when
        ImageWrapper<short[][][]> result = new ToRGBFunction<>(Image2ByteFactory.getInstance(), Image2ByteFactory.getInstance()).apply(rgba);

        // then
        Assert.assertEquals(result.getChannelType(), ChannelType.RGB);
    }

    @Test
    void testBinary() {
        // given
        ImageWrapper<short[][][]> rgba = Image2ByteFactory.getInstance().getImage(2, 2, ChannelType.BINARY);

        // when
        ImageWrapper<short[][][]> result = new ToRGBFunction<>(Image2ByteFactory.getInstance(), Image2ByteFactory.getInstance()).apply(rgba);

        // then
        Assert.assertEquals(result.getChannelType(), ChannelType.RGB);
    }
}
