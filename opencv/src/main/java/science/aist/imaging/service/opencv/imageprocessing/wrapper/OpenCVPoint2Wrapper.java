/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.AbstractPoint2Wrapper;
import org.opencv.core.Point;

/**
 * <p>Class for wrapping a two-dimensional point of the opencv framework.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@SuppressWarnings({"common-java:DuplicatedBlocks", "java:S1695"})
public class OpenCVPoint2Wrapper extends AbstractPoint2Wrapper<Point> {
    private static final String ERROR = "There is no wrapped point.";

    /**
     * Constructor for a new AbstractPoint2Wrapper
     *
     * @param point The point which should be wrapped
     */
    public OpenCVPoint2Wrapper(Point point) {
        super(point);
    }

    /**
     * @param x x-coordinate of the wrapped point
     * @param y y-coordinate of the wrapped point
     */
    public OpenCVPoint2Wrapper(int x, int y) {
        this((double) x, y);
    }

    /**
     * @param x x-coordinate of the wrapped point
     * @param y y-coordinate of the wrapped point
     */
    public OpenCVPoint2Wrapper(double x, double y) {
        super(new Point(x, y));
    }

    /**
     * Getter for the x-coordinate
     *
     * @return Returns the value of the x-coordinate.
     */
    @Override
    public double getX() {
        if (point == null) {
            throw new NullPointerException(ERROR);
        }
        return point.x;
    }

    /**
     * Setter for the x-coordinate
     *
     * @param x value which should be assigned to the x-coordinate
     */
    @Override
    public void setX(double x) {
        if (point == null) throw new NullPointerException(ERROR);
        point.x = x;
    }

    /**
     * Getter for the y-coordinate
     *
     * @return Returns the value of the y-coordinate.
     */
    @Override
    public double getY() {
        if (point == null) throw new NullPointerException(ERROR);
        return point.y;
    }

    /**
     * Setter for the y-coordinate
     *
     * @param y value which should be assigned to the y-coordinate
     */
    @Override
    public void setY(double y) {
        if (point == null) throw new NullPointerException(ERROR);
        point.y = y;
    }
}
