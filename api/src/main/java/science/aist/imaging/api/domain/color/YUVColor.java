/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.color;

import lombok.Getter;
import science.aist.imaging.api.domain.wrapper.ChannelType;

/**
 * <p>Class representing a YUV-color.</p>
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
@Getter
public class YUVColor extends Color implements ThreeChannelColor {

    /**
     * White HSV Color
     */
    public static final transient YUVColor WHITE = new YUVColor(1.000, 0.001, 0.000);
    /**
     * Black HSV Color
     */
    public static final transient YUVColor BLACK = new YUVColor(0, 0, 0);
    /**
     * Red HSV Color
     */
    public static final transient YUVColor RED = new YUVColor(0.299, -0.147, 0.615);
    /**
     * Green HSV Color
     */
    public static final transient YUVColor GREEN = new YUVColor(0.587, -0.289, -0.515);
    /**
     * Blue HSV Color
     */
    public static final transient YUVColor BLUE = new YUVColor(0.114, 0.437, -0.100);

    public YUVColor() {
        super(3);
    }

    public YUVColor(double y, double u, double v) {
        super(y, u, v);
    }

    @Override
    public double getChannel1() {
        return channels[0];
    }

    @Override
    public void setChannel1(double channel) {
        setyLuma(channel);
    }

    @Override
    public double getChannel2() {
        return channels[1];
    }

    @Override
    public void setChannel2(double channel) {
        setuChroma(channel);
    }

    @Override
    public double getChannel3() {
        return channels[2];
    }

    @Override
    public void setChannel3(double channel) {
        setvChroma(channel);
    }

    public void setyLuma(double yLuma) {
        if (yLuma < 0 || yLuma > 1) {
            throw new IllegalArgumentException("Given luminance is not allowed. Must be between 0 and 1.");
        }
        this.channels[0] = yLuma;
    }

    public void setuChroma(double uChroma) {
        if (uChroma < -1 || uChroma > 1) {
            throw new IllegalArgumentException("Given chroma is not allowed. Must be between -1 and 1.");
        }
        this.channels[1] = uChroma;
    }

    public void setvChroma(double vChroma) {
        if (vChroma < -1 || vChroma > 1) {
            throw new IllegalArgumentException("Given chroma is not allowed. Must be between -1 and 1.");
        }
        this.channels[2] = vChroma;
    }

    public double getYLuma() {
        return channels[0];
    }

    public double getUChroma() {
        return channels[1];
    }

    public double getVChroma() {
        return channels[2];
    }

    @Override
    public boolean isCompatibleWithType(ChannelType channelType) {
        return channelType == ChannelType.YUV;
    }
}
