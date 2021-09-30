/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformers;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.api.typecheck.TypeException;

import java.util.Arrays;

/**
 * <p>Tests {@link RGB2BGRTransformer}</p>
 *
 * @author Andreas Pointner
 */
public class RGB2BGRTransformerTest {

    @Test
    void testFromSuccess() {
        // given
        ImageWrapper<short[][][]> bgr = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.BGR,
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
        ImageWrapper<short[][][]> rgb = new RGB2BGRTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(short[][][].class)).transformTo(bgr);

        // then
        Assert.assertNotNull(rgb);
        Assert.assertEquals(rgb.getHeight(), 3);
        Assert.assertEquals(rgb.getWidth(), 2);
        Assert.assertEquals(rgb.getChannelType(), ChannelType.RGB);
        Assert.assertTrue(Arrays.deepEquals(rgb.getImage(),
                new short[][][]{
                        new short[][]{
                                new short[]{3, 2, 1},
                                new short[]{6, 5, 4},
                        },
                        new short[][]{
                                new short[]{9, 8, 7},
                                new short[]{12, 11, 10},
                        },
                        new short[][]{
                                new short[]{15, 14, 13},
                                new short[]{18, 17, 16},
                        }
                })
        );
    }

    @Test
    void testToSuccess() {
        // given
        ImageWrapper<short[][][]> rgb = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB,
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
        ImageWrapper<short[][][]> bgr = new RGB2BGRTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(short[][][].class)).transformFrom(rgb);

        // then
        Assert.assertNotNull(bgr);
        Assert.assertEquals(bgr.getHeight(), 3);
        Assert.assertEquals(bgr.getWidth(), 2);
        Assert.assertEquals(bgr.getChannelType(), ChannelType.BGR);
        Assert.assertTrue(Arrays.deepEquals(bgr.getImage(),
                new short[][][]{
                        new short[][]{
                                new short[]{3, 2, 1},
                                new short[]{6, 5, 4},
                        },
                        new short[][]{
                                new short[]{9, 8, 7},
                                new short[]{12, 11, 10},
                        },
                        new short[][]{
                                new short[]{15, 14, 13},
                                new short[]{18, 17, 16},
                        }
                })
        );
    }

    @Test(expectedExceptions = TypeException.class)
    void testFromFail() {
        // given
        ImageWrapper<short[][][]> rgb = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.RGB);

        // when
        ImageWrapper<short[][][]> bgr = new RGB2BGRTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(short[][][].class)).transformTo(rgb);

        // then
        // Exception
    }

    @Test(expectedExceptions = TypeException.class)
    void testToFail() {
        // given
        ImageWrapper<short[][][]> bgr = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 2, ChannelType.BGR);

        // when
        ImageWrapper<short[][][]> rgb = new RGB2BGRTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(short[][][].class)).transformFrom(bgr);

        // then
        // Exception
    }
}
