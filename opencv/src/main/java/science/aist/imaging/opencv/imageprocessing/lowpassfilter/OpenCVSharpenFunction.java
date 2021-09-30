/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.lowpassfilter;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * <p>sharpens a image by using gaussian blur and subtract it</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVSharpenFunction implements ImageFunction<Mat, Mat> {
    private final OpenCVGaussFilterFunction gauss = new OpenCVGaussFilterFunction();
    /**
     * Gaussian kernel standard deviation in X direction.
     */
    private double sigmaX = 5;
    /**
     * Gaussian kernel standard deviation in Y direction.
     */
    private double sigmaY = 5;
    /**
     * weight of the first array elements. (Default: 1.75)
     */
    private double alpha = 1.75;
    /**
     * weight of the second array elements. (Default: -0.75)
     */
    private double beta = -0.75;
    /**
     * scalar added to each sum (Default: 0)
     */
    private double gamma = 0;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> image) {
        gauss.setKernelHeight(0);
        gauss.setKernelWidth(0);
        gauss.setSigmaX(sigmaX);
        gauss.setSigmaY(sigmaY);

        Mat res = new Mat();
        Core.addWeighted(image.getImage(), alpha, gauss.apply(image).getImage(), beta, gamma, res);
        return ImageFactoryFactory.getImageFactory(Mat.class).getImage(res);
    }
}
