/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw.circle;

import lombok.Setter;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.service.core.imageprocessing.draw.AbstractDrawer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Abstract drawer for drawing a circle</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractDrawCircle<I> extends AbstractDrawer<I, JavaPoint2D> {
    @Setter
    protected int radius = 0;

    protected List<JavaPoint2D> getCircleContour(JavaPoint2D center) {
        List<JavaPoint2D> points = new ArrayList<>();
        if (radius == 0) {
            points.add(center);
        } else {
            addCirclePoints(center, points, radius);
        }
        return points;
    }

    protected void addCirclePoints(JavaPoint2D center, Collection<JavaPoint2D> points, int r) {
        for (int degree = 0; degree < 360; degree++) {
            final double angle = Math.toRadians(degree);
            final double x = r * Math.cos(angle) + (center.getX() - 1);
            final double y = r * Math.sin(angle) + (center.getX() - 1);
            points.add(new JavaPoint2D(x, y));
        }
    }
}
