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
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import org.opencv.core.Point;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>This class implements a Transformer between Point2Wrapper of Point (OpenCV) and JavaPoint</p>
 * @author Andreas Pointner
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVPoint2WrapperJavaPointTransformer implements Transformer<Point2Wrapper<Point>, JavaPoint2D> {
    /**
     * @param javaPoint2D to be transformed
     * @return item corresponding to DTO
     */
    @Override
    public Point2Wrapper<Point> transformTo(JavaPoint2D javaPoint2D) {
        return new OpenCVPoint2Wrapper(javaPoint2D.getX(), javaPoint2D.getY());
    }

    /**
     * @param pointPoint2Wrapper to be transformed
     * @return DTO corresponding to item
     */
    @Override
    public JavaPoint2D transformFrom(Point2Wrapper<Point> pointPoint2Wrapper) {
        return new JavaPoint2D(pointPoint2Wrapper.getX(), pointPoint2Wrapper.getY());
    }
}
