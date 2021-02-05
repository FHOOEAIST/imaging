/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Class for creating a symmetrical polygon</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
@AllArgsConstructor
public class PolygonFunction implements Function<JavaPoint2D, JavaPolygon2D> {
    /***
     * Radius of the polygon
     */
    protected int radius;

    /**
     * Number of points of the polygon
     */
    protected int numOfPoints;

    /**
     * Rotational offset of the points along the radius
     */
    protected int offset;

    /**
     * Create a polygon for a given center point, a radius and the number of polygon points.
     *
     * @param center Center of the polygon
     * @return Polygon
     */
    public JavaPolygon2D apply(JavaPoint2D center) {
        int stepSize = 360 / numOfPoints;

        List<JavaPoint2D> points = new ArrayList<>();
        for (int i = offset; i < 360 + offset; i += stepSize) {
            int angle = i % 360;
            double rad = Math.toRadians(angle);
            double x = center.getIntX() + (radius * Math.cos(rad));
            double y = center.getIntY() + (radius * Math.sin(rad));
            points.add(new JavaPoint2D(x, y));
        }

        return new JavaPolygon2D(points);
    }

}
