/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.edgedetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.conversion.OpenCVBGR2GrayscaleFunction;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Sobel Edge Detector</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Getter(AccessLevel.PRIVATE)
@Setter
public class OpenCVSobelEdgeDetection implements ImageFunction<Mat, Mat> {

    @Setter
    private OpenCVBGR2GrayscaleFunction grayscaleFunction;
    /**
     * Size of the extended Sobel kernel
     */
    private int ksize = 3;

    /**
     * performs an edge detection on given image
     *
     * @param img image for which the edge detection should be executed
     * @return the resulting edges
     */
    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> img) {
        @Cleanup("release") Mat gradX = new Mat();
        @Cleanup("release") Mat absGradX = new Mat();
        @Cleanup("release") Mat gradY = new Mat();
        @Cleanup("release") Mat absGradY = new Mat();

        // Convert it to gray
        @Cleanup ImageWrapper<Mat> greyscaleImage = grayscaleFunction.apply(img);

        // Generate gradX and gradY
        // Gradient X

        Imgproc.Sobel(greyscaleImage.getImage(), gradX, CvType.CV_16S, 1, 0, getKsize(), 1, 1, Core.BORDER_DEFAULT);
        Core.convertScaleAbs(gradX, absGradX);

        // Gradient Y

        Imgproc.Sobel(greyscaleImage.getImage(), gradY, CvType.CV_16S, 0, 1, getKsize(), 1, 1, Core.BORDER_DEFAULT);
        Core.convertScaleAbs(gradY, absGradY);

        // Total Gradient (approximate)
        Mat res = new Mat();
        Core.addWeighted(absGradX, 0.5, absGradY, 0.5, 0, res);

        return ImageFactoryFactory.getImageFactory(Mat.class).getImage(res);
    }

}
