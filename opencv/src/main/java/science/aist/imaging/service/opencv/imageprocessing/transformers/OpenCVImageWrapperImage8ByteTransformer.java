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
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Transformer from OpenCV Image Wrapper to 8 Byte image wrapper</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVImageWrapperImage8ByteTransformer implements Transformer<ImageWrapper<Mat>, ImageWrapper<double[][][]>> {
    @Override
    public ImageWrapper<Mat> transformTo(ImageWrapper<double[][][]> imageWrapper) {
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        int channels = imageWrapper.getChannels();

        double[][][] image = imageWrapper.getImage();

        ImageWrapper<Mat> res = ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(height, width, CvType.CV_64FC(channels));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                res.getImage().put(y, x, image[y][x]);
            }
        }

        return res;
    }

    @Override
    public ImageWrapper<double[][][]> transformFrom(ImageWrapper<Mat> matImageWrapper) {
        int width = matImageWrapper.getWidth();
        int height = matImageWrapper.getHeight();
        int channels = matImageWrapper.getChannels();

        Mat image = matImageWrapper.getImage();

        ChannelType toUse = matImageWrapper.getChannelType();
        if (toUse == ChannelType.UNKNOWN) {
            toUse = channels == 1 ? ChannelType.GREYSCALE : channels == 3 ? ChannelType.UNKNOWN_3_CHANNEL : channels == 4 ? ChannelType.UNKNOWN_4_CHANNEL : ChannelType.UNKNOWN;
        }

        ImageWrapper<double[][][]> res = ImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width, toUse);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                res.getImage()[y][x] = image.get(y, x);
            }
        }

        return res;
    }
}
