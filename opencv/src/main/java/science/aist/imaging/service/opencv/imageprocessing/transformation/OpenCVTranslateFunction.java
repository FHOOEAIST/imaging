/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.Direction;
import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 * <p>Function for translating an image
 * Source: http://stackoverflow.com/questions/19068085/shift-image-content-with-opencv/26766505#26766505</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVTranslateFunction implements ImageFunction<Mat, Mat> {
    /**
     * offset in x direction
     */
    private double xOffset;

    /**
     * offset in y direction
     */
    private double yOffset;

    private static ImageWrapper<Mat> translate(ImageWrapper<Mat> img, int offset, Direction direction) {
        if (offset == 0) return img;
        // to avoid exceptions just use the absolute value!
        offset = Math.abs(offset);

        Mat inputImg = img.getImage();
        Mat outImg = Mat.zeros(inputImg.size(), inputImg.type());
        // depending on the given direction move the image
        switch (direction) {
            case SHIFT_RIGHT:
                inputImg.submat(new Rect(0, 0, inputImg.cols() - offset, inputImg.rows())).copyTo(outImg.submat(new Rect(offset, 0, outImg.cols() - offset, outImg.rows())));
                break;
            case SHIFT_DOWN:
                inputImg.submat(new Rect(0, 0, inputImg.cols(), inputImg.rows() - offset)).copyTo(outImg.submat(new Rect(0, offset, outImg.cols(), outImg.rows() - offset)));
                break;
            case SHIFT_LEFT:
                inputImg.submat(new Rect(offset, 0, inputImg.cols() - offset, inputImg.rows())).copyTo(outImg.submat(new Rect(0, 0, outImg.cols() - offset, outImg.rows())));
                break;
            default: // ShiftUp
                inputImg.submat(new Rect(0, offset, inputImg.cols(), inputImg.rows() - offset)).copyTo(outImg.submat(new Rect(0, 0, outImg.cols(), outImg.rows() - offset)));
                break;
        }

        return OpenCVFactory.getInstance().getImage(outImg);
    }

    /**
     * @param offset set translation offset
     */
    public void setOffset(TranslationOffset offset) {
        this.xOffset = offset.getXOffset();
        this.yOffset = offset.getYOffset();
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> img) {
        // prepare result
        ImageWrapper<Mat> res = OpenCVFactory.getInstance().getImage(img.getImage());
        // get offset on x - axis

        int innerXOffset = (int) (xOffset > 0 ? xOffset + .5 : xOffset - .5);
        // if x offset is positive move image to the right else left
        if (innerXOffset > 0) {
            res = translate(res, innerXOffset, Direction.SHIFT_RIGHT);
        } else if (innerXOffset < 0) {
            res = translate(res, Math.abs(innerXOffset), Direction.SHIFT_LEFT);
        }
        // get offset on x - axis
        int innerYOffset = (int) (yOffset > 0 ? yOffset + .5 : yOffset - .5);
        // if x offset is positive move image to down else up
        if (innerYOffset > 0) {
            res = translate(res, innerYOffset, Direction.SHIFT_DOWN);
        } else if (innerYOffset < 0) {
            res = translate(res, Math.abs(innerYOffset), Direction.SHIFT_UP);
        }

        return res;

    }
}
