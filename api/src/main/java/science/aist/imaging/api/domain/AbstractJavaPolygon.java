/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * <p>Abstract implementation of a polygon</p>
 *
 * @param <T> The type of the point.
 * @author Christoph Praschl
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public abstract class AbstractJavaPolygon<T extends AbstractJavaPoint<T>, L extends AbstractJavaLine<T>> extends AbstractSurface<T, L> {

    /**
     * internal points representing the polygon
     */
    @EqualsAndHashCode.Include
    protected List<T> points = new ArrayList<>();
    @Getter(lazy = true)
    private final T centroid = calculateCentroid();
    private Double innerWidth = null;
    private Double innerHeight = null;
    private Double innerDepth = null;
    @Getter(lazy = true)
    private final double width = calculateInnerWidth();
    @Getter(lazy = true)
    private final double height = calculateInnerHeight();
    @Getter(lazy = true)
    private final double depth = calculateInnerDepth();

    @SafeVarargs
    public AbstractJavaPolygon(T... points) {
        this(Arrays.asList(points));
    }

    public AbstractJavaPolygon(Collection<T> points) {
        this.points.addAll(points);
    }

    private void initInnerWidthHeightDepth() {
        double maxXDistance = 0;
        double maxYDistance = 0;
        double maxZDistance = 0;

        for (T t : this.points) {
            for (T point : points) {
                double localXDistance = Math.abs(t.getX() - point.getX());
                if (localXDistance > maxXDistance) {
                    maxXDistance = localXDistance;
                }

                double localYDistance = Math.abs(t.getY() - point.getY());
                if (localYDistance > maxYDistance) {
                    maxYDistance = localYDistance;
                }

                double localZDistance = Math.abs(t.getZ() - point.getZ());
                if (localZDistance > maxZDistance) {
                    maxZDistance = localZDistance;
                }
            }
        }

        innerWidth = maxXDistance;
        innerHeight = maxYDistance;
        innerDepth = maxZDistance;
    }

    /**
     * @return true if polygon is empty else false
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    public List<T> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public int getSize() {
        return getPoints().size();
    }

    protected abstract L createLine(T p1, T p2);

    protected abstract T createPoint(double x, double y, double z);

    @Override
    protected List<L> calculateContour() {
        if (getPoints().isEmpty()) return Collections.emptyList();

        List<L> res = new ArrayList<>();
        int size = getPoints().size();
        List<T> localPoints = getPoints();
        for (int i = 1; i <= size; i++) {
            T prev = localPoints.get((i - 1) % size);
            T curr = localPoints.get(i % size);
            res.add(createLine(prev, curr));
        }
        return res;
    }

    /**
     * @return Returns the centroid of the polygon
     */
    private T calculateCentroid() {
        double centroidX = 0;
        double centroidY = 0;
        double centroidZ = 0;

        for (T knot : getPoints()) {
            centroidX += knot.getX();
            centroidY += knot.getY();
            centroidZ += knot.getZ();
        }

        return createPoint(centroidX / getPoints().size(), centroidY / getPoints().size(), centroidZ / getPoints().size());
    }

    private double calculateInnerWidth() {
        if (innerWidth == null) initInnerWidthHeightDepth();
        return innerWidth;
    }

    private double calculateInnerHeight() {
        if (innerHeight == null) initInnerWidthHeightDepth();
        return innerHeight;
    }

    protected double calculateInnerDepth() {
        if (innerDepth == null) initInnerWidthHeightDepth();
        return innerDepth;
    }
}
