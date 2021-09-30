/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.draw.polygon;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.core.imageprocessing.draw.AbstractDrawer;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Draw implementation for drawing any polygon's outline</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class DrawPolygon<I> extends AbstractDrawer<I, JavaPolygon2D> {
    @Override
    public void accept(ImageWrapper<I> iImageWrapper, JavaPolygon2D poly) {
        Set<JavaPoint2D> bresenham = new HashSet<>();
        for (JavaLine2D javaLine2D : poly.getContour()) {
            bresenham.addAll(javaLine2D.getBresenham());
        }

        addOffsetPoints(bresenham, thickness);
        drawPoints(iImageWrapper, bresenham);
    }
}
