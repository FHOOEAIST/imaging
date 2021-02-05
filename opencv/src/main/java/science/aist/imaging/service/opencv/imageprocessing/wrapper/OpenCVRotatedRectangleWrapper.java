/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RotatedRectangleWrapper;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Wraps {@link RotatedRect}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class OpenCVRotatedRectangleWrapper implements RotatedRectangleWrapper<RotatedRect, Point> {
    /**
     * the wrapped object
     * caution: {@link RotatedRect#angle} returns a value in degree!
     */
    private RotatedRect rotatedRect;

    /**
     * @param rotatedRect the wrapped rotated rectangle
     */
    public OpenCVRotatedRectangleWrapper(RotatedRect rotatedRect) {
        this.rotatedRect = rotatedRect;
    }

    /**
     * Create a rotated rectangle and wraps it using this wrapper.
     *
     * @param center the center point of the rectangle
     * @param width  the width
     * @param height the height
     * @param angle  the getRotation or angle in radians
     * @return the resulting rectangle wrapped in the wrapper
     */
    public static RotatedRectangleWrapper<RotatedRect, Point> create(Point2Wrapper<Point> center, double width, double height, double angle) {
        return new OpenCVRotatedRectangleWrapper(new RotatedRect(center.getPoint(), new Size(width, height), Math.toDegrees(angle)));
    }

    @Override
    public Point2Wrapper<Point> getTopRightPoint() {
        return getPoints().get(2);
    }

    @Override
    public Point2Wrapper<Point> getBottomLeftPoint() {
        return getPoints().get(0);
    }

    @Override
    public List<Point2Wrapper<Point>> getPoints() {
        // Receive the corner points in order bottomLeft, topLeft, topRight, bottomRight (since 3.4, no order in 3.2 documented)
        Point[] vertices = new Point[4];
        rotatedRect.points(vertices);
        return Arrays
                .stream(vertices)
                .map(OpenCVPoint2Wrapper::new)
                .collect(Collectors.toList());
    }

    @Override
    public double getRotation() {
        return Math.toRadians(rotatedRect.angle);
    }

    @Override
    public RotatedRect getRectangle() {
        return rotatedRect;
    }

    @Override
    public void setRectangle(RotatedRect rectangle) {
        this.rotatedRect = rectangle;
    }

    @Override
    public Point2Wrapper<Point> getTopLeftPoint() {
        return getPoints().get(1);
    }

    @Override
    public Point2Wrapper<Point> getBottomRightPoint() {
        return getPoints().get(3);
    }

    @Override
    public Point2Wrapper<Point> getCenterPoint() {
        return new OpenCVPoint2Wrapper(rotatedRect.center);
    }

    @Override
    public double getWidth() {
        return rotatedRect.size.width;
    }

    @Override
    public double getHeight() {
        return rotatedRect.size.height;
    }
}
