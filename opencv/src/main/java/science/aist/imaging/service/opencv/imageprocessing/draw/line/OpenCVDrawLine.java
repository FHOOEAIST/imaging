/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.line;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVLineType;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVPoint2WrapperJavaPointTransformer;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.function.BiConsumer;

/**
 * <p>Mat Consumer for drawing a line</p>
 * <p>Draws a line onto an given image with {@link org.opencv.imgproc.Imgproc#line(Mat, Point, Point, Scalar, int, int, int)}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVDrawLine implements BiConsumer<ImageWrapper<Mat>, JavaLine2D> {
    private OpenCVPoint2WrapperJavaPointTransformer transformer = new OpenCVPoint2WrapperJavaPointTransformer();
    private OpenCVScalarRGBColorTransformer colorTransformer = new OpenCVScalarRGBColorTransformer();

    /**
     * Rectangle color.
     */
    private RGBColor color;
    /**
     * Thickness of lines that make up the rectangle. Negative values, like CV_FILLED, mean that the function has to draw a filled rectangle. Default: 1
     */
    private int thickness = 1;
    /**
     * Type of the line. Default: {@link OpenCVLineType#LINE_8}
     */
    private OpenCVLineType lineType = OpenCVLineType.LINE_8;
    /**
     * Number of fractional bits in the coordinates of the center and in the radius value.
     */
    private int shift;

    public void accept(ImageWrapper<Mat> matImageWrapper, LineWrapper<Point> javaLine2D) {
        Scalar c = colorTransformer.transformTo(color);
        Imgproc.line(matImageWrapper.getImage(), javaLine2D.getStartPoint().getPoint(), javaLine2D.getEndPoint().getPoint(), c, thickness, lineType.getLineType(), shift);
    }

    @Override
    public void accept(ImageWrapper<Mat> matImageWrapper, JavaLine2D javaLine2D) {
        Point p1 = transformer.transformTo(javaLine2D.getStartPoint()).getPoint();
        Point p2 = transformer.transformTo(javaLine2D.getEndPoint()).getPoint();
        Scalar c = colorTransformer.transformTo(color);
        Imgproc.line(matImageWrapper.getImage(), p1, p2, c, thickness, lineType.getLineType(), shift);
    }
}
