/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw.polygon;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.core.imageprocessing.draw.AbstractDrawer;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Draw implementation for drawing any polygon filled</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class DrawPolygonFilled<I> extends AbstractDrawer<I, JavaPolygon2D> {
    @Override
    public void accept(ImageWrapper<I> iImageWrapper, JavaPolygon2D poly) {
        Set<JavaPoint2D> points = new HashSet<>(poly.getAreaPoints());
        addOffsetPoints(points, thickness);
        drawPoints(iImageWrapper, points);
    }
}
