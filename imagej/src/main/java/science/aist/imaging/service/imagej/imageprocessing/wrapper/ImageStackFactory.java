/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.imagej.imageprocessing.wrapper;

import ij.ImageStack;
import ij.process.ColorProcessor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageFactory} for ImageJ's {@link ImageStack}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ImageStackFactory implements ImageFactory<ImageStack> {
    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link TypeBasedImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = ImageStack.class} for this specific factory.
     */
    public ImageStackFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<ImageStack> getImage(int height, int width, ChannelType channel) {
        ImageStack imageStack = new ImageStack(width, height, channel.getNumberOfChannels());
        for (int c = 1; c < channel.getNumberOfChannels() + 1; c++) {
            imageStack.setProcessor(new ColorProcessor(width, height), c);
        }

        return getImage(height, width, channel, imageStack);
    }

    @Override
    public ImageWrapper<ImageStack> getImage(int height, int width, ChannelType channel, ImageStack image) {
        if (height != image.getHeight()) {
            throw new IllegalArgumentException("Height does not match the given image processor");
        }

        if (width != image.getWidth()) {
            throw new IllegalArgumentException("Width does not match the given image processor");
        }

        if (channel.getNumberOfChannels() != image.getSize()) {
            throw new IllegalArgumentException("Channeltype does not match stack size");
        }

        return new ImageStackWrapper(image, channel);
    }

    @Override
    public ImageWrapper<ImageStack> getImage(ImageStack image) {
        int size = image.getSize();
        ChannelType c;
        if (size == 4) {
            c = ChannelType.UNKNOWN_4_CHANNEL;
        } else if (size == 3) {
            c = ChannelType.UNKNOWN_3_CHANNEL;
        } else if (size == 2) {
            c = ChannelType.UNKNOWN_2_CHANNEL;
        } else if (size == 1) {
            c = ChannelType.GREYSCALE;
        } else {
            c = ChannelType.UNKNOWN;
        }
        return getImage(image.getHeight(), image.getWidth(), c, image);
    }

    @Override
    public Class<ImageStack> getSupportedType() {
        return ImageStack.class;
    }
}
