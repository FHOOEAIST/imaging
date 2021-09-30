/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.openimaj.imageprocessing.wrapper;

import org.openimaj.image.FImage;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Implementation of a {@link ImageWrapper} for OpenIMAJ's {@link FImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class FImageWrapper extends AbstractImageWrapper<FImage> {

    /**
     * maxValue of the channel type; required to convert value between 0.0 and 1.0 for OpenIMAJ
     */
    private final double maxVal;

    /**
     * minValue of the channel type; required to convert value between 0.0 and 1.0 for OpenIMAJ
     */
    private final double minVal;

    protected FImageWrapper(FImage image, ChannelType channelType) {
        super(image);
        if (channelType != ChannelType.GREYSCALE && channelType != ChannelType.BINARY) {
            throw new IllegalArgumentException("FImage can only represent a 1-channel image");
        }
        this.channelType = channelType;
        minVal = channelType.getMinVal(0);
        maxVal = channelType.getMaxVal(0);
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
        return 1; // FImage can only store one channel
    }

    @Override
    public double getValue(int x, int y, int channel) {
        return image.getPixel(x, y) * maxVal + minVal;
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        float cval = (float)(val / maxVal - minVal);
        image.setPixel(x, y, cval);
    }

    @Override
    public Class<FImage> getSupportedType() {
        return FImage.class;
    }
}
