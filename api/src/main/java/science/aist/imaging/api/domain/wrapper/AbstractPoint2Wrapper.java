/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import java.util.Objects;

/**
 * <p>Abstract generic implementation of the Point2Wrapper interface.
 * Getter and Setter for coordinate-values (not for the wrapped point) have to be overwritten in inherited class!</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 * @param <P> The type of the point
 */
public abstract class AbstractPoint2Wrapper<P> implements Point2Wrapper<P> {
    protected P point; // field containing the point

    /**
     * Constructor for a new AbstractPoint2Wrapper
     *
     * @param point The point which should be wrapped
     */
    public AbstractPoint2Wrapper(P point) {
        this.point = point;
    }

    /**
     * Getter for the wrapped point object.
     *
     * @return Returns the wrapped point object.
     */
    @Override
    public P getPoint() {
        return point;
    }

    /**
     * Setter for the wrapped point object
     *
     * @param point AbstractPoint2Wrapper which should be wrapped.
     */
    @Override
    public void setPoint(P point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    /**
     * Generated Code
     *
     * @param o value to compare
     * @return true if values are equals else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPoint2Wrapper<?> that = (AbstractPoint2Wrapper<?>) o;
        return Objects.equals(point, that.point);
    }

    /**
     * Generated Code
     *
     * @return hashCode for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(point);
    }
}
