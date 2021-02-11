/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.operator;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Test class for {@link DivFunction}</p>
 *
 * @author Christoph Praschl
 */
public class DivFunctionTest {
    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, 10);
        ImageWrapper<short[][][]> provide2 = provider.getImage(10, 10, ChannelType.BGR, 5);

        DivFunction<short[][][]> function = new DivFunction<>();

        // when
        ImageWrapper<short[][][]> apply = function.apply(provide, provide2);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(apply.getValue(x, y, c), 2.0);
                }
            }
        }
    }

    @Test
    public void testApply2(){
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, 10);

        DivFunction<short[][][]> function = new DivFunction<>();

        // when
        ImageWrapper<short[][][]> apply = function.apply(provide, 10);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(apply.getValue(x, y, c), 1.0);
                }
            }
        }
    }
}
