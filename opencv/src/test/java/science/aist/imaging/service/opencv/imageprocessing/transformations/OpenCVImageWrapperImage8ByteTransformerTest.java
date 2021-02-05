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
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVImageWrapperImage8ByteTransformer;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Random;

/**
 * <p>Tests for OpenCVImageWrapperImage8ByteTransformer</p>
 *
 * @author Christoph Praschl
 */
public class OpenCVImageWrapperImage8ByteTransformerTest extends OpenCVTest {
    @Test
    public void testCVImageTo8Byte() {
        // given
        InputStream is = OpenCVImageWrapperImage8ByteTransformerTest.class.getResourceAsStream("/logo/original.tif");
        openCVLoader.setColored(false);
        ImageWrapper<Mat> wrapper = openCVLoader.apply(is);
        OpenCVImageWrapperImage8ByteTransformer transformer = new OpenCVImageWrapperImage8ByteTransformer();

        // when
        ImageWrapper<double[][][]> res = transformer.transformFrom(wrapper);

        // then
        double[][][] resImage = res.getImage();

        for (int y = 0; y < wrapper.getHeight(); y++) {
            for (int x = 0; x < wrapper.getWidth(); x++) {
                for (int c = 0; c < wrapper.getChannels(); c++) {
                    Assert.assertEquals(resImage[y][x][c], wrapper.getImage().get(y, x)[c]);
                }
            }
        }
    }

    @Test
    public void testEightByteToCVImage() {
        // given
        ChannelType channelType = ChannelType.GREYSCALE;
        final int height = 500;
        final int width = 500;
        double[][][] inputImage = getSample8ByteImageData(height, width, channelType.getNumberOfChannels());
        ImageWrapper<double[][][]> i = Image8ByteFactory.getInstance().getImage(height, width, channelType, inputImage);

        OpenCVImageWrapperImage8ByteTransformer transformer = new OpenCVImageWrapperImage8ByteTransformer();

        // when
        ImageWrapper<Mat> res = transformer.transformTo(i);

        // then
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    Assert.assertEquals(res.getImage().get(y, x)[c], inputImage[y][x][c]);
                }
            }
        }
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
