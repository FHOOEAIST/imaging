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
public abstract class AbstractPointFeatureDetection<E, I> implements FeatureDetection<E, I> {
    /**
     * Feature Extraction Type
     */
    private ExtractorType extractorType = ExtractorType.FAST;

    /**
     * @return feature extraction type
     */
    public ExtractorType getExtractorType() {
        return extractorType;
    }

    /**
     * @param extractorType feature extraction type
     */
    public void setType(ExtractorType extractorType) {
        this.extractorType = extractorType;
    }

    /**
     * Enum with Feature Extration Types
     */
    public enum ExtractorType {
        FAST, STAR, SIFT, SURF, ORB, MSER, GFTT, HARRIS, SIMPLEBLOB, DENSE, BRISK, AKAZE
    }
}
