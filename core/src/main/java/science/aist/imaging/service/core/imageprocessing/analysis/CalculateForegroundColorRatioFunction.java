/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.analysis;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.Setter;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;

/**
 * <p>Calculates the ratio of a specific foreground color. Therefore it counts how many pixels equals foregroundColor.
 * Then it returns count(pixels) / (width * height)</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class CalculateForegroundColorRatioFunction<T> implements ToDoubleFunction<ImageWrapper<T>> {

    /**
     * Allowed types for this function
     */
    private static final TypeChecker typeChecker = new TypeChecker(Arrays.asList(ChannelType.GREYSCALE, ChannelType.BINARY));

    /**
     * the color to search
     */
    private short foregroundColor;

    @Override
    public double applyAsDouble(ImageWrapper<T> value) {
        typeChecker.accept(value);

        AtomicInteger cnt = new AtomicInteger(0);
        value.applyFunction((image, x, y, c) -> {
            if (value.getValue(x, y, 0) == foregroundColor)
                cnt.incrementAndGet();
        }, true);

        return (double) cnt.get() / (double) (value.getWidth() * value.getHeight());
    }
}
