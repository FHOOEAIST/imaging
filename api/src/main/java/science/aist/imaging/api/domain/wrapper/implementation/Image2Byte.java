/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import lombok.CustomLog;
import lombok.Getter;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;

import java.util.Objects;

/**
 * <p>Image wrapper for images with 2 byte precision</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
@Getter
public class Image2Byte extends AbstractImageWrapper<short[][][]> {
    private final int width;
    private final int height;

    Image2Byte(int width, int height, ChannelType channel) {
        this(width, height, channel, new short[height][width][channel.getNumberOfChannels()]);
    }

    Image2Byte(int width, int height, ChannelType channel, short[][][] image) {
        super(image);
        this.width = width;
        this.height = height;
        this.channelType = channel;
    }

    @Override
    public double getValue(int x, int y, int channel) {
        return image[y][x][channel];
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        image[y][x][channel] = (short) val;
    }

    @Override
    public boolean supportsParallelAccess() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image2Byte that = (Image2Byte) o;
        if (that.width != width) {
            log.info("Images are different in width: {} <> {}", width, that.width);
            return false;
        }
        if (that.height != height) {
            log.info("Images are different in height: {} <> {}", height, that.height);
            return false;
        }
        if (that.getChannelType() != getChannelType()) {
            log.info("Images are different in type: {} <> {}", getChannelType().name(), that.getChannelType().name());
            return false;
        }

        short[][][] otherImage = that.image;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    if (image[y][x][c] != otherImage[y][x][c]) {
                        log.info("Images are different at: x={} y={} c={} with: img1: {} and img2: {}", x, y, c, image[y][x][c], otherImage[y][x][c]);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, image);
    }

    @Override
    @SuppressWarnings("java:S3012")
    public double[] getValues(int x, int y) {
        short[] shorts = image[y][x];
        double[] result = new double[shorts.length];
        for (int i = 0; i < shorts.length; i++) {
            result[i] = shorts[i];
        }

        return result;
    }

    @Override
    public void setValues(int x, int y, double[] values) {
        short[] result = new short[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = (short) values[i];
        }

        image[y][x] = result;
    }
}
