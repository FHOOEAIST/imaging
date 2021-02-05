/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformers;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaRectangleRotated2D;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RotatedRectangleWrapper;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVRotatedRectangleWrapper;
import lombok.NonNull;
import lombok.Setter;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Transformer between a {@link RotatedRectangleWrapper} of {@link RotatedRect} and {@link Point} into a {@link JavaRectangleRotated2D}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class OpenCVRotatedRectangleWrapperToJavaRectangleRotatedTransformer implements Transformer<RotatedRectangleWrapper<RotatedRect, Point>, JavaRectangleRotated2D> {

    /**
     * Transformer to transform a {@link Point2Wrapper} of {@link Point} into a {@link JavaPoint2D}
     */
    @Setter
    @NonNull
    private Transformer<Point2Wrapper<Point>, JavaPoint2D> point2WrapperJavaPointTransformer;

    @Override
    public RotatedRectangleWrapper<RotatedRect, Point> transformTo(JavaRectangleRotated2D javaRectangleRotated2D) {
        return OpenCVRotatedRectangleWrapper.create(
                point2WrapperJavaPointTransformer.transformTo(javaRectangleRotated2D.getCenterPoint()),
                javaRectangleRotated2D.getWidth(),
                javaRectangleRotated2D.getHeight(),
                javaRectangleRotated2D.getRotation());
    }

    @Override
    public JavaRectangleRotated2D transformFrom(RotatedRectangleWrapper<RotatedRect, Point> rotatedRectPointRotatedRectangleWrapper) {
        // Create the rotated java rectangle
        return new JavaRectangleRotated2D(
                point2WrapperJavaPointTransformer.transformFrom(rotatedRectPointRotatedRectangleWrapper.getCenterPoint()),
                rotatedRectPointRotatedRectangleWrapper.getWidth(),
                rotatedRectPointRotatedRectangleWrapper.getHeight(),
                rotatedRectPointRotatedRectangleWrapper.getRotation()
        );
    }
}
