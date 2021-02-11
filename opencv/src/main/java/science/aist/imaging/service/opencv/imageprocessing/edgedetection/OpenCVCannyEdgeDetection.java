/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.edgedetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Canny Edge Detector</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Getter(AccessLevel.PRIVATE)
@Setter
public class OpenCVCannyEdgeDetection implements ImageFunction<Mat, Mat> {
    /**
     * first threshold for the hysteresis procedure.
     */
    private double thresholdLow = 50;
    /**
     * second threshold for the hysteresis procedure.
     */
    private double thresholdHigh = 200;
    /**
     * aperture size for the Sobel() operator.
     */
    private int apertureSize = 3;
    /**
     * a flag, indicating whether a more accurate  L2norm = sqrt{(dI/dx)^2 + (dI/dy)^2} should be used to calculate the image gradient magnitude ( L2gradient=true ), or whether the default  L1norm =|dI/dx|+|dI/dy| is enough ( L2gradient=false ).
     */
    private boolean l2Gradient = false;

    /**
     * performs an edge detection on given image
     *
     * @param img image for which the edge detection should be executed
     * @return the resulting edges
     */
    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> img) {
        Mat res = new Mat();
        Imgproc.Canny(img.getImage(), res, getThresholdLow(), getThresholdHigh(), getApertureSize(), isL2Gradient());
        return TypeBasedImageFactoryFactory.getImageFactory(Mat.class).getImage(res);
    }
}
