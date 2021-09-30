/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.conversion;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.Mat;

import java.util.stream.IntStream;

/**
 * <p>Inverts a given Image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVInvertFunction implements ImageFunction<Mat, Mat> {
    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        Mat in = matImageWrapper.getImage();
        Mat m = new Mat(in.size(), in.type());

        // by Andreas Pointner: OpenCV invert won't work and I have no idea why ... (yes I converted it into CV_32F, nevertheless opencv assertion fail, and said it isn't CV_32F, but I had an output a line before which said, that it is CV_32F)
        byte[] b = new byte[(int) in.total() * in.channels()];
        in.get(0, 0, b);
        IntStream.range(0, b.length).parallel().forEach(i -> b[i] ^= 0xFF);
        m.put(0, 0, b);

        return ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(m, ChannelType.HSV);
    }
}
