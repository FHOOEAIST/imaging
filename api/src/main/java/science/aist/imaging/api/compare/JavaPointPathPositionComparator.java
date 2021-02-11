/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import science.aist.imaging.api.domain.AbstractJavaPoint;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * <p>Compares two {@link JavaPoint2D}s based on their position on a path represented by a {@link JavaPolygon2D}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
public class JavaPointPathPositionComparator implements Comparator<JavaPoint2D>, Serializable {
    @NonNull
    private final JavaPolygon2D javaPolygon2D;

    /**
     * @param o1 point 1
     * @param o2 point 2
     * @return a value &lt; 0 if o1 is on path before o2
     * == 0 if o1 is equal to o2
     * &gt; 0 if o1 is on path after o2
     */
    @Override
    public int compare(JavaPoint2D o1, JavaPoint2D o2) {
        List<JavaLine2D> pathSegments = javaPolygon2D.getContour();

        double pathDistance = 0;
        double distanceO1 = 0;
        double distanceO2 = 0;
        boolean distanceO1Found = false;
        boolean distanceO2Found = false;


        // iterate to pathSegments.size() - 1 because last path segment = first path segment
        for (int i = 0; i < pathSegments.size() - 1; i++) {
            JavaLine2D pathSegment = pathSegments.get(i);

            if (!distanceO1Found && pathSegment.isPointOnLine(o1)) {
                distanceO1 = pathDistance + Math.abs(AbstractJavaPoint.pointDistance(pathSegment.getStartPoint(), o1));
                distanceO1Found = true;
            }

            if (!distanceO2Found && pathSegment.isPointOnLine(o2)) {
                distanceO2 = pathDistance + Math.abs(AbstractJavaPoint.pointDistance(pathSegment.getStartPoint(), o2));
                distanceO2Found = true;
            }

            pathDistance += pathSegment.length();
        }

        if (!distanceO1Found) {
            throw new IllegalArgumentException("o1 is not on the path");
        }

        if (!distanceO2Found) {
            throw new IllegalArgumentException("o2 is not on the path");
        }

        return Double.compare(distanceO1, distanceO2);
    }
}

