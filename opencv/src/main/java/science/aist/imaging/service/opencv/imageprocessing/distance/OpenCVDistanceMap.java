/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.distance;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVDistanceLabel;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVDistanceMask;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVDistanceType;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Calculate the distance map using {@link org.opencv.imgproc.Imgproc#distanceTransform(Mat, Mat, int, int, int)}</p>
 * <p><a href="https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html">https://docs.opencv.org/2.4/modules/imgproc/doc/miscellaneous_transformations.html</a></p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class OpenCVDistanceMap implements ImageFunction<Mat, Mat> {
    /**
     * <p>Type of the Distance Map Calculation</p>
     * <p>OpenCV: Type of distance. It can be CV_DIST_L1, CV_DIST_L2 , or CV_DIST_C .</p>
     */
    private OpenCVDistanceType type = OpenCVDistanceType.C;

    /**
     * <p>Mask Size of the Distance Map Calculation</p>
     * <p>OpenCV: Size of the distance transform mask. It can be 3, 5, or CV_DIST_MASK_PRECISE (the latter option is only supported by the first function).
     * In case of the CV_DIST_L1 or CV_DIST_C distance type, the parameter is forced to 3 because a 3x3 mask gives the same result as
     * 5x5 or any larger aperture</p>
     */
    private OpenCVDistanceMask mask = OpenCVDistanceMask.MASK_5;

    /**
     * <p>OpenCVDistanceLabel of the Distance Map </p>
     * <p>OpenCV: Optional output 2D array of labels (the discrete Voronoi diagram).
     * It has the type CV_32SC1 and the same size as src. See the details below.</p>
     */
    private OpenCVDistanceLabel labelType = OpenCVDistanceLabel.CCOMP;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> input) {
        ImageWrapper<Mat> result = TypeBasedImageFactoryFactory.getImageFactory(Mat.class).getImage(new Mat());
        Imgproc.distanceTransform(input.getImage(), result.getImage(), type.getType(), mask.getMaskSize(), labelType.getLabel());
        return result;
    }
}

