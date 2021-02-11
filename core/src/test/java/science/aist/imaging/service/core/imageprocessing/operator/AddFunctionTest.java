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
 * <p>Test class for {@link AddFunction}</p>
 *
 * @author Christoph Praschl
 */
public class AddFunctionTest {

    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, 5);
        ImageWrapper<short[][][]> provide2 = provider.getImage(10, 10, ChannelType.BGR, 10);

        AddFunction<short[][][]> function = new AddFunction<>();

        // when
        ImageWrapper<short[][][]> apply = function.apply(provide, provide2);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(apply.getValue(x, y, c), 15.0);
                }
            }
        }
    }

    @Test
    public void testApply2() {
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, 5);
        ImageWrapper<short[][][]> provide2 = provider.getImage(10, 10, ChannelType.BGR, 10);
        ImageWrapper<short[][][]> mask = provider.getImage(10, 10, ChannelType.BINARY);
        mask.setValue(0, 0, 0, 255);

        AddFunction<short[][][]> function = new AddFunction<>();

        // when
        ImageWrapper<short[][][]> apply = function.apply(provide, provide2, mask);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    if (x == 0 && y == 0) {
                        Assert.assertEquals(apply.getValue(x, y, c), 15.0);
                    } else {
                        Assert.assertEquals(apply.getValue(x, y, c), 5.0);
                    }
                }
            }
        }
    }

    @Test
    public void testApply3() {
        // given
        ImageFactory<double[][][]> provider = ImageFactoryFactory.getImageFactory(double[][][].class);
        ImageWrapper<double[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, 5);
        ImageWrapper<double[][][]> provide2 = provider.getImage(10, 10, ChannelType.BGR, 10);

        AddFunction<double[][][]> function = new AddFunction<>();
        function.setAlpha(0.5);
        function.setBeta(1.5);
        function.setGamma(1);

        // when
        ImageWrapper<double[][][]> apply = function.apply(provide, provide2);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(apply.getValue(x, y, c), 18.5);
                }
            }
        }
    }

}
