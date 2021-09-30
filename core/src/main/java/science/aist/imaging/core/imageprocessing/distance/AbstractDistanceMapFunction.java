/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.distance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.function.Function;

/**
 * <p>Abstract implementation of an distance map function containing the required contour color value</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
public abstract class AbstractDistanceMapFunction<T> implements Function<ImageWrapper<?>, ImageWrapper<T>> {
    @Getter
    protected double contourColour;

    @Getter
    protected AbstractDistanceMetric distanceMetric;
}
