/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.lowpassfilter;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * <p>apply Gaussian-filter to image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVGaussFilterFunction implements ImageFunction<Mat, Mat> {
    /**
     * Width of the applied kernel; must be positive and odd. Or, it can be zero and then it is computed from sigma* .
     */
    private int kernelWidth;

    /**
     * Height of the applied kernel; must be positive and odd. Or, it can be zero and then it is computed from sigma* .
     */
    private int kernelHeight;

    /**
     * Gaussian kernel standard deviation in X direction.
     */
    private double sigmaX;

    /**
     * Gaussian kernel standard deviation in Y direction.
     */
    private double sigmaY;

    /**
     * @param kernelWidth Width of the applied kernel; must be positive and odd. Or, it can be zero and then it is computed from sigma* .
     */
    public void setKernelWidth(int kernelWidth) {
        if ((kernelWidth != 0 && kernelWidth % 2 == 0) || kernelWidth < 0)
            throw new IllegalArgumentException("kernelWidth must be odd and positive or zero");
        this.kernelWidth = kernelWidth;
    }

    /**
     * @param kernelHeight Height of the applied kernel; must be positive and odd. Or, it can be zero and then it is computed from sigma* .
     */
    public void setKernelHeight(int kernelHeight) {
        if ((kernelHeight != 0 && kernelHeight % 2 == 0) || kernelHeight < 0)
            throw new IllegalArgumentException("kernelHeight must be odd and positive or zero");
        this.kernelHeight = kernelHeight;
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> image) {
        Mat m = image.getImage();
        Mat res = new Mat();
        Imgproc.GaussianBlur(m, res, new Size(kernelWidth, kernelHeight), sigmaX, sigmaY);
        return OpenCVFactory.getInstance().getImage(res);
    }
}
