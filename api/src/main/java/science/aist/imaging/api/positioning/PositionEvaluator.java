/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.positioning;

import science.aist.imaging.api.domain.offset.TranslationOffsetInMM;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;

/**
 * <p>Interface which provides functionality to evaluate e.g. the position of an object in the image</p>
 *
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @author Christoph Praschl
 * @since 1.0
 */
public interface PositionEvaluator<I, P> {
    /**
     * Method for calibrating the PositionEvaluator
     *
     * @param imageForCalibration Image used for calibration
     */
    void calibrate(ImageWrapper<I> imageForCalibration);

    /**
     * Method for evaluating e.g. the position of an object in the image
     *
     * @param image Image where Object should be found.
     * @return Position of the Object in the image (Position -1/-1 -&gt; no position found)
     */
    Point2Wrapper<P> getPosition(ImageWrapper<I> image);

    /**
     * Method for evaluating the offset of an object between two images
     *
     * @param ref     The reference image containing the object
     * @param current The current image containing the object
     * @return The offset between the object´s position in ref and the object´s position in current
     */
    TranslationOffsetInMM getOffset(ImageWrapper<I> ref, ImageWrapper<I> current);
}
