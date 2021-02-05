/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.AbstractPoint3Wrapper;
import org.opencv.core.Point3;

/**
 * <p>Class for wrapping a three-dimensional point of the opencv framework.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@SuppressWarnings({"common-java:DuplicatedBlocks", "java:S1695"})
public class OpenCVPoint3Wrapper extends AbstractPoint3Wrapper<Point3> {
    private static final String ERROR = "There is no wrapped point.";

    public OpenCVPoint3Wrapper(double x, double y, double z) {
        super(new Point3(x, y, z));
    }

    /**
     * Constructor for a new AbstractPoint2Wrapper
     *
     * @param point The point which should be wrapped
     */
    public OpenCVPoint3Wrapper(Point3 point) {
        super(point);
    }

    /**
     * Getter for the x-coordinate
     *
     * @return Returns the value of the x-coordinate.
     */
    @Override
    public double getX() {
        if (point == null) throw new NullPointerException(ERROR);
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

    /**
     * Getter for the z-coordinate
     *
     * @return Returns the value of the z-coordinate, if this is a three-dimensional point.
     */
    @Override
    public double getZ() {
        if (point == null) throw new NullPointerException(ERROR);
        return point.z;
    }

    /**
     * Setter for the z-coordinate
     *
     * @param z value which should be assigned to the z-coordinate, if this is a three-dimensional point.
     */
    @Override
    public void setZ(double z) {
        if (point == null) throw new NullPointerException(ERROR);
        point.z = z;
    }
}
