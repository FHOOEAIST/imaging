/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Arrays;

/**
 * <p>Test class for {@link ConvolveFunction}</p>
 *
 * @author Andreas Pointner
 */

public class ConvolveFunctionTest {

    private final ConvolveFunction<double[][][], double[][][]> convolve8Byte = new ConvolveFunction<>(ImageFactoryFactory.getImageFactory(double[][][].class));

    @Test
    public void testApply() {
        // given
        convolve8Byte.setNormalize(false);
        ImageWrapper<double[][][]> imageWrapper = ImageFactoryFactory.getImageFactory(double[][][].class).getImage(4, 3, ChannelType.GREYSCALE, new double[][][]{
                new double[][]{
                        new double[]{12},
                        new double[]{11},
                        new double[]{10}
                },
                new double[][]{
                        new double[]{9},
                        new double[]{8},
                        new double[]{7}
                },
                new double[][]{
                        new double[]{6},
                        new double[]{5},
                        new double[]{4}
                },
                new double[][]{
                        new double[]{3},
                        new double[]{2},
                        new double[]{1}
                },
        });

        double[][] kernel = new double[][]{
                new double[]{1, 2, 3},
                new double[]{4, 5, 6},
                new double[]{7, 8, 9}
        };

        // when
        ImageWrapper<double[][][]> result = convolve8Byte.apply(imageWrapper, kernel);

        // then
        Assert.assertEquals(result.getWidth(), 3);
        Assert.assertEquals(result.getHeight(), 4);
        Assert.assertTrue(Arrays.deepEquals(result.getImage(), new double[][][]{
                new double[][]{
                        new double[]{270},
                        new double[]{353},
                        new double[]{206}
                },
                new double[][]{
                        new double[]{243},
                        new double[]{300},
                        new double[]{165}
                },
                new double[][]{
                        new double[]{144},
                        new double[]{165},
                        new double[]{84}
                },
                new double[][]{
                        new double[]{54},
                        new double[]{56},
                        new double[]{26}
                },
        }));
    }
}