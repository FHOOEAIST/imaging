/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.helper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.seshat.Logger;

import java.util.Collections;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

/**
 * <p>Normalizes a Greyscale image</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class NormalizeFunction<T, R> implements ImageFunction<T, R> {
    protected static final Logger logger = Logger.getInstance();
    private static final int WHITE = 255;
    private static final List<ChannelType> supportedTypes = Collections.singletonList(ChannelType.GREYSCALE);
    private final ToDoubleFunction<ImageWrapper<?>> findMax = new FindMaxFunction();

    @NonNull
    private final ImageFactory<R> provider;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        new TypeChecker(supportedTypes).accept(imageWrapper);

        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();

        ImageWrapper<R> result = provider.getImage(height, width, ChannelType.GREYSCALE);

        double max = findMax.applyAsDouble(imageWrapper);
        double factor = WHITE / max;
        logger.debug("NormalizeFunction Factor: " + factor);

        IntStream.range(0, height).parallel().forEach(y -> {
            for (int x = 0; x < width; x++) {
                result.setValue(x, y, 0, imageWrapper.getValue(x, y, 0) * factor + 0.5);
            }
        });

        return result;
    }
}
