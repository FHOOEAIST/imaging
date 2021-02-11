/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.polygon;

import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVLineType;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVMatOfPointToListOfPointWrapperTransformer;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVPoint2WrapperJavaPointTransformer;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;
import science.aist.jack.general.transformer.Transformer;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * <p>Draws a JavaPoly using {@link Imgproc#fillPoly(Mat, List, Scalar, int, int, Point)}</p>
 *
 * @author Andreas Pointner
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVDrawFilledPolygon implements BiConsumer<ImageWrapper<Mat>, JavaPolygon2D> {
    /**
     * The color in which the JavaPolygon gets drawn
     */
    private RGBColor color = RGBColor.WHITE;

    /**
     * Type of the line. Default: {@link OpenCVLineType#LINE_8}
     */
    private OpenCVLineType lineType = OpenCVLineType.LINE_8;

    /**
     * The shift
     * Number of fractional bits in the vertex coordinates.
     */
    private int shift = 0;

    /**
     * Optional offset of all points of the contours.
     */
    private JavaPoint2D offSet = new JavaPoint2D(0, 0);

    /**
     * Transformer to convert form a RGBColor to a Scalar
     */
    private Transformer<Scalar, RGBColor> scalarRGBColorTransformer = new OpenCVScalarRGBColorTransformer();

    /**
     * Transformer to convert from a Point2Wrapper to a JavaPoint
     */
    private Transformer<Point2Wrapper<Point>, JavaPoint2D> point2WrapperJavaPointTransformer = new OpenCVPoint2WrapperJavaPointTransformer();

    /**
     * Transformer to convert form a List of Point2Wrapper of Points to a MatOfPoint
     */
    private Transformer<List<Point2Wrapper<Point>>, MatOfPoint> listMatOfPointTransformer = new OpenCVMatOfPointToListOfPointWrapperTransformer();

    @Override
    public void accept(ImageWrapper<Mat> matImageWrapper, JavaPolygon2D javaPolygon2D) {
        @Cleanup("release") MatOfPoint input = listMatOfPointTransformer.transformFrom(javaPolygon2D
                .getPoints()
                .stream()
                .map(point2WrapperJavaPointTransformer::transformTo)
                .collect(Collectors.toList()));
        Imgproc.fillPoly(matImageWrapper.getImage(),
                Collections.singletonList(input),
                scalarRGBColorTransformer.transformTo(color),
                lineType.getLineType(),
                shift,
                point2WrapperJavaPointTransformer.transformTo(offSet).getPoint());
    }
}
