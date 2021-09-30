/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.draw.circle;

import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.opencv.imageprocessing.domain.OpenCVLineType;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVPoint2WrapperJavaPointTransformer;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;

import java.util.function.BiConsumer;

/**
 * <p>Mat Consumer for drawing circles</p>
 * <p>Draws an circles onto an given image with {@link org.opencv.imgproc.Imgproc#circle(Mat, Point, int, Scalar, int, int, int)}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVDrawCircle implements BiConsumer<ImageWrapper<Mat>, JavaPoint2D> {
    private OpenCVPoint2WrapperJavaPointTransformer transformer = new OpenCVPoint2WrapperJavaPointTransformer();
    private OpenCVScalarRGBColorTransformer colorTransformer = new OpenCVScalarRGBColorTransformer();

    /**
     * Radius of the circle.
     */
    private int radius;
    /**
     * Rectangle color.
     */
    private RGBColor color;
    /**
     * Thickness of lines that make up the rectangle. Negative values, like CV_FILLED, mean that the function has to draw a filled rectangle.
     */
    private int thickness = 1;
    /**
     * Type of the line. Default: {@link OpenCVLineType#LINE_8}
     */
    private OpenCVLineType lineType = OpenCVLineType.LINE_8;
    /**
     * Number of fractional bits in the point coordinates. Default: 0
     */
    private int shift;

    @Override
    public void accept(ImageWrapper<Mat> matImageWrapper, JavaPoint2D javaPoint2D) {
        Point2Wrapper<Point> c = transformer.transformTo(javaPoint2D);
        Scalar col = colorTransformer.transformTo(color);
        Imgproc.circle(matImageWrapper.getImage(), c.getPoint(), radius, col, thickness, lineType.getLineType(), shift);
    }
}
