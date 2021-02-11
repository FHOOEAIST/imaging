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

import java.util.List;
import java.util.function.BiFunction;

/**
 * <p>Class for merging multiple images to one image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
public class ChannelMerger<I, T> implements BiFunction<List<ImageWrapper<I>>, ChannelType, ImageWrapper<T>> {
    @NonNull
    private final ImageFactory<T> provider;

    @Override
    public ImageWrapper<T> apply(List<ImageWrapper<I>> imageWrapperList, ChannelType channelType) {
        if (imageWrapperList.size() != channelType.getNumberOfChannels()) {
            throw new IllegalArgumentException("Number of given channels does not match the channeltype");
        }
        ImageWrapper<I> firstElem = imageWrapperList.get(0);
        int width = firstElem.getWidth();
        int height = firstElem.getHeight();
        ImageWrapper<T> result = provider.getImage(height, width, channelType);

        result.applyFunction((image, x, y, c) -> {
            ImageWrapper<I> iImageWrapper = imageWrapperList.get(c);
            if (iImageWrapper.getChannels() != 1) {
                throw new IllegalArgumentException("Channel images must have exactly one channel. This is not fulfilled");
            }

            if (iImageWrapper.getWidth() != width || iImageWrapper.getHeight() != height) {
                throw new IllegalArgumentException("Given channel image does not have the expected size");
            }

            double value = iImageWrapper.getValue(x, y, 0);
            image.setValue(x, y, c, value);
        });


        return result;
    }
}
