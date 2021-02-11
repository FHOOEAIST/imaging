/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Class for splitting an image into its channels</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
public class ChannelSplitter<I, T> implements Function<ImageWrapper<I>, List<ImageWrapper<T>>> {
    @NonNull
    private final ImageFactory<T> provider;

    @Override
    public List<ImageWrapper<T>> apply(ImageWrapper<I> wrapper) {
        List<ImageWrapper<T>> result = new ArrayList<>();

        for (int i = 0; i < wrapper.getChannels(); i++) {
            final int idx = i;
            ImageWrapper<T> provide = provider.getImage(wrapper.getHeight(), wrapper.getWidth(), ChannelType.GREYSCALE);
            provide.applyFunction((image, x, y, c) -> {
                double value = wrapper.getValue(x, y, idx);
                provide.setValue(x, y, c, value);
            });
            result.add(provide);
        }

        return result;
    }
}
