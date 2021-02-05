/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import science.aist.jack.general.transformer.Transformer;

import java.util.stream.IntStream;

/**
 * <p>Generic transformer which allows to transform between given ImageWrapper types</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class GenericImageWrapperTransformer<T, P> implements Transformer<ImageWrapper<T>, ImageWrapper<P>> {
    @NonNull
    private ImageFactory<T> tProvider;

    @NonNull
    private ImageFactory<P> pProvider;

    @Override
    public ImageWrapper<T> transformTo(@NonNull ImageWrapper<P> image) {
        ImageWrapper<T> provide = tProvider.getImage(image.getHeight(), image.getWidth(), image.getChannelType());

        transferData(image, provide);

        return provide;
    }

    @Override
    public ImageWrapper<P> transformFrom(@NonNull ImageWrapper<T> image) {
        ImageWrapper<P> provide = pProvider.getImage(image.getHeight(), image.getWidth(), image.getChannelType());

        transferData(image, provide);

        return provide;
    }

    /**
     * Moves the pixel information from wrapper1 to wrapper2
     *
     * @param wrapper1 source of pixels
     * @param wrapper2 target of pixels
     */
    protected void transferData(ImageWrapper<?> wrapper1, ImageWrapper<?> wrapper2) {
        if (wrapper2.supportsParallelAccess()) {
            IntStream.range(0, wrapper1.getWidth()).parallel().forEach(x ->
                    transferData(wrapper1, wrapper2, x)
            );
        } else {
            for (int x = 0; x < wrapper1.getWidth(); x++) {
                transferData(wrapper1, wrapper2, x);
            }
        }
    }

    /**
     * Moves the pixel information from wrapper1 to wrapper2 for a given row
     *
     * @param wrapper1 source of pixels
     * @param wrapper2 target of pixels
     * @param x        row index
     */
    protected void transferData(ImageWrapper<?> wrapper1, ImageWrapper<?> wrapper2, int x) {
        for (int y = 0; y < wrapper1.getHeight(); y++) {
            for (int c = 0; c < wrapper1.getChannels(); c++) {
                wrapper2.setValue(x, y, c, wrapper1.getValue(x, y, c));
            }
        }
    }
}