/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.javacv.imageprocessing.wrapper;


import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.opencv.opencv_core.Mat;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;

/**
 * <p>Implementation of the ImageWrapper Interface for <a href="https://github.com/bytedeco/javacv">JavaCV</a></p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaCVImageWrapper extends AbstractImageWrapper<Mat> {
    JavaCVImageWrapper(Mat image, ChannelType type) {
        super(image);
        this.channelType = type;
    }

    @Override
    public int getWidth() {
        return image.cols();
    }

    @Override
    public int getHeight() {
        return image.rows();
    }

    @Override
    public double getValue(int x, int y, int channel) {
        try (UByteRawIndexer dstIdx = image.createIndexer()) {
            return dstIdx.get(y, x, channel);
        }
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        try (UByteRawIndexer dstIdx = image.createIndexer()) {
            dstIdx.put(y, x, channel, (byte)val);
        }
    }

    @Override
    public Class<Mat> getSupportedType() {
        return Mat.class;
    }

    @Override
    public void close() {
        image.close();
    }
}
