/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.imagej.imageprocessing.wrapper;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import lombok.CustomLog;
import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.service.imagej.imageprocessing.converter.ColorConverter;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageWrapper} for ImageJ's {@link ImageProcessor}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
@CustomLog
public class ImageProcessorWrapper extends AbstractImageWrapper<ImageProcessor> {
    private static final ColorConverter converter = new ColorConverter();

    protected ImageProcessorWrapper(ImageProcessor image, ChannelType type) {
        super(image);
        this.channelType = type;

        if (channelType.getNumberOfChannels() > 3) {
            throw new IllegalArgumentException("ImageJ Imageprocessor does not support images with > 3 channels. Use an ImageStack for this case");
        }
    }

    @Override
    public int getChannels() {
        return image.getNChannels();
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
    public double getValue(int x, int y, int channel) {
        int pixel = image.getPixel(x, y);
        if(image instanceof ColorProcessor) {
            RGBColor convert = converter.convert(pixel);
            return convert.getChannel(channel);
        } else {
            return pixel;
        }
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        if(image instanceof ColorProcessor) {
            int pixel = image.getPixel(x, y);
            RGBColor convert = converter.convert(pixel);
            double[] channels = convert.getChannels();
            channels[channel] = val;
            image.putPixel(x, y, converter.convert(new Color(channels)));
        } else {
            image.putPixel(x, y, (int) val);
        }
    }

    @Override
    public Class<ImageProcessor> getSupportedType() {
        return ImageProcessor.class;
    }
}
