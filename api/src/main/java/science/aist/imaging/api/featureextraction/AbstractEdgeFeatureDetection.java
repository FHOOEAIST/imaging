/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.featureextraction;

/**
 * @param <E> Type of Elements in collection of the feature
 * @param <I> Type of Image wrapped by ImageWrapper
 * @author Andreas Pointner
 * @since 1.0
 */
public abstract class AbstractEdgeFeatureDetection<E, I> implements FeatureDetection<E, I> {
    /**
     * Defines each x points which are interesting. E.q. with = 3 just every third point will be used as feature.
     */
    private int stepsOfInterestingPoints = 5;

    /**
     * @return steps of point which are interesting
     */
    public int getStepsOfInterestingPoints() {
        return stepsOfInterestingPoints;
    }

    /**
     * @param stepsOfInterestingPoints steps of point which are interesting
     */
    public void setStepsOfInterestingPoints(int stepsOfInterestingPoints) {
        this.stepsOfInterestingPoints = stepsOfInterestingPoints;
    }
}
