/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import java.util.Collection;

/**
 * <p>Interface for providing/saving unique and prominent information.</p>
 *
 * @param <E> element which is contained in features
 * @author Christoph Praschl
 * @since 1.0
 */
public interface FeatureWrapper<E> {
    /**
     * Getter for the contained features which represents the features.
     *
     * @return The collections of unique and prominent information
     */
    Collection<E> getFeatures();

    /**
     * Setter for the contained features which represents the features.
     *
     * @param collection The collections of unique and prominent information
     */
    void setFeatures(Collection<E> collection);

    /**
     * Method for adding a new feature.
     *
     * @param feature Feature which should be added.
     */
    void addFeature(E feature);

    /**
     * Method for removing a feature.
     *
     * @param feature Feature which should be removed.
     */
    void removeFeature(E feature);

    /**
     * Method for transforming the contained feature points
     *
     * @param imageHeight      The height of the image from which the features were extracted.
     * @param imageWidth       The width of the image from which the features were extracted.
     * @param xOffset          The offset on the x-axis
     * @param yOffset          The offset on the y-axis
     * @param rotationalOffset The rotational offset.
     * @return A collection of elements with features.
     */
    Collection<E> getTransformedFeatures(double imageWidth, double imageHeight, double xOffset, double yOffset, double rotationalOffset);
}
