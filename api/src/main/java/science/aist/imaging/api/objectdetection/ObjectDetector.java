/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.objectdetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;

/**
 * <p>Interface for a color based Object Detector</p>
 *
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <R> Type of Rectangle wrapped by RectangleWrapper
 * @author Christoph Praschl
 * @since 1.0
 */
public interface ObjectDetector<I, P, R> {
    /**
     * Method for detecting an object.
     *
     * @param image Image where object should be detected
     * @return Returns the center point of the detected object.
     */
    Point2Wrapper<P> getObjectCenter(ImageWrapper<I> image);

    /**
     * Method for detecting an object.
     *
     * @param image Image where object should be detected
     * @return Returns the boundingbox of the detected object.
     */
    RectangleWrapper<R, P> getBoundingBox(ImageWrapper<I> image);
}
