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
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;

import java.util.Arrays;

/**
 * <p>Test class for {@link ChannelMerger}</p>
 *
 * @author Christoph Praschl
 */
public class ChannelMergerTest {

    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> r = provider.getImage(10, 10, ChannelType.GREYSCALE, 0);
        ImageWrapper<short[][][]> g = provider.getImage(10, 10, ChannelType.GREYSCALE, 1);
        ImageWrapper<short[][][]> b = provider.getImage(10, 10, ChannelType.GREYSCALE, 2);

        ChannelMerger<short[][][], short[][][]> merger = new ChannelMerger<>(provider);

        // when
        ImageWrapper<short[][][]> apply = merger.apply(Arrays.asList(r, g, b), ChannelType.RGB);

        // then
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int c = 0; c < 3; c++) {
                    Assert.assertEquals(apply.getValue(x, y, c), (double) c);
                }
            }
        }
    }

}
