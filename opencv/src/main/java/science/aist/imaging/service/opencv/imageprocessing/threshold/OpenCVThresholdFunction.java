/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.threshold;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVThresholdType;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Function for applying an threshold</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVThresholdFunction implements ImageFunction<Mat, Mat> {
    /**
     * threshold value
     */
    private double thresh;
    /**
     * maximum value to use with the THRESH_BINARY and THRESH_BINARY_INV thresholding types.
     */
    private double maxval = 255.0;
    /**
     * The function applies fixed-level thresholding to a single-channel array. The function is typically used to get a bi-level (binary) image out of a grayscale image ( compare() could be also used for this purpose) or for removing a noise, that is, filtering out pixels with too small or too large values. There are several types of thresholding supported by the function.
     */
    private OpenCVThresholdType type = OpenCVThresholdType.BINARY;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        // Creating empty matrix to store the temp results
        ImageWrapper<Mat> wrapper = ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(new Mat(), ChannelType.BINARY);
        // Converting the gray scale image to binary image
        Imgproc.threshold(matImageWrapper.getImage(), wrapper.getImage(), thresh, maxval, type.getType());

        return wrapper;
    }
}
