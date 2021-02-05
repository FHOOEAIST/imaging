/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link PaddingFunction}</p>
 *
 * @author Christoph Praschl
 */
public class PaddingFunctionTest {

    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = Image2ByteFactory.getInstance();
        ImageWrapper<short[][][]> image = provider.getImage(10, 10, ChannelType.GREYSCALE, 1);

        PaddingFunction<short[][][], short[][][]> paddingFunction = new PaddingFunction<>(provider, new double[]{0});
        paddingFunction.setPaddingUnify(5);

        // when
        ImageWrapper<short[][][]> apply = paddingFunction.apply(image);

        // then
        Assert.assertEquals(apply.getWidth(), 20);
        Assert.assertEquals(apply.getHeight(), 20);

        for (int x = 0; x < apply.getWidth(); x++) {
            for (int y = 0; y < apply.getHeight(); y++) {
                if (x > 4 && x < 15 && y > 4 && y < 15) {
                    Assert.assertEquals((int) apply.getValue(x, y, 0), 1);
                } else {
                    Assert.assertEquals((int) apply.getValue(x, y, 0), 0);
                }
            }
        }
    }


    @Test
    public void testApply2() {
        // given
        ImageFactory<short[][][]> provider = Image2ByteFactory.getInstance();
        ImageWrapper<short[][][]> image = provider.getImage(10, 10, ChannelType.GREYSCALE, 1);

        PaddingFunction<short[][][], short[][][]> paddingFunction = new PaddingFunction<>(provider, new double[]{0});
        paddingFunction.setPaddingTop(5);
        paddingFunction.setPaddingLeft(3);

        // when
        ImageWrapper<short[][][]> apply = paddingFunction.apply(image);

        // then
        Assert.assertEquals(apply.getWidth(), 13);
        Assert.assertEquals(apply.getHeight(), 15);

        for (int x = 0; x < apply.getWidth(); x++) {
            for (int y = 0; y < apply.getHeight(); y++) {
                if (x > 2 && y > 4) {
                    Assert.assertEquals((int) apply.getValue(x, y, 0), 1);
                } else {
                    Assert.assertEquals((int) apply.getValue(x, y, 0), 0);
                }
            }
        }
    }

}
