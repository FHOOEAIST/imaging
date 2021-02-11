/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformations;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVImageWrapperImage2ByteTransformer;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Random;

/**
 * <p>Tests for OpenCVImageWrapperImage2ByteTransformer</p>
 *
 * @author Christoph Praschl
 */
public class OpenCVImageWrapperImage2ByteTransformerTest extends OpenCVTest {
    @Test
    public void testCVImageTo2Byte() {
        // given
        InputStream is = OpenCVImageWrapperImage2ByteTransformerTest.class.getResourceAsStream("/logo/original.tif");
        openCVLoader.setColored(false);
        ImageWrapper<Mat> wrapper = openCVLoader.apply(is);
        OpenCVImageWrapperImage2ByteTransformer transformer = new OpenCVImageWrapperImage2ByteTransformer();

        // when
        ImageWrapper<short[][][]> res = transformer.transformFrom(wrapper);

        // then
        short[][][] resImage = res.getImage();

        for (int y = 0; y < wrapper.getHeight(); y++) {
            for (int x = 0; x < wrapper.getWidth(); x++) {
                for (int c = 0; c < wrapper.getChannels(); c++) {
                    Assert.assertEquals(resImage[y][x][c], (short) wrapper.getImage().get(y, x)[c]);
                }
            }
        }
    }

    @Test
    public void testTwoByteToCVImage() {
        // given
        ChannelType channelType = ChannelType.GREYSCALE;
        final int height = 500;
        final int width = 500;
        short[][][] inputImage = getSample2ByteImageData(height, width, channelType.getNumberOfChannels());
        ImageWrapper<short[][][]> i = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(height, width, channelType, inputImage);

        OpenCVImageWrapperImage2ByteTransformer transformer = new OpenCVImageWrapperImage2ByteTransformer();

        // when
        ImageWrapper<Mat> res = transformer.transformTo(i);

        // then
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    Assert.assertEquals(res.getImage().get(y, x)[c], (double) inputImage[y][x][c]);
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
}
