/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Implements the {@link ImageFactory} interface for 2 byte images</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class Image2ByteFactory implements ImageFactory<short[][][]> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = short[][][].class} for this specific factory.
     */
    public Image2ByteFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<short[][][]> getImage(int height, int width, ChannelType channel) {
        return new Image2Byte(width, height, channel);
    }

    @Override
    public ImageWrapper<short[][][]> getImage(int height, int width, ChannelType channel, short[][][] image) {
        return new Image2Byte(width, height, channel, image);
    }

    @Override
    public ImageWrapper<short[][][]> getImage(short[][][] image) {
        int height = image.length;

        if (height == 0) {
            return new Image2Byte(0, 0, ChannelType.UNKNOWN, image);
        }

        int width = image[0].length;

        if (width == 0) {
            return new Image2Byte(0, height, ChannelType.UNKNOWN, image);
        }

        int channels = image[0][0].length;

        if (channels == 0) {
            return new Image2Byte(width, height, ChannelType.UNKNOWN, image);
        }

        return new Image2Byte(width, height, ChannelType.makeChannelType(channels), image);
    }

    @Override
    public Class<short[][][]> getSupportedType() {
        return short[][][].class;
    }
}
