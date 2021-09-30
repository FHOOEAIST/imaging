/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.conversion;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.List;

/**
 * <p>Test class for {@link ChannelSplitter}</p>
 *
 * @author Christoph Praschl
 */
public class ChannelSplitterTest {

    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.RGB, new double[]{0, 1, 2});
        ChannelSplitter<short[][][], short[][][]> splitter = new ChannelSplitter<>(provider);

        // when
        List<ImageWrapper<short[][][]>> apply = splitter.apply(provide);

        // then
        for (ImageWrapper<short[][][]> imageWrapper : apply) {
            Assert.assertEquals(imageWrapper.getHeight(), 10);
            Assert.assertEquals(imageWrapper.getWidth(), 10);
            Assert.assertEquals(imageWrapper.getChannelType(), ChannelType.GREYSCALE);
        }

        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    ImageWrapper<short[][][]> imageWrapper = apply.get(i);
                    Assert.assertEquals(imageWrapper.getValue(x, y, 0), (double) i);
                }
            }
        }
    }

}
