/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.color;

import lombok.NonNull;
import science.aist.imaging.api.domain.wrapper.ChannelType;

import java.util.Arrays;

/**
 * <p>Class representing a color value</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class Color {
    @NonNull
    protected double[] channels;

    public Color(double... channels) {
        this.channels = channels;
    }

    protected Color(int n) {
        this.channels = new double[n];
    }

    public Color() {
        channels = new double[0];
    }

    /**
     * Checks if color is compatible with channel type
     *
     * @param channelType to be checked
     * @return true iff compatible
     */
    public boolean isCompatibleWithType(ChannelType channelType) {
        return channelType.getNumberOfChannels() == getNumberOfChannels();
    }

    /**
     * @return of channels of this color value
     */
    public int getNumberOfChannels() {
        return channels.length;
    }

    /**
     * @param c channel to be accessed
     * @return channel value
     */
    public double getChannel(int c) {
        if (c > channels.length - 1 || c < 0) {
            throw new IllegalArgumentException("Not a valid channel color");
        }
        return channels[c];
    }

    /**
     * @return a copy of the color's channels
     */
    public double[] getChannels() {
        return Arrays.copyOf(channels, channels.length);
    }

    /**
     * @return a copy of the color's channels casted to short
     */
    public short[] getChannelsShort() {
        short[] shorts = new short[channels.length];
        for (int i = 0; i < channels.length; i++) {
            shorts[i] = (short) channels[i];
        }
        return shorts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Arrays.equals(channels, color.channels);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(channels);
    }
}
