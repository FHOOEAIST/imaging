/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.pointprocessing;



import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class for creating a star-shaped polygon</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class StarPolygonFunction extends PolygonFunction {
    /**
     * Factor ]0, 1[ for calculating the inner radius of the stars
     */
    private double innerRadius = 0.5;

    /**
     * Constructor for creating a polygon function for star shaped polygons
     *
     * @param radius      Radius of the polygon
     * @param numOfPoints Number of points of the polygon
     * @param offset      Rotational offset of the points along the radius
     * @param innerRadius Factor ]0, 1[ for calculating the inner radius of the stars
     */
    public StarPolygonFunction(int radius, int numOfPoints, int offset, double innerRadius) {
        super(radius, numOfPoints, offset);
        if (innerRadius <= 0 || innerRadius >= 1) {
            throw new IllegalArgumentException("innerRadius must not be <= 0 or >= 1");
        }
        this.innerRadius = innerRadius;
    }

    @Override
    public JavaPolygon2D apply(JavaPoint2D center) {
        int stepSize = 360 / (numOfPoints * 2);
        int innerRadiusInt = (int) (radius * innerRadius);

        List<JavaPoint2D> points = new ArrayList<>();
        for (int i = offset, idx = 0; i < 360 + offset; i += stepSize, idx++) {
            int angle = i % 360;
            double rad = Math.toRadians(angle);

            int r;
            if (idx % 2 == 0) {
                r = radius;
            } else {
                r = innerRadiusInt;
            }

            double x = center.getIntX() + (r * Math.cos(rad));
            double y = center.getIntY() + (r * Math.sin(rad));
            points.add(new JavaPoint2D(x, y));
        }

        return new JavaPolygon2D(points);
    }
}

