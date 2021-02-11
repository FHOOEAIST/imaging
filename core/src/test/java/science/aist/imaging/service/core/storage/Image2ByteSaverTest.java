/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.storage;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import science.aist.seshat.Logger;

import java.io.File;

/**
 * @author Christoph Praschl
 */
public class Image2ByteSaverTest {
    private static final transient Logger log = Logger.getInstance();
    private static final String IMAGE_NAME = "tmp.png";

    @AfterTest
    void cleanup() {
        if (!new File(IMAGE_NAME).delete()) {
            log.warn("Could not delete File " + IMAGE_NAME);
        }
    }

    @Test
    public void testSaveRGB() {
        // given
        Image2ByteSaver saver = new Image2ByteSaver();
        saver.setTransformer8ByteTo2Byte(new Image2ByteToImage8ByteTransformer());

        ImageWrapper<short[][][]> img = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(1, 3, ChannelType.RGB, new short[][][]{
                new short[][]{
                        new short[]{255, 0, 0},
                        new short[]{0, 255, 0},
                        new short[]{0, 0, 255}
                }});

        // when
        saver.accept(img, IMAGE_NAME);

        // then
        File f = new File(IMAGE_NAME);
        Assert.assertTrue(f.exists() && !f.isDirectory());
    }

    @Test
    void testBGR() {
        // given
        ImageWrapper<short[][][]> img = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(1, 3, ChannelType.BGR, new short[][][]{
                new short[][]{
                        new short[]{0, 0, 255},
                        new short[]{0, 255, 0},
                        new short[]{255, 0, 0}
                }
        });

        Image2ByteSaver saver = new Image2ByteSaver();
        saver.setTransformer8ByteTo2Byte(new Image2ByteToImage8ByteTransformer());

        // when
        saver.accept(img, IMAGE_NAME);

        // then
        File f = new File(IMAGE_NAME);
        Assert.assertTrue(f.exists() && !f.isDirectory());
    }

    @Test
    void testRGBA() {
        // given
        ImageWrapper<short[][][]> img = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(1, 3, ChannelType.RGBA, new short[][][]{
                new short[][]{
                        new short[]{255, 0, 0, 127},
                        new short[]{0, 255, 0, 127},
                        new short[]{0, 0, 255, 127}
                }
        });

        Image2ByteSaver saver = new Image2ByteSaver();
        saver.setTransformer8ByteTo2Byte(new Image2ByteToImage8ByteTransformer());

        // when
        saver.accept(img, IMAGE_NAME);

        // then
        File f = new File(IMAGE_NAME);
        Assert.assertTrue(f.exists() && !f.isDirectory());
    }
}
