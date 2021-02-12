/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.d4j.imageprocessing.wrapper;

import org.nd4j.linalg.api.ndarray.INDArray;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Implementation of a {@link ImageWrapper} for Deeplearning4j's {@link INDArray}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class INDArrayWrapper extends AbstractImageWrapper<INDArray> {
    protected INDArrayWrapper(INDArray image, ChannelType type) {
        super(image);
        channelType = type;
    }

    @Override
    public void close() {
        image.close();
    }

    @Override
    public int getWidth() {
        return (int)image.shape()[0];
    }

    @Override
    public int getHeight() {
        return (int)image.shape()[1];
    }

    @Override
    public int getChannels() {
        return (int)image.shape()[2];
    }

    @Override
    public double getValue(int x, int y, int channel) {
            return image.getDouble(y, x, channel);
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
            image.putScalar(new int[]{y, x, channel}, val);
    }
}
