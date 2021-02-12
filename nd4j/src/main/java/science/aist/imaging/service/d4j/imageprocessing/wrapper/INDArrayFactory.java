/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.d4j.imageprocessing.wrapper;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageWrapper} for Deeplearning4j's {@link INDArray}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class INDArrayFactory implements ImageFactory<INDArray> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = ImageProcessor.class} for this specific factory.
     */
    public INDArrayFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<INDArray> getImage(int height, int width, ChannelType channel) {
        INDArray inputNet2 = Nd4j.zeros(height, width, channel.getNumberOfChannels());
        return getImage(height, width, channel, inputNet2);
    }

    @Override
    public ImageWrapper<INDArray> getImage(int height, int width, ChannelType channel, INDArray image) {
        if(image.shape().length != 3) {
            throw new IllegalArgumentException("Only images of shape [height, width, channels] are supported");
        }

        long[] shape = image.shape();

        if(height != shape[0]){
            throw new IllegalArgumentException("Height does not match for the given image");
        }

        if(width != shape[1]){
            throw new IllegalArgumentException("Height does not match for the given image");
        }

        if(channel.getNumberOfChannels() != image.shape()[2]){
            throw new IllegalArgumentException("The number of channels does not match the given image");
        }

        return new INDArrayWrapper(image, channel);
    }

    @Override
    public ImageWrapper<INDArray> getImage(INDArray image) {
        if(image.shape().length != 3) {
            throw new IllegalArgumentException("Only images of shape [height, width, channels] are supported");
        }

        long[] shape = image.shape();
        long numberOfChannels = shape[2];

        ChannelType c;
        if (numberOfChannels == 4) {
            c = ChannelType.UNKNOWN_4_CHANNEL;
        } else if (numberOfChannels == 3) {
            c = ChannelType.UNKNOWN_3_CHANNEL;
        } else if (numberOfChannels == 2) {
            c = ChannelType.UNKNOWN_2_CHANNEL;
        } else if (numberOfChannels == 1) {
            c = ChannelType.GREYSCALE;
        } else {
            c = ChannelType.UNKNOWN;
        }

        return getImage((int)shape[0], (int)shape[1], c, image);
    }

    @Override
    public Class<INDArray> getSupportedType() {
        return INDArray.class;
    }
}
