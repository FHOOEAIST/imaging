/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.draw.circle;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Draw implementation for drawing a circle outline</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class DrawCircle<I> extends AbstractDrawCircle<I> {
    @Override
    public void accept(ImageWrapper<I> iImageWrapper, JavaPoint2D javaPoint2D) {
        Set<JavaPoint2D> points = new HashSet<>(getCircleContour(javaPoint2D));
        addOffsetPoints(points, thickness);
        drawPoints(iImageWrapper, points);
    }
}
