/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Function for rotating an image
 * Source: http://stackoverflow.com/questions/26692648/rotate-image-using-opencv</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVRotateFunction implements ImageFunction<Mat, Mat> {
    /**
     * Rotation in degrees by which image should be rotated (Default: 0.0)
     */
    private double rotation = 0.0;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> img) {
        if (Math.abs(rotation) < 0.00000001) return img;
        // fine working getRotation but with cropping image...

        // get image from wrapper
        Mat src = img.getImage();
        // create a new point which represents the image´s center (around this point the image will be rotated)
        Point center = new Point(src.cols() / 2, src.rows() / 2);
        // create getRotation Matrix
        @Cleanup("release") Mat rotateMatrix = Imgproc.getRotationMatrix2D(center, rotation, 1.0);

        // create result mat
        Mat res = new Mat(src.size(), src.type());
        // rotate image
        Imgproc.warpAffine(src, res, rotateMatrix, src.size());

        return TypeBasedImageFactoryFactory.getImageFactory(Mat.class).getImage(res);
    }
}
