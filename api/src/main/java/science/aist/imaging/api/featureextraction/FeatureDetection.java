/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.featureextraction;


import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>This interface is used to compute a `FeatureWrapper` of an image. This can be done with algorithms like
 * GradientMagnitude, CannyEdge, Sift or similar algorithms.</p>
 *
 * @param <E> Type of Elements in collection of the feature
 * @param <I> Type of Image wrapped by ImageWrapper
 * @author Christoph Praschl
 * @since 1.0
 */
public interface FeatureDetection<E, I> {
    /**
     * Method for computing feature of an image source.
     *
     * @param img                  is the source for which features should be extracted
     * @param additionalParameters additional parameters to set for the feature detection
     * @return A featurewrapper containing the computed features of the image.
     */
    FeatureWrapper<E> getFeature(ImageWrapper<I> img, String additionalParameters);

    /**
     * Method for computing feature of an image source.
     *
     * @param img                  is the source for which features should be extracted
     * @param additionalParameters additional parameters to set for the feature detection
     * @param bestX                the maximum number of elements that should be in the featureWrapper
     * @return A featurewrapper containing the computed features of the image.
     */
    FeatureWrapper<E> getFeature(ImageWrapper<I> img, String additionalParameters, int bestX);
}
