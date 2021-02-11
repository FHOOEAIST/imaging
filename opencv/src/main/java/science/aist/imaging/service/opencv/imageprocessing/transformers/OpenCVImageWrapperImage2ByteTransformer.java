/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.Mat;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Transformer from OpenCV Image Wrapper to 2 Byte image wrapper</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVImageWrapperImage2ByteTransformer implements Transformer<ImageWrapper<Mat>, ImageWrapper<short[][][]>> {
    @Override
    @SuppressWarnings("java:S3012")
    public ImageWrapper<Mat> transformTo(ImageWrapper<short[][][]> imageWrapper) {
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        int channels = imageWrapper.getChannels();

        short[][][] image = imageWrapper.getImage();

        ChannelType ct = imageWrapper.getChannelType();

        // We do not support BGRA yet. If we need this, we will extend it.
        if (ct == ChannelType.RGB || ct == ChannelType.RGBA) {
            ct = ChannelType.BGR;
            channels = 3;
        }

        ImageWrapper<Mat> res = TypeBasedImageFactoryFactory.getImageFactory(Mat.class).getImage(height, width, ct);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double[] colors = new double[channels];

                switch (imageWrapper.getChannelType()) {
                    case RGB:
                    case RGBA: // We do not support BGRA yet. If we need this, we will extend it. We just drop the RGB channel
                        for (int c = 0; c < 3; c++) {
                            colors[2 - c] = image[y][x][c];
                        }
                        break;
                    default:
                        for (int c = 0; c < channels; c++) {
                            colors[c] = image[y][x][c];
                        }
                        break;
                }


                res.getImage().put(y, x, colors);
            }
        }

        return res;
    }

    @Override
    public ImageWrapper<short[][][]> transformFrom(ImageWrapper<Mat> matImageWrapper) {
        int width = matImageWrapper.getWidth();
        int height = matImageWrapper.getHeight();
        int channels = matImageWrapper.getChannels();

        Mat image = matImageWrapper.getImage();

        ChannelType toUse = matImageWrapper.getChannelType();
        if (toUse == ChannelType.UNKNOWN) {
            toUse = channels == 1 ? ChannelType.GREYSCALE : channels == 3 ? ChannelType.UNKNOWN_3_CHANNEL : channels == 4 ? ChannelType.UNKNOWN_4_CHANNEL : ChannelType.UNKNOWN;
        }

        ImageWrapper<short[][][]> res = TypeBasedImageFactoryFactory.getImageFactory(short[][][].class).getImage(height, width, toUse);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                short[] colors = new short[channels];
                for (int c = 0; c < channels; c++) {
                    colors[c] = (short) image.get(y, x)[c];
                }

                res.getImage()[y][x] = colors;
            }
        }

        return res;
    }
}
