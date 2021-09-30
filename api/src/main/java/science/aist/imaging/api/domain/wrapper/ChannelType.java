/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import lombok.Getter;

/**
 * <p>Enum with pixel channel types</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
public enum ChannelType {
    UNKNOWN(-1, new double[]{Double.MIN_VALUE}, new double[]{Double.MAX_VALUE}),
    UNKNOWN_2_CHANNEL(2, new double[]{Double.MIN_VALUE, Double.MIN_VALUE}, new double[]{Double.MAX_VALUE, Double.MAX_VALUE}),
    UNKNOWN_3_CHANNEL(3, new double[]{Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE}, new double[]{Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE}),
    UNKNOWN_4_CHANNEL(4, new double[]{Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE}, new double[]{Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE}),
    GREYSCALE(1, new double[]{0.0}, new double[]{255.0}),
    BINARY(1, new double[]{0.0}, new double[]{255.0}),
    BGR(3, new double[]{0.0, 0.0, 0.0}, new double[]{255.0, 255.0, 255.0}),
    RGB(3, new double[]{0.0, 0.0, 0.0}, new double[]{255.0, 255.0, 255.0}),
    HSV(3, new double[]{0.0, 0.0, 0.0}, new double[]{360.0, 1.0, 1.0}),
    BGRA(4, new double[]{0.0, 0.0, 0.0, 0.0}, new double[]{255.0, 255.0, 255.0, 1.0}),
    RGBA(4, new double[]{0.0, 0.0, 0.0, 0.0}, new double[]{255.0, 255.0, 255.0, 1.0}),
    LUV(3, new double[]{0.0, -134.0, -140.0}, new double[]{100.0, 220.0, 122.0}),
    YUV(3, new double[]{0.0, -1.0, -1.0}, new double[]{1.0, 1.0, 1.0});

    private final static String ERROR = "Given channel is out of bounds for this ChannelType.";

    /**
     * defines the number of channels of this channeltype in the imaging project
     */
    private int numberOfChannels;

    /**
     * Defines the lower bound of the channel ranges of this channeltype in the imaging project
     */
    private double[] minVal;

    /**
     * Defines the upper bound of the channel ranges of this channeltype in the imaging project
     */
    private double[] maxVal;

    ChannelType(int numberOfChannels, double[] minVal, double[] maxVal) {
        this.numberOfChannels = numberOfChannels;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    /**
     * Creates a ChannelType out of the numbers of channels
     *
     * @param channels the number of channels
     * @return the channel type matching the number of channels
     */
    public static ChannelType makeChannelType(int channels) {
        switch (channels) {
            case 1:
                return GREYSCALE;
            case 3:
                return UNKNOWN_3_CHANNEL;
            case 4:
                return UNKNOWN_4_CHANNEL;
            default:
                return UNKNOWN;
        }
    }

    /**
     * Checks if the given value is valid for the given channel in this ChannelType
     *
     * @param val     to be checked
     * @param channel in which channel is checked
     * @return true iff value is valid for the channel
     */
    public boolean isValidValue(double val, int channel) {
        if (channel < 0 || channel >= numberOfChannels) {
            throw new IllegalArgumentException(ERROR);
        }
        return val >= minVal[channel] && val <= maxVal[channel];
    }

    /**
     * Get the min val of the given channel
     * @param channel channel to get value
     * @return the min value for the channel
     */
    public double getMinVal(int channel){
        if (channel < 0 || channel >= numberOfChannels) {
            throw new IllegalArgumentException(ERROR);
        }
        return getMinVal()[channel];
    }

    /**
     * Get the max val of the given channel
     * @param channel channel to get value
     * @return the max value for the channel
     */
    public double getMaxVal(int channel){
        if (channel < 0 || channel >= numberOfChannels) {
            throw new IllegalArgumentException(ERROR);
        }
        return getMaxVal()[channel];
    }

    /**
     * Scales the given pixel value to a new range defined
     * @param value current value to be scaled
     * @param channel channel position of the value
     * @param oldMin min value of the old range associated with the current value
     * @param oldMax max value of the old range associated with the current value
     * @return pixel value within the current ChannelType definition
     * @since 2.0
     */
    public double scaleToChannel(double value, int channel, double oldMin, double oldMax) {
        return scaleToChannel(value, oldMin, oldMax, getMinVal(channel), getMaxVal(channel));
    }

    /**
     * Scales the given pixel value of this Channeltype to a new range defined
     * @param value current value to be scaled
     * @param channel channel position of the value
     * @param newMin min value of the target range associated with the resulting value
     * @param newMax max value of the target range associated with the resulting value
     * @return pixel value within the current ChannelType definition
     * @since 2.0
     */
    public double scaleFromChannel(double value, int channel, double newMin, double newMax) {
        return scaleToChannel(value, getMinVal(channel), getMaxVal(channel), newMin, newMax);
    }

    /**
     * Scales the given pixel value to a new range defined
     * @param value current value to be scaled
     * @param oldMin min value of the old range associated with the current value
     * @param oldMax max value of the old range associated with the current value
     * @param newMin min value of the new range associated with the resulting value
     * @param newMax max value of the new range associated with the resulting value
     * @return pixel value within the given target range
     * @since 2.0
     */
    public static double scaleToChannel(double value, double oldMin, double oldMax, double newMin, double newMax) {
        return (((value - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }
}
