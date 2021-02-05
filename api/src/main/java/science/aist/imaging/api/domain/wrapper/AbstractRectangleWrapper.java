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
 * <p>Abstract Implementation of the RectangleWrapper Interface</p>
 *
 * @param <R> Target type representing the rectangle which should be wrapped.
 * @param <P> Target type representing of the rectangles point
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractRectangleWrapper<R, P> implements RectangleWrapper<R, P> {

    /**
     * Wrapped rectangle
     */
    protected R rectangle;

    public AbstractRectangleWrapper(R rectangle) {
        this.rectangle = rectangle;
    }

    public AbstractRectangleWrapper(P topLeftPoint, P bottomRightPoint) {
        this.rectangle = buildRectangle(topLeftPoint, bottomRightPoint);
    }

    public AbstractRectangleWrapper(double topLeftPointX, double topLeftPointY, double bottomRightPointX, double bottomRightPointY) {
        this.rectangle = buildRectangle(topLeftPointX, topLeftPointY, bottomRightPointX, bottomRightPointY);
    }

    /**
     * Method to create a new rectangle from to given points
     *
     * @param topLeftPoint     Top left point of the rectangle
     * @param bottomRightPoint Bottom right point of the rectangle
     * @return The created rectangle
     */
    protected R buildRectangle(P topLeftPoint, P bottomRightPoint) {
        throw new UnsupportedOperationException("Method must be overwritten. Abstract implementation not possible");
    }

    /**
     * Method to create a new rectangle from to given points (defined by the x and y coordinates)
     *
     * @param topLeftPointX     x-Coordinate of the Top left point of the rectangle
     * @param topLeftPointY     y-Coordinate of the Top left point of the rectangle
     * @param bottomRightPointX x-Coordinate of the Bottom Right point of the rectangle
     * @param bottomRightPointY y-Coordinate of the Bottom Right point of the rectangle
     * @return The created rectangle
     */
    protected R buildRectangle(double topLeftPointX, double topLeftPointY, double bottomRightPointX, double bottomRightPointY) {
        throw new UnsupportedOperationException("Method must be overwritten. Abstract implementation not possible");
    }

    /**
     * @return Wrapped Rectangle
     */
    @Override
    public R getRectangle() {
        return rectangle;
    }

    /**
     * @param rectangle The Rectangle to wrap
     */
    @Override
    public void setRectangle(R rectangle) {
        this.rectangle = rectangle;
    }
}
