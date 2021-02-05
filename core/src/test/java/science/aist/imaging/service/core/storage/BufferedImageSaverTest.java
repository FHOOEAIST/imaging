/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.storage;

import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import science.aist.imaging.service.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.api.domain.wrapper.implementation.BufferedImageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import science.aist.seshat.Logger;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * <p>Tests {@link BufferedImageSaver}</p>
 *
 * @author Andreas Pointner
 */
public class BufferedImageSaverTest {

    protected static final transient Logger log = Logger.getInstance();
    private static final String IMAGE_NAME = "tmp.png";
    private GenericImageWrapperTransformer<double[][][], BufferedImage> image8ByteToBufferedImageTransformer = new GenericImageWrapperTransformer<>(Image8ByteFactory.getInstance(), BufferedImageFactory.getInstance());

    @AfterTest
    void cleanup() {
        if (!new File(IMAGE_NAME).delete()) {
            log.warn("Could not delete File " + IMAGE_NAME);
        }
    }

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    void testGreyscale() {
        // given
        ImageWrapper<double[][][]> img = Image8ByteFactory.getInstance().getImage(1, 3, ChannelType.GREYSCALE, new double[][][]{
                new double[][]{
                        new double[]{0},
                        new double[]{127},
                        new double[]{255}
                }
        });

        BufferedImageSaver saver = new BufferedImageSaver();

        // when
        saver.accept(image8ByteToBufferedImageTransformer.transformFrom(img).getImage(), IMAGE_NAME);

        // then
        // Just check, that there is no exception ...
    }

    @Test
    void testRGB() {
        // given
        ImageWrapper<double[][][]> img = Image8ByteFactory.getInstance().getImage(1, 3, ChannelType.RGB, new double[][][]{
                new double[][]{
                        new double[]{255, 0, 0},
                        new double[]{0, 255, 0},
                        new double[]{0, 0, 255}
                }
        });

        BufferedImageSaver saver = new BufferedImageSaver();

        // when
        saver.accept(image8ByteToBufferedImageTransformer.transformFrom(img).getImage(), IMAGE_NAME);

        // then
        // Just check, that there is no exception ...
    }

    @Test
    void testBGR() {
        // given
        ImageWrapper<double[][][]> img = Image8ByteFactory.getInstance().getImage(1, 3, ChannelType.BGR, new double[][][]{
                new double[][]{
                        new double[]{0, 0, 255},
                        new double[]{0, 255, 0},
                        new double[]{255, 0, 0}
                }
        });

        BufferedImageSaver saver = new BufferedImageSaver();

        // when
        saver.accept(image8ByteToBufferedImageTransformer.transformFrom(img).getImage(), IMAGE_NAME);

        // then
        // Just check, that there is no exception ...
    }

    @Test
    void testRGBA() {
        // given
        ImageWrapper<double[][][]> img = Image8ByteFactory.getInstance().getImage(1, 3, ChannelType.RGBA, new double[][][]{
                new double[][]{
                        new double[]{255, 0, 0, 127},
                        new double[]{0, 255, 0, 127},
                        new double[]{0, 0, 255, 127}
                }
        });

        BufferedImageSaver saver = new BufferedImageSaver();

        // when
        saver.accept(image8ByteToBufferedImageTransformer.transformFrom(img).getImage(), IMAGE_NAME);

        // then
        // Just check, that there is no exception ...
    }
}
