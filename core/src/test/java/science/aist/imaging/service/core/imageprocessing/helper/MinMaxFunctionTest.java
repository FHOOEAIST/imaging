/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.helper;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.jack.math.MinMax;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>Test class for {@link MinMaxFunction}</p>
 *
 * @author Christoph Praschl
 */
public class MinMaxFunctionTest {

    @Test
    public void testApply() {
        // given
        Random random = new Random(768457);

        ImageWrapper<short[][][]> provide = Image2ByteFactory.getInstance().getImage(10, 10, ChannelType.BGR);
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    provide.setValue(x, y, c, random.nextInt(255));
                }
            }
        }

        MinMaxFunction function = new MinMaxFunction();

        // when
        MinMax<double[]> apply = function.apply(provide);

        // then
        Assert.assertTrue(Arrays.equals(apply.getMin(), new double[]{0.0, 4.0, 3.0}));
        Assert.assertTrue(Arrays.equals(apply.getMax(), new double[]{252.0, 252.0, 254.0}));
    }

}
