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
 * <p>Generic interface which implements getter and setter for a line wrapper</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public abstract class AbstractLineWrapper<P> implements LineWrapper<P> {

    /**
     * Point which represents the start of the line
     */
    private Point2Wrapper<P> startPoint;
    /**
     * Point which represents the end of the line.
     */
    private Point2Wrapper<P> endPoint;

    /**
     * Constructor to set a start and a end point
     *
     * @param startPoint startPoint
     * @param endPoint   endPoint
     */
    public AbstractLineWrapper(Point2Wrapper<P> startPoint, Point2Wrapper<P> endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * @return startPoint
     */
    @Override
    public Point2Wrapper<P> getStartPoint() {
        return startPoint;
    }

    /**
     * @param startPoint startPoint
     */
    @Override
    public void setStartPoint(Point2Wrapper<P> startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * @return endPoint
     */
    @Override
    public Point2Wrapper<P> getEndPoint() {
        return endPoint;
    }

    /**
     * @param endPoint endPoint
     */
    @Override
    public void setEndPoint(Point2Wrapper<P> endPoint) {
        this.endPoint = endPoint;
    }
}
