/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.openimaj.imageprocessing.wrapper;

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

    /**
     * maxValue of the channel type; required to convert value between 0.0 and 1.0 for OpenIMAJ
     */
    private final double[] maxVal;

    /**
     * minValue of the channel type; required to convert value between 0.0 and 1.0 for OpenIMAJ
     */
    private final double[] minVal;


    protected MBFImageWrapper(MBFImage image, ChannelType channelType) {
        super(image);
        this.channelType = channelType;
        this.minVal = channelType.getMinVal();
        this.maxVal = channelType.getMaxVal();
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
        return image.getPixel(x,y)[channel] * maxVal[channel] + minVal[channel];
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        Float[] pixel = image.getPixel(x, y);
        pixel[channel] = (float) (val / maxVal[channel] - minVal[channel]);
        image.setPixel(x,y, pixel);
    }
}
