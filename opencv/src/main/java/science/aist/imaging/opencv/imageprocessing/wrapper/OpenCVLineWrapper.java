/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.AbstractLineWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import org.opencv.core.Point;

/**
 * <p>Specific Implementation for opencv line, which explicitly uses Point.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class OpenCVLineWrapper extends AbstractLineWrapper<Point> {

    /**
     * Constructor to set a line with the coordinate of its points in integer
     *
     * @param x1 x-coordinate of start point
     * @param y1 y-coordinate of start point
     * @param x2 x-coordinate of end point
     * @param y2 y-coordinate of end point
     */
    public OpenCVLineWrapper(int x1, int y1, int x2, int y2) {
        this((double) x1, (double) y1, (double) x2, (double) y2);
    }

    /**
     * Constructor to set a line with the coordinate of its points in double
     *
     * @param x1 x-coordinate of start point
     * @param y1 y-coordinate of start point
     * @param x2 x-coordinate of end point
     * @param y2 y-coordinate of end point
     */
    public OpenCVLineWrapper(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Constructor to set a line with opencv Points
     *
     * @param startPoint start point
     * @param endPoint   end point
     */
    public OpenCVLineWrapper(Point startPoint, Point endPoint) {
        this(new OpenCVPoint2Wrapper(startPoint), new OpenCVPoint2Wrapper(endPoint));
    }

    /**
     * Constructor to set a line with Point2Wrappers
     *
     * @param startPoint start point wrapper
     * @param endPoint   end point wrapper
     */
    public OpenCVLineWrapper(Point2Wrapper<Point> startPoint, Point2Wrapper<Point> endPoint) {
        super(startPoint, endPoint);
    }
}
