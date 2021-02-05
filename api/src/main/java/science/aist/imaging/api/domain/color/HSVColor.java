/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.color;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import lombok.ToString;

/**
 * <p>Class representing a HSVColor</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@ToString
public class HSVColor extends Color implements ThreeChannelColor {
    /**
     * White HSV Color
     */
    public static final transient HSVColor WHITE = new HSVColor(0, 0, 1.0);
    /**
     * Black HSV Color
     */
    public static final transient HSVColor BLACK = new HSVColor(0, 0, 0);
    /**
     * Red HSV Color
     */
    public static final transient HSVColor RED = new HSVColor(0, 1.0, 1.0);
    /**
     * Green HSV Color
     */
    public static final transient HSVColor GREEN = new HSVColor(120, 1.0, 1.0);
    /**
     * Blue HSV Color
     */
    public static final transient HSVColor BLUE = new HSVColor(240, 1.0, 1.0);

    public HSVColor() {
        super(3);
    }

    public HSVColor(double hue, double saturation, double value) {
        super(hue, saturation, value);
    }

    public double getHue() {
        return channels[0];
    }

    /**
     * @param hue to be set
     * @throws IllegalArgumentException if hue is smaller than 0.0 or greater than 360.0
     */
    public void setHue(double hue) {
        if (hue < 0.0 || hue > 360.0)
            throw new IllegalArgumentException("Given hue is not in range");
        this.channels[0] = hue;
    }

    public double getSaturation() {
        return channels[1];
    }

    /**
     * @param saturation The Saturation of the HSV-Color
     * @throws IllegalArgumentException if saturation is smaller than 0.0 or greater than 1.0
     */
    public void setSaturation(double saturation) {
        if (saturation < 0.0 || saturation > 1.0)
            throw new IllegalArgumentException("Given saturation is not a percent-value. Must be between 0 and 1.");
        this.channels[1] = saturation;
    }

    public double getValue() {
        return channels[2];
    }

    /**
     * @param value The Value of the HSV-Color
     * @throws IllegalArgumentException if value is smaller than 0.0 or greater than 1.0
     */
    public void setValue(double value) {
        if (value < 0.0 || value > 1.0)
            throw new IllegalArgumentException("Given value is not a percent-value. Must be between 0 and 1.");
        this.channels[2] = value;
    }

    /**
     * @return The first channel for the represented color
     */
    @Override
    public double getChannel1() {
        return getChannel(0);
    }

    /**
     * @param channel The first channel for the represented color
     */
    @Override
    public void setChannel1(double channel) {
        setHue(channel);
    }

    /**
     * @return The second channel for the represented color
     */
    @Override
    public double getChannel2() {
        return getChannel(1);
    }

    /**
     * @param channel The second channel for the represented color
     */
    @Override
    public void setChannel2(double channel) {
        setSaturation(channel);
    }

    /**
     * @return The third channel for the represented color
     */
    @Override
    public double getChannel3() {
        return getChannel(2);
    }

    /**
     * @param channel The third channel for the represented color
     */
    @Override
    public void setChannel3(double channel) {
        setValue(channel);
    }

    @Override
    public boolean isCompatibleWithType(ChannelType channelType) {
        return channelType == ChannelType.HSV;
    }
}
