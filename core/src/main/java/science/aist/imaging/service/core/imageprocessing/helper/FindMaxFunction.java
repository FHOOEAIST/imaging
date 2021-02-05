/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.helper;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;

import java.util.function.ToDoubleFunction;

/**
 * <p>Finds the maximum value in a single channel {@link ImageWrapper} of short[][][]</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class FindMaxFunction implements ToDoubleFunction<ImageWrapper<?>> {
    /**
     * The allowed types for this function
     */
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE, ChannelType.BINARY);

    @Override
    public double applyAsDouble(ImageWrapper<?> imageWrapper) {
        typeChecker.accept(imageWrapper);

        double max = Character.MIN_VALUE;
        for (int y = 0; y < imageWrapper.getHeight(); y++) {
            for (int x = 0; x < imageWrapper.getWidth(); x++) {
                double val = imageWrapper.getValue(x, y, 0);
                if (max < val) max = val;
            }
        }

        return max;
    }
}
