/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.helper;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Test class for {@link HistogramFunction}</p>
 *
 * @author Andreas Pointner
 */

public class HistogramFunctionTest {
    private final HistogramFunction<short[][][]> histogramFunction = new HistogramFunction<>();

    @Test
    public void testApply() {
        // given
        ImageWrapper<short[][][]> imageWrapper = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(2, 3, ChannelType.GREYSCALE, new short[][][]{
                new short[][]{
                        new short[]{1},
                        new short[]{2},
                        new short[]{1},
                },
                new short[][]{
                        new short[]{0},
                        new short[]{0},
                        new short[]{1},
                },
        });

        // when
        int[] histogram = histogramFunction.apply(imageWrapper);

        // then
        Assert.assertEquals(histogram.length, 256);
        Assert.assertEquals(histogram[0], 2);
        Assert.assertEquals(histogram[1], 3);
        Assert.assertEquals(histogram[2], 1);
    }
}