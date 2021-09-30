/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformers;

import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVRectangleWrapper;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>This class implements a Transformer between OpenCV RectangleWrapper and JavaRectangle</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVRectangleWrapperJavaRectangleTransformer implements Transformer<RectangleWrapper<Rect, Point>, JavaRectangle2D> {
    /**
     * @param javaRectangle to be transformed
     * @return From corresponding to To
     */
    @Override
    public RectangleWrapper<Rect, Point> transformTo(JavaRectangle2D javaRectangle) {
        return new OpenCVRectangleWrapper(javaRectangle.getTopLeft().getX(), javaRectangle.getTopLeft().getY(), javaRectangle.getBottomRight().getX(), javaRectangle.getBottomRight().getY());
    }

    /**
     * @param rectPointRectangleWrapper to be transformed
     * @return To corresponding to From
     */
    @Override
    public JavaRectangle2D transformFrom(RectangleWrapper<Rect, Point> rectPointRectangleWrapper) {
        return new JavaRectangle2D(rectPointRectangleWrapper.getTopLeftPoint().getX(), rectPointRectangleWrapper.getTopLeftPoint().getY(), rectPointRectangleWrapper.getBottomRightPoint().getX(), rectPointRectangleWrapper.getBottomRightPoint().getY());
    }
}
