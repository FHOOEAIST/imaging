/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.fitnessfunction;


import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>This interface provides metrics for example `SumOfSquareDifferences` to compute the similarity of two images.</p>
 *
 * @param <E> Type of Elements in features of the feature
 * @param <I> Type of Image wrapped by ImageWrapper
 * @author Christoph Praschl
 * @since 1.0
 */

public interface FitnessFunction<E, I> {
    /**
     * This function is used to calculate similarity of two images
     *
     * @param a            The image which should be compared with b.
     * @param b            The image which is used for comparison.
     * @param dataToIgnore Data which should be ignored fo fitness calculation (e.g. Background color of an image). Can be null. Then no data will be ignored.
     * @return Returns a metrics representing the difference of a and b.
     * (0 = no differences)
     */
    double getFitness(ImageWrapper<I> a, ImageWrapper<I> b, RGBColor dataToIgnore);

    /**
     * This function is used to calculate similarity of two images
     *
     * @param a The image which should be compared with b.
     * @param b The image which is used for comparison.
     * @return Returns a metrics representing the difference of a and b.
     * (0 = no differences)
     */
    double getFitness(ImageWrapper<I> a, ImageWrapper<I> b);

    /**
     * This function is used to calculate similarity of two features.
     *
     * @param a The feature which should be compared with b.
     * @param b The feature which is used for comparison.
     * @return Returns a metrics representing the difference of a and b.
     * (0 = no differences)
     */
    double getFitness(FeatureWrapper<E> a, FeatureWrapper<E> b);
}
