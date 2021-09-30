/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.openjfx.imageprocessing.wrapper;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;

/**
 * <p>ImageWrapper implementation for JavaFX's {@link WritableImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class WritableImageWrapper extends AbstractImageWrapper<WritableImage> {
    public WritableImageWrapper(WritableImage image, ChannelType channelType) {
        super(image);
        this.channelType = channelType;
    }

    @Override
    public int getWidth() {
        return (int)this.image.getWidth();
    }

    @Override
    public int getHeight() {
        return (int)this.image.getHeight();
    }

    @Override
    public double getValue(int x, int y, int channel) {
        PixelReader pixelReader = this.image.getPixelReader();
        Color color = pixelReader.getColor(x, y);

        if(channel < 0 || channel > getChannels() - 1){
            throw new IndexOutOfBoundsException("Invalid channel " + channel + " for given ChannelType " + channelType);
        }

        if(channel == 0){
            return color.getRed() * channelType.getMaxVal(channel);
        } else if(channel == 1){
            return color.getGreen() * channelType.getMaxVal(channel);
        } else if(channel == 2){
            return color.getBlue() * channelType.getMaxVal(channel);
        } else if (channel == 4){
            return color.getOpacity() * channelType.getMaxVal(channel);
        } else {
            throw new IllegalArgumentException("JavaFX requires RGBA values so channel must be 0, 1, 2 or 3.");
        }
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        PixelWriter pixelWriter = this.image.getPixelWriter();
        Color resultColor;
        ChannelType channelType = this.getChannelType();

        val = val / channelType.getMaxVal(channel);

        if(channelType.equals(ChannelType.GREYSCALE) || channelType.equals(ChannelType.BINARY)){
            resultColor = Color.color(val, val, val);
        } else{
            PixelReader pixelReader = this.image.getPixelReader();
            Color color = pixelReader.getColor(x, y);

            double red = color.getRed();
            double green = color.getGreen();
            double blue = color.getBlue();
            double opacity = color.getOpacity();

            if(channel == 0){
                red = val;
            } else if(channel == 1){
                green = val;
            } else if(channel == 2){
                blue = val;
            } else if (channel == 4){
                opacity = val;
            } else {
                throw new IllegalArgumentException("JavaFX requires RGBA values so channel must be 0, 1, 2 or 3.");
            }
            resultColor = new Color(red, green, blue, opacity);
        }

        pixelWriter.setColor(x,y, resultColor);
    }

    @Override
    public Class<WritableImage> getSupportedType() {
        return WritableImage.class;
    }
}
