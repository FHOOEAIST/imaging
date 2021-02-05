/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVBorderMode;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * <p>Function for apply a padding around the image. Creates a Border around the image with the given color.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVPaddingFunction implements ImageFunction<Mat, Mat> {
    private final OpenCVScalarRGBColorTransformer colorTransformer = new OpenCVScalarRGBColorTransformer();
    private RGBColor color = RGBColor.BLACK;
    private int paddingTop = 0;
    private int paddingBottom = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private OpenCVBorderMode borderMode = OpenCVBorderMode.BORDER_CONSTANT;

    /**
     * Parameter specifying how many pixels in each direction from the source image rectangle to extrapolate.
     *
     * @param top    padding top
     * @param bottom padding bottom
     * @param left   padding left
     * @param right  padding right
     */
    public void setPaddings(int top, int right, int bottom, int left) {
        paddingTop = top;
        paddingBottom = bottom;
        paddingLeft = left;
        paddingRight = right;
    }

    /**
     * Parameter specifying how many pixels in each direction from the source image rectangle to extrapolate.
     *
     * @param padding padding top
     */
    public void setPaddings(int padding) {
        paddingTop = padding;
        paddingBottom = padding;
        paddingLeft = padding;
        paddingRight = padding;
    }

    /**
     * @param borderMode Border type, one of the BORDER_* , except for BORDER_TRANSPARENT and BORDER_ISOLATED .
     */
    public void setBorderMode(OpenCVBorderMode borderMode) {
        if (borderMode == OpenCVBorderMode.BORDER_TRANSPARENT || borderMode == OpenCVBorderMode.BORDER_ISOLATED)
            throw new IllegalArgumentException("Only OpenCVBorderMode.BORDER_CONSTANT and OpenCVBorderMode.BORDER_REPLICATE are allowed ");
        this.borderMode = borderMode;
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> image) {
        Mat result = new Mat();
        Core.copyMakeBorder(image.getImage(), result, paddingTop, paddingBottom, paddingLeft, paddingRight, borderMode.getBorderType(), colorTransformer.transformTo(color));
        return OpenCVFactory.getInstance().getImage(result);
    }
}
