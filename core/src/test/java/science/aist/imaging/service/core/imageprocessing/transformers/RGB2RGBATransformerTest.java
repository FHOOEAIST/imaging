/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.api.typecheck.TypeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * <p>Tests {@link RGB2RGBATransformer}</p>
 *
 * @author Andreas Pointner
 */
public class RGB2RGBATransformerTest {
    @Test
    void testFromSuccess() {
        // given
        ImageWrapper<short[][][]> rgba = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGBA,
                new short[][][]{
                        new short[][]{
                                new short[]{1, 2, 3, 19},
                                new short[]{4, 5, 6, 20},
                        },
                        new short[][]{
                                new short[]{7, 8, 9, 21},
                                new short[]{10, 11, 12, 22},
                        },
                        new short[][]{
                                new short[]{13, 14, 15, 23},
                                new short[]{16, 17, 18, 24},
                        }
                }
        );

        // when
        ImageWrapper<short[][][]> rgb = new RGB2RGBATransformer<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class), TypeBasedImageFactoryFactory.getImageFactory(short[][][].class)).transformTo(rgba);

        // then
        Assert.assertNotNull(rgb);
        Assert.assertEquals(rgb.getHeight(), 3);
        Assert.assertEquals(rgb.getWidth(), 2);
        Assert.assertEquals(rgb.getChannelType(), ChannelType.RGB);
        Assert.assertTrue(Arrays.deepEquals(rgb.getImage(),
                new short[][][]{
                        new short[][]{
                                new short[]{1, 2, 3},
                                new short[]{4, 5, 6},
                        },
                        new short[][]{
                                new short[]{7, 8, 9},
                                new short[]{10, 11, 12},
                        },
                        new short[][]{
                                new short[]{13, 14, 15},
                                new short[]{16, 17, 18},
                        }
                }
        ));
    }

    @Test
    void testToSuccess() {
        // given
        ImageWrapper<short[][][]> rgb = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB,
                new short[][][]{
                        new short[][]{
                                new short[]{1, 2, 3},
                                new short[]{4, 5, 6},
                        },
                        new short[][]{
                                new short[]{7, 8, 9},
                                new short[]{10, 11, 12},
                        },
                        new short[][]{
                                new short[]{13, 14, 15},
                                new short[]{16, 17, 18},
                        }
                }
        );

        // when
        ImageWrapper<short[][][]> rgba = new RGB2RGBATransformer<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class), TypeBasedImageFactoryFactory.getImageFactory(short[][][].class)).transformFrom(rgb);

        // then
        Assert.assertNotNull(rgba);
        Assert.assertEquals(rgba.getHeight(), 3);
        Assert.assertEquals(rgba.getWidth(), 2);
        Assert.assertEquals(rgba.getChannelType(), ChannelType.RGBA);
        Assert.assertTrue(Arrays.deepEquals(rgba.getImage(),
                new short[][][]{
                        new short[][]{
                                new short[]{1, 2, 3, 255},
                                new short[]{4, 5, 6, 255},
                        },
                        new short[][]{
                                new short[]{7, 8, 9, 255},
                                new short[]{10, 11, 12, 255},
                        },
                        new short[][]{
                                new short[]{13, 14, 15, 255},
                                new short[]{16, 17, 18, 255},
                        }
                })
        );
    }

    @Test(expectedExceptions = TypeException.class)
    void testFromFail() {
        // given
        ImageWrapper<short[][][]> rgb = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB);

        // when
        ImageWrapper<short[][][]> rgba = new RGB2RGBATransformer<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class), TypeBasedImageFactoryFactory.getImageFactory(short[][][].class)).transformTo(rgb);

        // then
        // Exception
    }

    @Test(expectedExceptions = TypeException.class)
    void testToFail() {
        // given
        ImageWrapper<short[][][]> rgba = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGBA);

        // when
        ImageWrapper<short[][][]> rgb = new RGB2RGBATransformer<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class), TypeBasedImageFactoryFactory.getImageFactory(short[][][].class)).transformFrom(rgba);

        // then
        // Exception
    }
}
