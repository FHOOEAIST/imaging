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
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Inverts a given image.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class InvertFunction<T, R> implements ImageFunction<T, R> {
    @NonNull
    private final ImageFactory<R> provider;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        ImageWrapper<R> result = provider.getImage(height, width, imageWrapper.getChannelType());

        result.applyFunction((image, x, y, c) -> image.setValue(x, y, c, 255 - imageWrapper.getValue(x, y, c)));

        return result;
    }
}
