/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.service.opencv.imageprocessing.compare.OpenCVImageCompareFunction;
import lombok.CustomLog;
import org.opencv.core.Mat;

import java.util.Objects;

/**
 * <p>Implementation of the ImageWrapper Interface for opencv</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
public class OpenCVImageWrapper extends AbstractImageWrapper<Mat> {
    protected static final OpenCVImageCompareFunction compare = new OpenCVImageCompareFunction();

    OpenCVImageWrapper(Mat image) {
        super(image);
    }

    OpenCVImageWrapper(Mat image, ChannelType channelType) {
        super(image);
        this.channelType = channelType;
    }

    /**
     * @return The image width
     */
    @Override
    public int getWidth() {
        return image.width();
    }

    /**
     * @return The image height
     */
    @Override
    public int getHeight() {
        return image.height();
    }

    /**
     * @return The number of channels
     */
    @Override
    public int getChannels() {
        return image.channels();
    }

    /**
     * Calls the releases the allocated image.
     * Do not use further methods after calling this function
     */
    @Override
    public void close() {
        image.release();
        super.close();
    }

    @Override
    public double getValue(int x, int y, int channel) {
        return image.get(y, x)[channel];
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        double[] tmp = image.get(y, x);
        tmp[channel] = val;
        image.put(y, x, tmp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return (obj instanceof OpenCVImageWrapper) ? compare.test(this, (OpenCVImageWrapper) obj) : super.equals(obj);
    }

    /**
     * Generated Code
     *
     * @return hashCode for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(image, channelType, closed);
    }

    @Override
    public double[] getValues(int x, int y) {
        return image.get(y, x);
    }

    @Override
    public void setValues(int x, int y, double[] values) {
        image.put(y, x, values);
    }

    @Override
    public Class<Mat> getSupportedType() {
        return Mat.class;
    }
}
