/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.pooling;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.service.core.imageprocessing.filter.AbstractPoolingFunction;
import lombok.NonNull;

import java.util.List;

/**
 * <p>Implementation of a max pooling filter</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class MaxPoolingFunction<T, R> extends AbstractPoolingFunction<T, R> {
    public MaxPoolingFunction(@NonNull ImageFactory<R> provider) {
        super(provider);
    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "java:S3655"})
    @Override
    protected double pooling(List<Double> values) {
        return values.stream().max(Double::compareTo).get();
    }
}
