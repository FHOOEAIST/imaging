/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import java.util.List;

/**
 * <p>Extension of the {@link RectangleWrapper} to also support Rotated Rectangles</p>
 *
 * @param <R> Target type representing the rectangle which should be wrapped.
 * @param <P> Target type representing of the rectangles point
 * @author Andreas Pointner
 * @since 1.0
 */
public interface RotatedRectangleWrapper<R, P> extends RectangleWrapper<R, P> {
    /**
     * @return the top right point of the rectangle
     */
    Point2Wrapper<P> getTopRightPoint();

    /**
     * @return the bottom left point of the rectangle
     */
    Point2Wrapper<P> getBottomLeftPoint();

    /**
     * @return Receive the corner points in order bottomLeft, topLeft, topRight, bottomRight
     */
    List<Point2Wrapper<P>> getPoints();

    /**
     * @return the angle / getRotation of the rectangle in radians
     */
    double getRotation();
}
