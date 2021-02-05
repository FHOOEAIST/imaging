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
 * <p>Implements the {@link ImageFactory} interface for 8 byte images</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class Image8ByteFactory implements ImageFactory<double[][][]> {
    private static final Image8ByteFactory instance = new Image8ByteFactory();

    private Image8ByteFactory() {
    }

    /**
     * @return the instance of this {@link ImageFactory} implementation
     */
    public static ImageFactory<double[][][]> getInstance() {
        return instance;
    }

    @Override
    public ImageWrapper<double[][][]> getImage(int height, int width, ChannelType channel) {
        return new Image8Byte(width, height, channel);
    }

    @Override
    public ImageWrapper<double[][][]> getImage(int height, int width, ChannelType channel, double[][][] image) {
        return new Image8Byte(width, height, channel, image);
    }

    @Override
    public ImageWrapper<double[][][]> getImage(double[][][] image) {
        int height = image.length;

        if (height == 0) {
            return new Image8Byte(0, 0, ChannelType.UNKNOWN, image);
        }

        int width = image[0].length;

        if (width == 0) {
            return new Image8Byte(0, height, ChannelType.UNKNOWN, image);
        }

        int channels = image[0][0].length;

        if (channels == 0) {
            return new Image8Byte(width, height, ChannelType.UNKNOWN, image);
        }

        return new Image8Byte(width, height, ChannelType.makeChannelType(channels), image);
    }
}