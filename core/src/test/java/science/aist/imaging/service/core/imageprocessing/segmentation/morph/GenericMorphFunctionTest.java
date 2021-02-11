/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation.morph;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.NeighborType;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Arrays;

/**
 * <p>Test class for {@link GenericMorphFunction}</p>
 *
 * @author Christoph Praschl
 */
public class GenericMorphFunctionTest {

    @Test
    public void testApply() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);
        image.setValues(0, 0, new double[]{1, 0, 0});
        image.setValues(9, 9, new double[]{0, 1, 0});
        image.setValues(9, 0, new double[]{0, 1, 0});
        image.setValues(0, 9, new double[]{1, 1, 1});

        GenericMorphFunction<short[][][], short[][][]> morphfunction = new GenericMorphFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
        morphfunction.setIsBackgroundFunction(doubles -> (int) doubles.getChannel(0) == 0 && (int) doubles.getChannel(1) == 0 && (int) doubles.getChannel(2) == 0);
        morphfunction.setNeighborMask(NeighborType.N4.getImageMask());

        // when
        ImageWrapper<short[][][]> apply = morphfunction.apply(image);

        // then
        for (int y = 0; y < apply.getHeight(); y++) {
            for (int x = 0; x < apply.getWidth(); x++) {
                int sum = (int) Arrays.stream(apply.getValues(x, y)).sum();
                if (x == 0 && y == 0 ||
                        x == 0 && y == 1 ||
                        x == 1 && y == 0 ||
                        x == 9 && y == 0 ||
                        x == 8 && y == 0 ||
                        x == 9 && y == 1 ||
                        x == 0 && y == 9 ||
                        x == 0 && y == 8 ||
                        x == 1 && y == 9 ||
                        x == 9 && y == 9 ||
                        x == 9 && y == 8 ||
                        x == 8 && y == 9
                ) {
                    Assert.assertTrue(sum > 0);
                } else {
                    Assert.assertEquals(sum, 0);
                }
            }
        }
    }

    @Test
    public void testApply2() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(9, 9, ChannelType.RGB);
        image.setValues(0, 0, new double[]{1, 0, 0});
        image.setValues(8, 8, new double[]{0, 1, 0});
        image.setValues(8, 0, new double[]{0, 1, 0});
        image.setValues(0, 8, new double[]{1, 1, 1});

        GenericMorphFunction<short[][][], short[][][]> morphfunction = new GenericMorphFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class), 7);
        morphfunction.setIsBackgroundFunction(doubles -> (int) doubles.getChannel(0) == 0 && (int) doubles.getChannel(1) == 0 && (int) doubles.getChannel(2) == 0);

        // when
        ImageWrapper<short[][][]> apply = morphfunction.apply(image);

        // then
        for (int y = 0; y < apply.getHeight(); y++) {
            for (int x = 0; x < apply.getWidth(); x++) {
                int sum = (int) Arrays.stream(apply.getValues(x, y)).sum();
                if (x == 4 || y == 4) {
                    Assert.assertEquals(sum, 0);
                } else {
                    Assert.assertTrue(sum > 0);
                }
            }
        }
    }

}
