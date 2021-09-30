/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.openimaj.imageprocessing.wrapper;

import org.openimaj.image.MBFImage;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Implementation of a {@link ImageWrapper} for OpenIMAJ's {@link MBFImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class MBFImageWrapper extends AbstractImageWrapper<MBFImage> {

    protected MBFImageWrapper(MBFImage image, ChannelType channelType) {
        super(image);
        this.channelType = channelType;
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
    public int getChannels() {
        return image.colourSpace.getNumBands();
    }

    @Override
    public double getValue(int x, int y, int channel) {
        double value = image.getPixel(x,y)[channel];
        return channelType.scaleToChannel(value, channel,0, 1);
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        Float[] pixel = image.getPixel(x, y);
        pixel[channel] = (float) channelType.scaleFromChannel(val, channel,0, 1);
        image.setPixel(x,y, pixel);
    }

    @Override
    public Class<MBFImage> getSupportedType() {
        return MBFImage.class;
    }
}
