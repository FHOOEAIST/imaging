/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Converts a BGR image into a RGB image and vice versa</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class RGB2BGRTransformer<P, T> implements Transformer<ImageWrapper<P>, ImageWrapper<T>> {
    private static final TypeChecker toTC = new TypeChecker(ChannelType.RGB);
    private static final TypeChecker fromTC = new TypeChecker(ChannelType.BGR);

    @NonNull
    private final ImageFactory<P> pProvider;
    @NonNull
    private final ImageFactory<T> tProvider;

    private static <R, I> ImageWrapper<R> helper(ImageWrapper<I> imageWrapper, ChannelType newType, TypeChecker checker, ImageFactory<R> resultProvider) {
        checker.accept(imageWrapper);

        final int height = imageWrapper.getHeight();
        final int width = imageWrapper.getWidth();
        final int channels = imageWrapper.getChannels();

        ImageWrapper<R> resultImageWrapper = resultProvider.getImage(height, width, newType);
        resultImageWrapper.applyFunction((image, x, y, c) -> image.setValue(x, y, c, imageWrapper.getValue(x, y, channels - 1 - c)));

        return resultImageWrapper;
    }


    @Override
    public ImageWrapper<P> transformTo(ImageWrapper<T> rgbaImageWrapper) {
        return helper(rgbaImageWrapper, ChannelType.RGB, fromTC, pProvider);
    }

    @Override
    public ImageWrapper<T> transformFrom(ImageWrapper<P> rgbImageWrapper) {
        return helper(rgbImageWrapper, ChannelType.BGR, toTC, tProvider);
    }
}
