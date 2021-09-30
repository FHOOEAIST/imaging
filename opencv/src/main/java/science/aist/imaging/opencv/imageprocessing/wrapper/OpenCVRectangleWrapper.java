/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.AbstractRectangleWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 * <p>OpenCV Implementation of the RectangleWrapper</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVRectangleWrapper extends AbstractRectangleWrapper<Rect, Point> {

    public OpenCVRectangleWrapper(Rect rectangle) {
        super(rectangle);
    }

    public OpenCVRectangleWrapper(Point topLeftPoint, Point bottomRightPoint) {
        super(topLeftPoint, bottomRightPoint);
    }

    public OpenCVRectangleWrapper(double topLeftPointX, double topLeftPointY, double bottomRightPointX, double bottomRightPointY) {
        super(topLeftPointX, topLeftPointY, bottomRightPointX, bottomRightPointY);
    }

    /**
     * Method to create a new rectangle from to given points
     *
     * @param topLeftPoint     Top left point of the rectangle
     * @param bottomRightPoint Bottom right point of the rectangle
     * @return The created rectangle
     */
    @Override
    protected Rect buildRectangle(Point topLeftPoint, Point bottomRightPoint) {
        return new Rect(topLeftPoint, bottomRightPoint);
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
    @Override
    protected Rect buildRectangle(double topLeftPointX, double topLeftPointY, double bottomRightPointX, double bottomRightPointY) {
        return buildRectangle(new Point(topLeftPointX, topLeftPointY), new Point(bottomRightPointX, bottomRightPointY));
    }

    /**
     * @return The top left corner point of the rectangle
     */
    @Override
    public Point2Wrapper<Point> getTopLeftPoint() {
        return new OpenCVPoint2Wrapper(this.rectangle.tl());
    }

    /**
     * @return The bottom right corner point of the rectangle
     */
    @Override
    public Point2Wrapper<Point> getBottomRightPoint() {
        return new OpenCVPoint2Wrapper(this.rectangle.br());
    }

    /**
     * @return The center corner point of the rectangle
     */
    @Override
    public Point2Wrapper<Point> getCenterPoint() {
        return new OpenCVPoint2Wrapper(new Point(rectangle.tl().x + (rectangle.br().x - rectangle.tl().x) / 2,
                rectangle.tl().y + (rectangle.br().y - rectangle.tl().y) / 2));
    }

    /**
     * @return The width of the rectangle
     */
    @Override
    public double getWidth() {
        return rectangle.width;
    }

    /**
     * @return The height of the rectangle
     */
    @Override
    public double getHeight() {
        return rectangle.height;
    }


}
