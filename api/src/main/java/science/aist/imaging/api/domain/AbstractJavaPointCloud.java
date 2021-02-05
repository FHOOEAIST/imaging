/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import science.aist.jack.general.util.CastUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * <p>Wrapping class for an unordered amount of points</p>
 *
 * @param <T> The type of the point
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public abstract class AbstractJavaPointCloud<T extends AbstractJavaPoint<T>> {
    @NonNull
    private final List<T> points;

    @Getter(lazy = true)
    private final T centerPoint = calculateCenterPoint();

    protected abstract T calculateCenterPoint();

    /**
     * Help method for applying a unary operator to all points of the point cloud
     *
     * @param function unary operator which is applied to every point in the point cloud
     * @param <L> the type of the line
     * @return a new instance holding the transformed point cloud
     */
    protected <L extends AbstractJavaPointCloud<T>> L transformParallel(UnaryOperator<T> function) {
        return createPointCloud(points.stream().parallel().map(function).collect(Collectors.toList()));
    }

    protected abstract <L extends AbstractJavaPointCloud<T>> L createPointCloud(List<T> points);

    /**
     * Subtracts the given JavaPoint from the point cloud.
     *
     * @param vector The JavaPoint to be subtracted from the point cloud
     * @return A new instance holding the result of the subtraction
     */
    public abstract AbstractJavaPointCloud<T> sub(T vector);

    /**
     * Adds the given JavaPoint to the point cloud.
     *
     * @param vector The JavaPoint to be added to the point cloud
     * @return A new instance holding the result of the addition
     */
    public abstract AbstractJavaPointCloud<T> add(T vector);

    /**
     * Multiplies the point cloud with the given scalar.
     *
     * @param vector The multiplied vector
     * @return A new instance holding the result of the multiplication
     */
    public abstract AbstractJavaPointCloud<T> mult(T vector);

    /**
     * Divides the point cloud by the given scalar.
     *
     * @param scalar The scalar used for the division of the point cloud
     * @return A new instance holding the result of the division
     */
    public abstract AbstractJavaPointCloud<T> div(double scalar);

    /**
     * Scales the given JavaPointCloud around the center point
     *
     * @param scaleFactors vector containing the scale values
     * @return new scaled instance
     */
    public abstract AbstractJavaPointCloud<T> scale(T scaleFactors);

    /**
     * Scales the given JavaPointCloud around the given scale center point
     *
     * @param scaleFactors vector containing the scale values
     * @param scaleCenter  the scaleCenter
     * @return new scaled instance
     */
    public abstract AbstractJavaPointCloud<T> scale(T scaleFactors, T scaleCenter);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractJavaPointCloud<T> that = CastUtils.cast(o);

        for (T point : that.getPoints()) {
            if (!getPoints().contains(point)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }

    @Override
    public String toString() {
        return "AbstractJavaPointCloud{" +
                "centerPoint=" + getCenterPoint() +
                ",points=" + getPoints() +
                '}';
    }
}
