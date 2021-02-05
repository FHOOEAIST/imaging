/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.operator;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link SubFunction}</p>
 *
 * @author Christoph Praschl
 */
public class SubFunctionTest {
    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = Image2ByteFactory.getInstance();
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, 10);
        ImageWrapper<short[][][]> provide2 = provider.getImage(10, 10, ChannelType.BGR, 5);

        SubFunction<short[][][]> function = new SubFunction<>();

        // when
        ImageWrapper<short[][][]> apply = function.apply(provide, provide2);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(apply.getValue(x, y, c), 5.0);
                }
            }
        }
    }
}
