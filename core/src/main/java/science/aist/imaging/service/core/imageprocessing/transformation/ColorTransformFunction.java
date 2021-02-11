/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.function.Function;

/**
 * <p>Function that allows to change the color of the given image</p>
 * <p>Can be used to e.g. implement a LookUpTable or InvertFilter</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public class ColorTransformFunction<T, R> implements ImageFunction<T, R> {
    @NonNull
    private final ImageFactory<R> provider;

    @NonNull
    private final Function<Color, Color> colorTransformer;

    @Setter
    private ChannelType expectedChannelType = null;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> wrapper) {
        ChannelType ct;
        if (expectedChannelType == null) {
            ct = wrapper.getChannelType();
        } else {
            ct = expectedChannelType;
        }

        ImageWrapper<R> provide = provider.getImage(wrapper.getHeight(), wrapper.getWidth(), ct);

        provide.applyFunction((image, x, y, c) -> {
            Color values = wrapper.getValuesAsColor(x, y);
            Color apply = colorTransformer.apply(values);
            image.setValues(x, y, apply);
        });

        return provide;
    }
}
