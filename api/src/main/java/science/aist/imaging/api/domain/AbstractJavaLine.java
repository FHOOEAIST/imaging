/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Abstract line representation</p>
 *
 * @param <T> The type of the point.
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class AbstractJavaLine<T extends AbstractJavaPoint<T>> {
    @Getter(lazy = true)
    private final T centerPoint = calculateCenterPoint();
    /**
     * start point of the line
     */
    protected T startPoint;
    /**
     * end point of the line
     */
    protected T endPoint;

    /**
     * @return The direction vector of this line
     */
    public T getLineDirection() {
        return endPoint.sub(startPoint);
    }

    /**
     * Returns the length of the line.
     * JavaPoint.pointDistance(start, end)
     *
     * @return line of the length
     */
    public double length() {
        return AbstractJavaPoint.pointDistance(getStartPoint(), getEndPoint());
    }

    /**
     * Calculate the center point of a given line
     *
     * @return center point of the line
     */
    protected abstract T calculateCenterPoint();

    /**
     * Method for getting a point along this line with the given distance from the startPoint
     * (Based on https://math.stackexchange.com/questions/175896/finding-a-point-along-a-line-a-certain-distance-away-from-another-point)
     * Note: abstract implementation because of generic T not possible also if it is the same calculation.
     *
     * @param distance from the startPoint
     * @return the point with the given distance
     */
    public abstract T getPointAlongLine(double distance);

    /**
     * Method for getting n points between start and endpoint
     *
     * @param n number of interpolated points
     * @return List of points
     */
    public List<T> getInterpolatedPoints(int n) {
        return getInterpolatedPoints(n, false);
    }

    /**
     * Method for getting n points between start and endpoint
     *
     * @param n                  number of interpolated points
     * @param includeStartAndEnd flag which decides if start and endpoint should be added to the result
     * @return List of points
     */
    public List<T> getInterpolatedPoints(int n, boolean includeStartAndEnd) {
        List<T> result = new ArrayList<>();
        if (includeStartAndEnd) {
            result.add(getStartPoint());
        }

        double distanceBetweenPoints = length() / (n + 1);
        for (int i = 0; i < n; i++) {
            result.add(getPointAlongLine(distanceBetweenPoints * (i + 1)));
        }

        if (includeStartAndEnd) {
            result.add(getEndPoint());
        }
        return result;
    }
}
