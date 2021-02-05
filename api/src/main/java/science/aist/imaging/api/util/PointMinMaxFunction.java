/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.util;

import science.aist.imaging.api.domain.AbstractJavaPoint;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.jack.data.Pair;

import java.util.Collection;
import java.util.function.Function;

/**
 * <p>Function which calculates the min and the max coordinates</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class PointMinMaxFunction<T extends AbstractJavaPoint<T>> implements Function<Collection<T>, Pair<JavaPoint3D, JavaPoint3D>> {
    /**
     * @param points input
     * @return Pair of MIN and MAX where MIN contains the minimum coordinates and MAX contains the maximum Coordinates
     */
    @Override
    public Pair<JavaPoint3D, JavaPoint3D> apply(Collection<T> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("Points must not be null or empty");
        }

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double maxZ = Double.MIN_VALUE;

        for (T point : points) {
            double x = point.getX();
            double y = point.getY();
            double z = point.getZ();

            if (x < minX) {
                minX = x;
            }

            if (y < minY) {
                minY = y;
            }

            if (z < minZ) {
                minZ = z;
            }

            if (x > maxX) {
                maxX = x;
            }

            if (y > maxY) {
                maxY = y;
            }

            if (z > maxZ) {
                maxZ = z;
            }
        }

        return Pair.of(new JavaPoint3D(minX, minY, minZ), new JavaPoint3D(maxX, maxY, maxZ));
    }
}
