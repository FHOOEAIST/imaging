/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.javacv.imageprocessing.wrapper;

import org.bytedeco.opencv.opencv_core.Mat;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import static org.bytedeco.opencv.global.opencv_core.*;

/**
 * <p>Implements the {@link ImageFactory} interface for {@link Mat}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaCVFactory implements ImageFactory<Mat> {
    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = Mat.class} for this specific factory.
     */
    public JavaCVFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<Mat> getImage(int height, int width, ChannelType channel) {
        int type;
        switch (channel) {
            case BGRA:
            case RGBA:
            case UNKNOWN_4_CHANNEL:
                type = CV_8UC4;
                break;
            case GREYSCALE:
            case BINARY:
                type = CV_8UC1;
                break;
            default:
                type = CV_8UC3;
        }
        return getImage(new Mat(height, width, type));
    }

    public ImageWrapper<Mat> getImage(int height, int width, int channelType, ChannelType channel) {
        return getImage(new Mat(height, width, channelType), channel);
    }

    public ImageWrapper<Mat> getImage(int height, int width, int channelType) {
        ChannelType type;
        if (channels(channelType) == 1) {
            type = ChannelType.GREYSCALE;
        } else if (channels(channelType) == 2) {
            type = ChannelType.UNKNOWN_2_CHANNEL;
        } else if (channels(channelType) == 3) {
            type = ChannelType.UNKNOWN_3_CHANNEL;
        } else if (channels(channelType) == 4) {
            type = ChannelType.UNKNOWN_4_CHANNEL;
        } else {
            type = ChannelType.UNKNOWN;
        }

        return getImage(new Mat(height, width, channelType), type);
    }

    /**
     * Determines number of channels for given type
     * @param type for which number of channels should be determined
     * @return number of channels
     */
    private static int channels(int type) {
        return (type >> 3) + 1;
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
        return new JavaCVImageWrapper(mat, channelType);
    }
}