/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * <p>Abstract class representing any surface</p>
 *
 * @param <P> The type of the point.
 * @param <L> the type of the line.
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractSurface<P extends AbstractJavaPoint<P>, L extends AbstractJavaLine<P>> {

    @Getter(lazy = true)
    private final P normalvector = calculateNormalvector();
    @Getter(lazy = true)
    private final List<L> contour = calculateContour();

    /**
     * @return the normalvector of this surface
     */
    protected abstract P calculateNormalvector();

    /**
     * @return returns the path segments of this surface
     */
    protected abstract List<L> calculateContour();

    /**
     * Determines the point of intersection between this surface and a line
     * (source: https://stackoverflow.com/questions/5666222/3d-line-plane-intersection)
     *
     * @param planePoint point on this surface
     * @param line       to intersect with
     * @return Optional containing intersection point or {@link Optional#empty()} if line is parallel to triangle
     */
    protected Optional<P> getIntersection(P planePoint, AbstractJavaLine<P> line) {
        P planeNormal = this.getNormalvector();
        P lineDirection = line.getLineDirection();
        P linePoint = line.getStartPoint();

        if (planeNormal.dot(lineDirection.getUnify()) == 0) {
            return Optional.empty();
        }

        double t = (planeNormal.dot(planePoint) - planeNormal.dot(linePoint)) / planeNormal.dot(lineDirection.getUnify());
        return Optional.of(linePoint.add(lineDirection.getUnify().mult(t)));
    }
}
