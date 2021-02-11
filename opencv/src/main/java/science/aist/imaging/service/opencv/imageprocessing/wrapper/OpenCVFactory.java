/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;

/**
 * <p>Implements the {@link ImageFactory} interface for {@link Mat}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVFactory implements ImageFactory<Mat> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link TypeBasedImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = Mat.class} for this specific factory.
     */
    public OpenCVFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<Mat> getImage(int height, int width, ChannelType channel) {
        int type;
        switch (channel) {
            case BGRA:
            case RGBA:
            case UNKNOWN_4_CHANNEL:
                type = CvType.CV_8UC4;
                break;
            case GREYSCALE:
            case BINARY:
                type = CvType.CV_8UC1;
                break;
            default:
                type = CvType.CV_8UC3;
        }

        return getImage(height, width, type, channel);
    }

    public ImageWrapper<Mat> getImage(int height, int width, int channelType, ChannelType channel) {
        return new OpenCVImageWrapper(Mat.zeros(height, width, channelType), channel);
    }

    public ImageWrapper<Mat> getImage(int height, int width, int channelType) {
        ChannelType type;
        if (CvType.channels(channelType) == 1) {
            type = ChannelType.GREYSCALE;
        } else if (CvType.channels(channelType) == 2) {
            type = ChannelType.UNKNOWN_2_CHANNEL;
        } else if (CvType.channels(channelType) == 3) {
            type = ChannelType.UNKNOWN_3_CHANNEL;
        } else if (CvType.channels(channelType) == 4) {
            type = ChannelType.UNKNOWN_4_CHANNEL;
        } else {
            type = ChannelType.UNKNOWN;
        }

        return new OpenCVImageWrapper(Mat.zeros(height, width, channelType), type);
    }

    /**
     * Encapsulates the given image
     *
     * @param height  not used
     * @param width   not used
     * @param channel channel type of the image
     * @param image   the data which should be encapsulated in the image
     * @return Wrapper of opencv image
     */
    @Override
    public ImageWrapper<Mat> getImage(int height, int width, ChannelType channel, Mat image) {
        return getImage(image, channel);
    }

    /**
     * Encapsulates the given image
     *
     * @param image to be wrapped
     * @return wrapped image
     */
    @Override
    public ImageWrapper<Mat> getImage(Mat image) {
        return getImage(image, ChannelType.makeChannelType(image.type()));
    }

    @Override
    public Class<Mat> getSupportedType() {
        return Mat.class;
    }

    /**
     * Factory method for getting an opencv image
     *
     * @param mat         opencv mat image
     * @param channelType channel type of the image
     * @return returns a opencv image with given type and size
     */
    public ImageWrapper<Mat> getImage(Mat mat, ChannelType channelType) {
        return new OpenCVImageWrapper(mat, channelType);
    }
}
