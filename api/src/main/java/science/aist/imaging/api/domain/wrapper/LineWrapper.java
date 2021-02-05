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
 * <p>Interface which is used, to represent a line.</p>
 *
 * @param <P> Target type representing the point which should be wrapped.
 * @author Andreas Pointner
 * @since 1.0
 */
public interface LineWrapper<P> {
    /**
     * @return startPoint
     */
    Point2Wrapper<P> getStartPoint();

    /**
     * @param point startPoint
     */
    void setStartPoint(Point2Wrapper<P> point);

    /**
     * @return endPoint
     */
    Point2Wrapper<P> getEndPoint();

    /**
     * @param point endPoint
     */
    void setEndPoint(Point2Wrapper<P> point);
}
