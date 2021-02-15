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
 * <p>Image wrapper for images with 8 byte precision</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
@Getter
public class Image8Byte extends AbstractImageWrapper<double[][][]> {
    private final int width;
    private final int height;

    Image8Byte(int width, int height, ChannelType channel) {
        this(width, height, channel, new double[height][width][channel.getNumberOfChannels()]);
    }

    Image8Byte(int width, int height, ChannelType channel, double[][][] imageData) {
        super(imageData);
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
        image[y][x][channel] = val;
    }

    @Override
    public boolean supportsParallelAccess() {
        return true;
    }

    @Override
    public Class<double[][][]> getSupportedType() {
        return double[][][].class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image8Byte that = (Image8Byte) o;
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

        double[][][] otherImage = that.image;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channelType.getNumberOfChannels(); c++) {
                    if (image[y][x][c] != otherImage[y][x][c]) {
                        log.info("Images are different at: x={} y={} c={}", x, y, c);
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
    public double[] getValues(int x, int y) {
        return image[y][x];
    }

    @Override
    public void setValues(int x, int y, double[] values) {
        image[y][x] = values;
    }
}
