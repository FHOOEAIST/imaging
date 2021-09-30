/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Cleanup;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Calculates the morphological skeleton of an given image. Only works with binary images as an input
 * http://felix.abecassis.me/2011/09/opencv-morphological-skeleton/</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVMorphologicalSkeletonFunction implements ImageFunction<Mat, Mat> {
    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        Mat img = matImageWrapper.getImage();
        if (matImageWrapper.getChannelType() != ChannelType.BINARY || img.type() != CvType.CV_8UC1)
            throw new IllegalArgumentException("Only Binary as 8UC1 allowed");
        Mat skeleton = new Mat(img.size(), CvType.CV_8UC1, new Scalar(0));
        @Cleanup("release") Mat temp = new Mat();
        @Cleanup("release") Mat eroded = new Mat();
        @Cleanup("release") Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));

        do {
            Imgproc.erode(img, eroded, element);
            Imgproc.dilate(eroded, temp, element);
            Core.subtract(img, temp, temp);
            Core.bitwise_or(skeleton, temp, skeleton);
            eroded.copyTo(img);
        } while (Core.countNonZero(img) != 0);


        return ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(skeleton, ChannelType.BINARY);
    }
}
