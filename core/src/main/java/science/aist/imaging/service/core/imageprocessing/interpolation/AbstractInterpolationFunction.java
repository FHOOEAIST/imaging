/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.interpolation;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

/**
 * <p>Interface for interpolations</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
public abstract class AbstractInterpolationFunction implements BiFunction<ImageWrapper<?>, JavaPoint2D, Double> {
    @Getter
    protected double backgroundColor;
}
