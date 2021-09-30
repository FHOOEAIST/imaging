/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.draw.polygon;

import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.draw.circle.OpenCVDrawCircle;
import science.aist.imaging.opencv.imageprocessing.draw.line.OpenCVDrawLine;
import science.aist.imaging.opencv.imageprocessing.domain.OpenCVLineType;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>Mat Consumer for drawing a polygon</p>
 * <p>Draws lines of a polygon onto an given image with {@link org.opencv.imgproc.Imgproc#line(Mat, Point, Point, Scalar, int, int, int)} and {@link org.opencv.imgproc.Imgproc#circle(Mat, Point, int, Scalar, int, int, int)}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVDrawPolygon implements BiConsumer<ImageWrapper<Mat>, JavaPolygon2D> {
    private OpenCVDrawCircle circleDrawer = new OpenCVDrawCircle();
    private OpenCVDrawLine lineDrawer = new OpenCVDrawLine();

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
    /**
     * Radius for the polygon points
     */
    private int circleRadius = 1;


    @Override
    public void accept(ImageWrapper<Mat> matImageWrapper, JavaPolygon2D javaPolygon2D) {
        circleDrawer.setColor(color);
        circleDrawer.setThickness(thickness);
        circleDrawer.setLineType(lineType);
        circleDrawer.setShift(shift);
        circleDrawer.setRadius(circleRadius);
        lineDrawer.setColor(color);
        lineDrawer.setThickness(thickness);
        lineDrawer.setLineType(lineType);
        lineDrawer.setShift(shift);

        List<JavaPoint2D> p = javaPolygon2D.getPoints();
        for (int i = 0; i < p.size() - 1; i++) {
            JavaPoint2D p1 = p.get(i);
            JavaPoint2D p2 = (p.get(i + 1));
            circleDrawer.accept(matImageWrapper, p1);
            lineDrawer.accept(matImageWrapper, new JavaLine2D(p1, p2));
            circleDrawer.accept(matImageWrapper, p2);
        }

        JavaPoint2D p1 = p.get(0);
        JavaPoint2D p2 = p.get(p.size() - 1);
        lineDrawer.accept(matImageWrapper, new JavaLine2D(p1, p2));
    }
}
