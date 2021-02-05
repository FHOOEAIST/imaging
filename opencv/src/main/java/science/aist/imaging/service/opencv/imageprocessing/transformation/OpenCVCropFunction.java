/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 * <p>Function for cropping an image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVCropFunction implements ImageFunction<Mat, Mat> {
    private JavaPoint2D from;
    private JavaPoint2D to;

    /**
     * @param x coordinate of start point for cropping
     * @param y coordinate of start point for cropping
     */
    public void setFrom(double x, double y) {
        this.from = new JavaPoint2D(x, y);
    }

    /**
     * @param x coordinate of end point for cropping
     * @param y coordinate of end point for cropping
     */
    public void setTo(double x, double y) {
        this.to = new JavaPoint2D(x, y);
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> img) {
        if (!(0 <= from.getX() && from.getX() <= to.getX() && to.getX() <= img.getImage().width()))
            throw new IllegalArgumentException("0 < from.x < to.x < width");
        if (!(0 <= from.getY() && from.getY() <= to.getY() && to.getY() <= img.getImage().height()))
            throw new IllegalArgumentException("0 < from.y < to.y < height");

        int width = (int) (to.getX() - from.getX());
        int height = (int) (to.getY() - from.getY());

        if (width < 0) throw new IllegalArgumentException("width cannot be negative");
        if (height < 0) throw new IllegalArgumentException("height cannot be negative");
        Mat res = new Mat(img.getImage(), new Rect((int) from.getX(), (int) from.getY(), width, height));
        return OpenCVFactory.getInstance().getImage(res, img.getChannelType());
    }
}
