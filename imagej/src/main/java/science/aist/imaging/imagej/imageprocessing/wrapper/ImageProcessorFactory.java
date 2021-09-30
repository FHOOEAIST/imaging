/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.imagej.imageprocessing.wrapper;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageFactory} for ImageJ's {@link ImageProcessor}</p>
 * <p>Note that ImageJ saves pixels as integers using bit shifting. So if you need more than 3 channels take {@link ImageStackFactory}.</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ImageProcessorFactory implements ImageFactory<ImageProcessor> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = ImageProcessor.class} for this specific factory.
     */
    public ImageProcessorFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<ImageProcessor> getImage(int height, int width, ChannelType channel) {
        return getImage(height, width, channel, new ColorProcessor(width, height));
    }

    @Override
    public ImageWrapper<ImageProcessor> getImage(int height, int width, ChannelType channel, ImageProcessor image) {
        if (height != image.getHeight()) {
            throw new IllegalArgumentException("Height does not match the given image processor");
        }

        if (width != image.getWidth()) {
            throw new IllegalArgumentException("Width does not match the given image processor");
        }

        if(image.getNChannels() != channel.getNumberOfChannels()){
            throw new IllegalArgumentException("Channeltype does not match the given image processor");
        }

        return new ImageProcessorWrapper(image, channel);
    }

    @Override
    public ImageWrapper<ImageProcessor> getImage(ImageProcessor image) {
        return getImage(image.getHeight(), image.getWidth(), ChannelType.RGB, image);
    }

    @Override
    public Class<ImageProcessor> getSupportedType() {
        return ImageProcessor.class;
    }
}
