/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

/**
 * <p>Interface which wraps a rectangle.</p>
 *
 * @param <R> Target type representing the rectangle which should be wrapped.
 * @param <P> Target type representing of the rectangles point
 * @author Christoph Praschl
 * @since 1.0
 */
public interface RectangleWrapper<R, P> {
    /**
     * @return Wrapped Rectangle
     */
    R getRectangle();

    /**
     * @param rectangle The Rectangle to wrap
     */
    void setRectangle(R rectangle);

    /**
     * @return The top left corner point of the rectangle
     */
    Point2Wrapper<P> getTopLeftPoint();

    /**
     * @return The bottom right corner point of the rectangle
     */
    Point2Wrapper<P> getBottomRightPoint();

    /**
     * @return The center corner point of the rectangle
     */
    Point2Wrapper<P> getCenterPoint();

    /**
     * @return The width of the rectangle
     */
    double getWidth();

    /**
     * @return The height of the rectangle
     */
    double getHeight();

}
