/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.api.util.ToBooleanBiFunction;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link GenericImageCompareFunction}</p>
 *
 * @author Andreas Pointner
 */
public class GenericImageCompareFunctionTest {

    private ToBooleanBiFunction<ImageWrapper<?>, ImageWrapper<?>> compare;

    @BeforeTest
    void setUp() {
        GenericImageCompareFunction iwc = new GenericImageCompareFunction();
        iwc.setEpsilon(0.1);
        compare = iwc;
    }

    @Test
    void testSuccess1WithSameType() {
        // given
        ImageWrapper<short[][][]> i1 = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB, new short[][][]{
                new short[][]{
                        new short[]{1, 2, 3},
                        new short[]{4, 5, 6}
                },
                new short[][]{
                        new short[]{7, 8, 9},
                        new short[]{10, 11, 12}
                },
                new short[][]{
                        new short[]{13, 14, 15},
                        new short[]{16, 17, 18}
                }
        });
        ImageWrapper<short[][][]> i2 = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB, new short[][][]{
                new short[][]{
                        new short[]{1, 2, 3},
                        new short[]{4, 5, 6}
                },
                new short[][]{
                        new short[]{7, 8, 9},
                        new short[]{10, 11, 12}
                },
                new short[][]{
                        new short[]{13, 14, 15},
                        new short[]{16, 17, 18}
                }
        });

        // when
        boolean result = compare.applyAsBoolean(i1, i2);

        // then
        Assert.assertTrue(result);
    }

    @Test
    void testSuccessWithDifferentType() {
        // given
        ImageWrapper<short[][][]> i1 = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB, new short[][][]{
                new short[][]{
                        new short[]{1, 2, 3},
                        new short[]{4, 5, 6}
                },
                new short[][]{
                        new short[]{7, 8, 9},
                        new short[]{10, 11, 12}
                },
                new short[][]{
                        new short[]{13, 14, 15},
                        new short[]{16, 17, 18}
                }
        });
        ImageWrapper<double[][][]> i2 = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(3, 2, ChannelType.RGB, new double[][][]{
                new double[][]{
                        new double[]{1, 2, 3},
                        new double[]{4, 5, 6}
                },
                new double[][]{
                        new double[]{7, 8, 9},
                        new double[]{10, 11, 12}
                },
                new double[][]{
                        new double[]{13, 14, 15},
                        new double[]{16, 17, 18}
                }
        });

        // when
        boolean result = compare.applyAsBoolean(i1, i2);

        // then
        Assert.assertTrue(result);
    }

    @Test
    void testFailureDifferentHeight() {
        // given
        ImageFactory<short[][][]> byteProvider = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> i1 = byteProvider.getImage(3, 2, ChannelType.RGB);
        ImageWrapper<double[][][]> i2 = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(4, 2, ChannelType.RGB);

        // when
        boolean result = compare.applyAsBoolean(i1, i2);

        // then
        Assert.assertFalse(result);
    }

    @Test
    void testFailureDifferentWidth() {
        // given
        ImageWrapper<short[][][]> i1 = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB);
        ImageWrapper<double[][][]> i2 = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(3, 3, ChannelType.RGB);

        // when
        boolean result = compare.applyAsBoolean(i1, i2);

        // then
        Assert.assertFalse(result);
    }

    @Test
    void testFailureDifferentChannelType() {
        // given
        ImageWrapper<short[][][]> i1 = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB);
        ImageWrapper<double[][][]> i2 = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(3, 2, ChannelType.BGR);

        // when
        boolean result = compare.applyAsBoolean(i1, i2);

        // then
        Assert.assertFalse(result);
    }
}
