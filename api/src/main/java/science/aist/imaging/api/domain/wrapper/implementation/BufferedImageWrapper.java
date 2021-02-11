/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>Wrapper of an buffered image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BufferedImageWrapper implements ImageWrapper<BufferedImage> {
    @NonNull
    private transient BufferedImage image;

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public ChannelType getChannelType() {
        return BufferedImageType.getForId(image.getType()).getAssociatedType();
    }

    @Override
    public void close() {
        // nothing to do
    }

    /**
     * Generally see {@link ImageWrapper#getValue(int, int, int)}}
     * Note: Getter is based on the {@link ImageWrapper#getChannelType()} NOT on the actual {@link BufferedImage#getType()}
     *
     * @param x       the x-coordinate of the pixel
     * @param y       the y-coordinate of the pixel
     * @param channel the channel to select of a specific pixel
     * @return the value at the given position
     */
    @SuppressWarnings({"java:S131"})
    @Override
    public double getValue(int x, int y, int channel) {
        BufferedImageType type = BufferedImageType.getForId(image.getType());
        Color c = new Color(image.getRGB(x, y));

        switch (type) {
            case TYPE_3BYTE_BGR:
            case TYPE_INT_BGR:
                switch (channel) {
                    case 0:
                        return c.getBlue();
                    case 1:
                        return c.getGreen();
                    case 2:
                        return c.getRed();
                }
                break;
            case TYPE_4BYTE_ABGR:
            case TYPE_4BYTE_ABGR_PRE:
                switch (channel) {
                    case 0:
                        return c.getBlue();
                    case 1:
                        return c.getGreen();
                    case 2:
                        return c.getRed();
                    case 3:
                        return c.getAlpha();
                }
                break;
            case TYPE_BYTE_BINARY:
            case TYPE_BYTE_GRAY:
            case TYPE_USHORT_GRAY:
                if (channel == 0) {
                    return c.getRed();
                }
                break;
            case TYPE_USHORT_555_RGB:
            case TYPE_USHORT_565_RGB:
            case TYPE_INT_RGB:
                switch (channel) {
                    case 0:
                        return c.getRed();
                    case 1:
                        return c.getGreen();
                    case 2:
                        return c.getBlue();
                }
                break;
            case TYPE_INT_ARGB:
            case TYPE_INT_ARGB_PRE:
                switch (channel) {
                    case 0:
                        return c.getRed();
                    case 1:
                        return c.getGreen();
                    case 2:
                        return c.getBlue();
                    case 3:
                        return c.getAlpha();
                }
                break;
        }

        throw new IllegalArgumentException("Can't access the given channel on the wrapped image of type " + type);
    }

    /**
     * Generally see {@link ImageWrapper#setValue(int, int, int, double)}
     * Note: Setter is based on the {@link ImageWrapper#getChannelType()} NOT on the actual {@link BufferedImage#getType()}
     *
     * @param x       the x-coordinate of the pixel
     * @param y       the y-coordinate of the pixel
     * @param channel the channel to select of a specific pixel
     * @param val     the value for the pixel and channel
     */
    @Override
    @SuppressWarnings({"java:S131"})
    public void setValue(int x, int y, int channel, double val) {
        val = val + 0.5;

        int value;
        if (val < 0) {
            value = 0;
        } else if (val > 255) {
            value = 255;
        } else {
            value = (int) val;
        }

        BufferedImageType type = BufferedImageType.getForId(image.getType());
        Color c = new Color(image.getRGB(x, y));

        Color newColor = null;
        switch (type) {
            case TYPE_3BYTE_BGR:
            case TYPE_INT_BGR:
                switch (channel) {
                    case 0:
                        newColor = new Color(c.getRed(), c.getGreen(), value);
                        break;
                    case 1:
                        newColor = new Color(c.getRed(), value, c.getBlue());
                        break;
                    case 2:
                        newColor = new Color(value, c.getGreen(), c.getBlue());
                        break;
                }
                break;

            case TYPE_4BYTE_ABGR:
            case TYPE_4BYTE_ABGR_PRE:
                switch (channel) {
                    case 0:
                        newColor = new Color(c.getRed(), c.getGreen(), value, c.getAlpha());
                        break;
                    case 1:
                        newColor = new Color(c.getRed(), value, c.getBlue(), c.getAlpha());
                        break;
                    case 2:
                        newColor = new Color(value, c.getGreen(), c.getBlue(), c.getAlpha());
                        break;
                    case 3:
                        newColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), value);
                        break;
                }
                break;

            case TYPE_BYTE_BINARY:
            case TYPE_BYTE_GRAY:
            case TYPE_USHORT_GRAY:
                if (channel == 0) {
                    newColor = new Color(value, value, value);
                }
                break;

            case TYPE_USHORT_555_RGB:
            case TYPE_USHORT_565_RGB:
            case TYPE_INT_RGB:
                switch (channel) {
                    case 0:
                        newColor = new Color(value, c.getGreen(), c.getBlue());
                        break;
                    case 1:
                        newColor = new Color(c.getRed(), value, c.getBlue());
                        break;
                    case 2:
                        newColor = new Color(c.getRed(), c.getGreen(), value);
                        break;
                }
                break;
            case TYPE_INT_ARGB:
            case TYPE_INT_ARGB_PRE:
                switch (channel) {
                    case 0:
                        newColor = new Color(value, c.getGreen(), c.getBlue(), c.getAlpha());
                        break;
                    case 1:
                        newColor = new Color(c.getRed(), value, c.getBlue(), c.getAlpha());
                        break;
                    case 2:
                        newColor = new Color(c.getRed(), c.getGreen(), value, c.getAlpha());
                        break;
                    case 3:
                        newColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), value);
                        break;
                }
                break;
        }

        if (newColor == null) {
            throw new IllegalArgumentException("Can't set the given channel on the wrapped image of type " + type);
        }

        image.setRGB(x, y, newColor.getRGB());
    }
}
