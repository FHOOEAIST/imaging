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

import java.awt.image.BufferedImage;

/**
 * <p>Implements the {@link ImageFactory} interface for {@link BufferedImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class BufferedImageFactory implements ImageFactory<BufferedImage> {

    private static final BufferedImageFactory instance = new BufferedImageFactory();

    private BufferedImageFactory() {
    }

    /**
     * @return the instance of this {@link ImageFactory} implementation
     */
    public static ImageFactory<BufferedImage> getInstance() {
        return instance;
    }

    /**
     * Creates an imagewrapper of an buffered image with the given properties
     * Note: the getter/setter for a pixel value of the image wrapper corresponds to the wrapper's {@link ChannelType} not to the {@link BufferedImage#getType()}
     *
     * @param height height of the image
     * @param width  width of the image
     * @param type   type of the image
     * @return wrapper containing a buffered image
     */
    private static ImageWrapper<BufferedImage> getImage(int height, int width, BufferedImageType type) {
        return new BufferedImageWrapper(new BufferedImage(width, height, type.getId()));
    }

    @Override
    public ImageWrapper<BufferedImage> getImage(int height, int width, ChannelType channel) {
        return getImage(height, width, BufferedImageType.toBufferedImageType(channel));
    }

    /**
     * Prefer {@link BufferedImageFactory#getImage(BufferedImage)} since height, width and channeltype are not used since this information is provided by the image itself
     *
     * @param height  not used
     * @param width   not used
     * @param channel not used
     * @param image   the data which should be encapsulated in the image
     * @return wrapped image
     */
    @Override
    public ImageWrapper<BufferedImage> getImage(int height, int width, ChannelType channel, BufferedImage image) {
        return getImage(image);
    }

    /**
     * Creates an imagewrapper of the given buffered image
     *
     * @param image image to be wrapped
     * @return wrapped image
     */
    @Override
    public ImageWrapper<BufferedImage> getImage(BufferedImage image) {
        return new BufferedImageWrapper(image);
    }
}
