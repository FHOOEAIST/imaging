/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.conversion;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Function for converting an opencv bgr image to a hsv image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVBGR2HSVFunction implements ImageFunction<Mat, Mat> {
    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(matImageWrapper.getImage(), hsvImage, Imgproc.COLOR_BGR2HSV);

        return ((OpenCVFactory)TypeBasedImageFactoryFactory.getImageFactory(Mat.class)).getImage(hsvImage, ChannelType.HSV);
    }
}
