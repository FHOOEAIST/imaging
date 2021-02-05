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
 * <p>Class the represents an RGB color</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@ToString
public class RGBColor extends Color implements ThreeChannelColor {
    /**
     * White RGB Color
     */
    public static final transient RGBColor WHITE = new RGBColor(255, 255, 255);
    /**
     * Black RGB Color
     */
    public static final transient RGBColor BLACK = new RGBColor(0, 0, 0);
    /**
     * Red RGB Color
     */
    public static final transient RGBColor RED = new RGBColor(255, 0, 0);
    /**
     * Green RGB Color
     */
    public static final transient RGBColor GREEN = new RGBColor(0, 255, 0);
    /**
     * Blue RGB Color
     */
    public static final transient RGBColor BLUE = new RGBColor(0, 0, 255);

    public RGBColor() {
        super(3);
    }

    public RGBColor(double red, double green, double blue) {
        super(red, green, blue);
        checkValue(red);
        checkValue(green);
        checkValue(blue);
    }

    public RGBColor(int red, int green, int blue) {
        super(red, green, blue);
        checkValue(red);
        checkValue(green);
        checkValue(blue);
    }

    public RGBColor(int rgbRepresentation) {
        this();
        RGBColor color = createRGBColor(rgbRepresentation);
        channels[0] = color.getRed();
        channels[1] = color.getGreen();
        channels[2] = color.getBlue();
    }

    /**
     * @param color rgb color representation int in form
     * @return a new rgbColor object
     */
    public static RGBColor createRGBColor(int color) {
        return new RGBColor((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff);
    }

    /**
     * @param rgbColor RGBColor object
     * @return converts into a int value which represents the rgb color value
     */
    public static int createColorRepresentation(RGBColor rgbColor) {
        return ((int) rgbColor.getBlue() + ((int) rgbColor.getGreen() << 8 & 0xff00) + ((int) rgbColor.getRed() << 16 & 0xff0000));
    }

    /**
     * Lightens a color by a given amount
     *
     * @param color  The color to lighten
     * @param amount The amount to lighten the color. 0 will leave the color unchanged; 1 will make
     *               the color completely white
     * @return The bleached color
     */
    public static RGBColor lighten(RGBColor color, float amount) {
        double red = ((color.getRed() * (1 - amount) / 255.0f + amount) * 255.0f);
        double green = ((color.getGreen() * (1 - amount) / 255.0f + amount) * 255.0f);
        double blue = ((color.getBlue() * (1 - amount) / 255.0f + amount) * 255.0f);

        return new RGBColor(red, green, blue);
    }

    /**
     * Darkens a color by a given amount
     *
     * @param color  The color to darken
     * @param amount The amount to darken the color. 0 will leave the color unchanged; 1 will make
     *               the color completely black
     * @return The stained color
     */
    public static RGBColor darken(RGBColor color, float amount) {
        int red = (int) ((color.getRed() * (1 - amount) / 255.0f) * 255.0f);
        int green = (int) ((color.getGreen() * (1 - amount) / 255.0f) * 255.0f);
        int blue = (int) ((color.getBlue() * (1 - amount) / 255.0f) * 255.0f);

        return new RGBColor(red, green, blue);
    }

    private static void checkValue(double value) {
        if (value > 255.0 || value < 0.0)
            throw new IllegalArgumentException("Only a value between 0 and 255 possible. " + value + " is not between.");
    }

    public double getRed() {
        return channels[0];
    }

    public void setRed(double red) {
        checkValue(red);
        this.channels[0] = red;
    }

    public double getGreen() {
        return channels[1];
    }

    public void setGreen(double green) {
        checkValue(green);
        this.channels[1] = green;
    }

    public double getBlue() {
        return channels[2];
    }

    public void setBlue(double blue) {
        checkValue(blue);
        this.channels[2] = blue;
    }

    public int getColorRepresentation() {
        return createColorRepresentation(this);
    }

    /**
     * @return The first channel for the represented color
     */
    @Override
    public double getChannel1() {
        return getRed();
    }

    /**
     * @param channel The first channel for the represented color
     */
    @Override
    public void setChannel1(double channel) {
        setRed(channel);
    }

    /**
     * @return The second channel for the represented color
     */
    @Override
    public double getChannel2() {
        return getGreen();
    }

    /**
     * @param channel The second channel for the represented color
     */
    @Override
    public void setChannel2(double channel) {
        setGreen(channel);
    }

    /**
     * @return The third channel for the represented color
     */
    @Override
    public double getChannel3() {
        return getBlue();
    }

    /**
     * @param channel The third channel for the represented color
     */
    @Override
    public void setChannel3(double channel) {
        setBlue(channel);
    }

    @Override
    public boolean isCompatibleWithType(ChannelType channelType) {
        return channelType == ChannelType.RGB;
    }
}
