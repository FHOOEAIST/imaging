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
 * <p>Interface which wraps a two dimensional point.</p>
 *
 * @param <P> Target type representing the point which should be wrapped.
 * @author Christoph Praschl
 * @since 1.0
 */
public interface Point2Wrapper<P> {
    /**
     * Getter for the wrapped point object.
     *
     * @return Returns the wrapped point object.
     */
    P getPoint();

    /**
     * Setter for the wrapped point object
     *
     * @param point AbstractPoint2Wrapper which should be wrapped.
     */
    void setPoint(P point);


    /**
     * Getter for the x-coordinate
     *
     * @return Returns the value of the x-coordinate.
     */
    double getX();

    /**
     * Setter for the x-coordinate
     *
     * @param x value which should be assigned to the x-coordinate
     */
    void setX(double x);

    /**
     * Getter for the y-coordinate
     *
     * @return Returns the value of the y-coordinate.
     */
    double getY();

    /**
     * Setter for the y-coordinate
     *
     * @param y value which should be assigned to the y-coordinate
     */
    void setY(double y);
}
