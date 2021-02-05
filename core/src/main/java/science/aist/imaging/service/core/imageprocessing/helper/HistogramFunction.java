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
import lombok.Setter;

import java.util.function.Function;

/**
 * <p>Calculates the HistogramFunction of a given image</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class HistogramFunction<T> implements Function<ImageWrapper<T>, int[]> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE);

    private int maxValue = 256;

    @Override
    public int[] apply(ImageWrapper<T> imageWrapper) {
        typeChecker.accept(imageWrapper);
        int[] histogram = new int[maxValue];

        for (int y = 0; y < imageWrapper.getHeight(); y++) {
            for (int x = 0; x < imageWrapper.getWidth(); x++) {
                histogram[(int) (imageWrapper.getValue(x, y, 0) + 0.5)]++;
            }
        }

        return histogram;
    }
}
