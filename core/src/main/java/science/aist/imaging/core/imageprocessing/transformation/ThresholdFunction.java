/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;

/**
 * <p>Applies a threshold on a given greyscale image and creates a binary one</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class ThresholdFunction<T, R> implements ImageFunction<T, R> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE, ChannelType.BINARY);
    @NonNull
    private final ImageFactory<R> provider;
    /**
     * lower thresh
     */
    private double lowerThresh;

    /**
     * upper thresh (ignored if not set)
     */
    private Double upperThresh = null;

    /**
     * foreground default 255
     */
    private double foreground = 255;

    /**
     * background default 0
     */
    private double background = 0;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        typeChecker.accept(imageWrapper);

        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();
        ImageWrapper<R> result = provider.getImage(height, width, ChannelType.BINARY);

        result.applyFunction((image, x, y, c) -> {
            double val = imageWrapper.getValue(x, y, c);
            image.setValue(x, y, c, val > lowerThresh && (upperThresh != null && val < upperThresh) || val > lowerThresh && upperThresh == null ? foreground : background);
        });

        return result;
    }
}
