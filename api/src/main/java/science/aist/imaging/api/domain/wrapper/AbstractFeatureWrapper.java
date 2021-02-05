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
 * <p>Class for holding a features of unique and prominent information (for example of points).</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractFeatureWrapper<E> implements FeatureWrapper<E> {
    protected Collection<E> features;

    /**
     * Constructor for construction a FeatureWrapper of given Collection
     *
     * @param features collection which contains all features
     */
    public AbstractFeatureWrapper(Collection<E> features) {
        if (features == null) throw new IllegalArgumentException("Given collection must not be null.");
        this.features = features;
    }

    /**
     * Getter for the contained features which represents the features.
     *
     * @return The collections of unique and prominent information
     */
    @Override
    public Collection<E> getFeatures() {
        return features;
    }

    /**
     * Setter for the contained features which represents the features.
     *
     * @param collection The collections of unique and prominent information
     */
    @Override
    public void setFeatures(Collection<E> collection) {
        this.features = collection;
    }

    /**
     * Method for adding a new feature.
     *
     * @param feature Feature which should be added.
     */
    @Override
    public void addFeature(E feature) {
        features.add(feature);
    }

    /**
     * Method for removing a feature.
     *
     * @param feature Feature which should be removed.
     */
    @Override
    public void removeFeature(E feature) {
        features.remove(feature);
    }
}
