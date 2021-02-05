/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.optimization;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Objects of this interface are used to improve the quality and/or speed of image processing algorithms.</p>
 *
 * @param <I> Type of Image wrapped by ImageWrapper
 * @author Christoph Praschl
 * @since 1.0
 */

public interface Optimizer<I> {
    /**
     * Method for calculating the positional and rotational  offset of two images in an optimized way.
     *
     * @param ref     The image which is used for comparison.
     * @param current The image which should be compared with ref
     * @return Rotational and Translational offset between ref and current
     */
    RotationOffset optimize(ImageWrapper<I> ref, ImageWrapper<I> current);

    /**
     * Method for calculating the positional and rotational offset of two images in an optimized way.
     *
     * @param ref                      The image which is used for comparison.
     * @param current                  The image which should be compared with ref
     * @param numberOfIterations       Max. number of iterations which should be done.
     * @param positionalRadius         The search window for positional offset (look between -positionalRadius to +positionRadius. e.g. if image is translated max +/- 10 pixels on x-axis and y-axis)
     * @param rotationalRadius         The search window for rotational offset (look between -rotationalRadius to +rotationalRadius. e.g. if image is translated max +/- 2 degrees)
     * @param stepsOfInterestingPoints Defines each x points which are interesting for feature detection. E.q. with = 3 just every third point will be used as feature.
     * @param degressionRate           degression of positionalRadius and rotationalRadius per iteration
     * @param paddingFillColor         Color which is used for filling new pixels after padding.
     * @return Rotational and Translational offset between ref and current
     */
    RotationOffset optimize(ImageWrapper<I> ref, ImageWrapper<I> current, int numberOfIterations, int positionalRadius, int rotationalRadius, int stepsOfInterestingPoints, double degressionRate, RGBColor paddingFillColor);

    /**
     * Method for calculating the offset of two images.
     *
     * @param ref     The image which is used for comparison.
     * @param current The image which should be compared with ref
     * @return Rotational and Translational offset between ref and current
     */
    TranslationOffset optimizePositionalOffset(ImageWrapper<I> ref, ImageWrapper<I> current);

}
