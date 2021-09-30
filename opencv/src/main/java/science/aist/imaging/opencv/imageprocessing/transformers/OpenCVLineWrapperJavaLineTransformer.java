/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformers;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVLineWrapper;
import org.opencv.core.Point;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>This class implements a Transformer between LineWrapper of Point (OpenCV) and JavaLine</p>
 *
 * @author Andreas Pointner
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVLineWrapperJavaLineTransformer implements Transformer<LineWrapper<Point>, JavaLine2D> {
    /**
     * @param javaLine to be transformed
     * @return item corresponding to DTO
     */
    @Override
    public LineWrapper<Point> transformTo(JavaLine2D javaLine) {
        return new OpenCVLineWrapper(javaLine.getStartPoint().getX(), javaLine.getStartPoint().getY(), javaLine.getEndPoint().getX(), javaLine.getEndPoint().getY());
    }

    /**
     * @param pointLineWrapper to be transformed
     * @return DTO corresponding to item
     */
    @Override
    public JavaLine2D transformFrom(LineWrapper<Point> pointLineWrapper) {
        return new JavaLine2D(pointLineWrapper.getStartPoint().getX(), pointLineWrapper.getStartPoint().getY(), pointLineWrapper.getEndPoint().getX(), pointLineWrapper.getEndPoint().getY());
    }
}
