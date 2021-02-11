/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;

import java.util.Random;

/**
 * <p>Tests for Image2ByteToImage8ByteTransformer</p>
 *
 * @author Christoph Praschl
 */
public class Image2ByteToImage8ByteTransformerTest {
    private static int width = 500;
    private static int height = 500;

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    public void testTwoByteTo8ByteColored() {
        // given
        ChannelType channelType = ChannelType.RGB;
        short[][][] inputImage = getSample2ByteImageData(height, width, channelType.getNumberOfChannels());
        ImageWrapper<short[][][]> i = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(height, width, channelType, inputImage);

        Image2ByteToImage8ByteTransformer transformer = new Image2ByteToImage8ByteTransformer();

        // when
        ImageWrapper<double[][][]> res = transformer.transformFrom(i);

        // then
        double[][][] doubleImage = res.getImage();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    Assert.assertEquals((double) inputImage[y][x][c], doubleImage[y][x][c]);
                }
            }
        }
    }

    @Test
    public void testTwoByteTo8ByteGreyscale() {
        // given
        ChannelType channelType = ChannelType.GREYSCALE;
        short[][][] inputImage = getSample2ByteImageData(height, width, channelType.getNumberOfChannels());
        ImageWrapper<short[][][]> i = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(height, width, channelType, inputImage);

        Image2ByteToImage8ByteTransformer transformer = new Image2ByteToImage8ByteTransformer();

        // when
        ImageWrapper<double[][][]> res = transformer.transformFrom(i);

        // then
        double[][][] doubleImage = res.getImage();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    Assert.assertEquals((double) inputImage[y][x][c], doubleImage[y][x][c]);
                }
            }
        }
    }


    @Test
    public void testEightByteTo2ByteColored() {
        // given
        ChannelType channelType = ChannelType.RGB;
        double[][][] inputImage = getSample8ByteImageData(height, width, channelType.getNumberOfChannels());
        ImageWrapper<double[][][]> i = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width, channelType, inputImage);

        Image2ByteToImage8ByteTransformer transformer = new Image2ByteToImage8ByteTransformer();

        // when
        ImageWrapper<short[][][]> res = transformer.transformTo(i);

        // then
        short[][][] doubleImage = res.getImage();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    Assert.assertEquals((short) inputImage[y][x][c], doubleImage[y][x][c]);
                }
            }
        }
    }

    @Test
    public void testEightByteTo2ByteGreyscale() {
        // given
        ChannelType channelType = ChannelType.GREYSCALE;
        double[][][] inputImage = getSample8ByteImageData(height, width, channelType.getNumberOfChannels());
        ImageWrapper<double[][][]> i = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width, channelType, inputImage);

        Image2ByteToImage8ByteTransformer transformer = new Image2ByteToImage8ByteTransformer();

        // when
        ImageWrapper<short[][][]> res = transformer.transformTo(i);

        // then
        short[][][] doubleImage = res.getImage();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    Assert.assertEquals((short) inputImage[y][x][c], doubleImage[y][x][c]);
                }
            }
        }
    }

    private short[][][] getSample2ByteImageData(int height, int width, int channels) {
        short[][][] res = new short[height][width][channels];

        Random r = new Random(13);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channels; c++) {
                    res[y][x][c] = (short) r.nextInt(255);
                }
            }
        }

        return res;
    }

    private double[][][] getSample8ByteImageData(int height, int width, int channels) {
        double[][][] res = new double[height][width][channels];

        Random r = new Random(13);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channels; c++) {
                    res[y][x][c] = r.nextInt(255);
                }
            }
        }

        return res;
    }

}
