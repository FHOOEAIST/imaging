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
 * <p>Interface which wraps a three dimensional point.</p>
 *
 * @param <P> Target type representing the point which should be wrapped.
 * @author Christoph Praschl
 * @since 1.0
 */
public interface Point3Wrapper<P> extends Point2Wrapper<P> {
    /**
     * Getter for the z-coordinate
     *
     * @return Returns the value of the z-coordinate, if this is a three-dimensional point.
     */
    double getZ();

    /**
     * Setter for the z-coordinate
     *
     * @param z value which should be assigned to the z-coordinate, if this is a three-dimensional point.
     */
    void setZ(double z);
}
