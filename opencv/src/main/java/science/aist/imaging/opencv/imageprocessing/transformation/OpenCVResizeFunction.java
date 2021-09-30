/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Function for resizing a image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVResizeFunction implements ImageFunction<Mat, Mat> {
    private int width = 1;
    private int height = 1;

    /**
     * Setter for setting width and the height relative to the new width and the original image dimensions
     *
     * @param resizeWidth    The new width of the image
     * @param originalWidth  The original width of the image
     * @param originalHeight The original height of the image
     */
    public void setWidthAndRelativeHeight(int resizeWidth, int originalWidth, int originalHeight) {
        double factor = (double) resizeWidth / (double) originalWidth;
        height = (int) (originalHeight * factor);
        width = resizeWidth;
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> img) {
        // Create a new matrix with the new size and the same type as the old one.
        Mat res = new Mat(height, width, img.getImage().type());
        // use opencv to resize the image.
        Imgproc.resize(img.getImage(), res, new Size(width, height));
        // return the new image
        return ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(res, img.getChannelType());
    }
}
