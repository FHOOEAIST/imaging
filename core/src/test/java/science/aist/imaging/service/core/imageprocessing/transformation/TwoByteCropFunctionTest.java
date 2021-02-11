/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link TwoByteCropFunction}</p>
 *
 * @author Andreas Pointner
 */
public class TwoByteCropFunctionTest {


    private final static Image2ByteInputStreamLoader loader;

    static {
        loader = new Image2ByteInputStreamLoader();
        loader.setGreyscale(false);
    }

    private final TwoByteCropFunction<short[][][], short[][][]> twoByteCropFunction = new TwoByteCropFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));

    @Test
    void testSuccess() {
        // given
        ImageWrapper<short[][][]> image = loader.apply(getClass().getResourceAsStream("/logo/original.JPG"));
        ImageWrapper<short[][][]> imageComp = loader.apply(getClass().getResourceAsStream("/logo/originalCropped.png"));

        // when
        twoByteCropFunction.setFrom(15, 20);
        twoByteCropFunction.setTo(175, 120);
        ImageWrapper<short[][][]> result = twoByteCropFunction.apply(image);

        // then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getWidth(), 160);
        Assert.assertEquals(result.getHeight(), 100);
        Assert.assertEquals(result, imageComp);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testFailure1() {
        // given
        ImageWrapper<short[][][]> image = loader.apply(getClass().getResourceAsStream("/logo/original.JPG"));

        // when
        twoByteCropFunction.setFrom(15, 121);
        twoByteCropFunction.setTo(175, 120);
        twoByteCropFunction.apply(image);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testFailure2() {
        // given
        ImageWrapper<short[][][]> image = loader.apply(getClass().getResourceAsStream("/logo/original.JPG"));

        // when
        twoByteCropFunction.setFrom(4094840, 20);
        twoByteCropFunction.setTo(175, 120);
        twoByteCropFunction.apply(image);
    }
}
