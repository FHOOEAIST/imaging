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
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;

import java.util.function.BiFunction;

/**
 * <p>Allows to apply a transformation function to a given image. Please make sure, that every possible value of the
 * image is set as an index of the transformation function. This wont be checked, and will lead to a exception otherwise!</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class ValueTransformationFunction<P, T> implements BiFunction<ImageWrapper<P>, int[], ImageWrapper<T>> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.BINARY, ChannelType.GREYSCALE);

    @NonNull
    private ImageFactory<T> provider;


    @Override
    public ImageWrapper<T> apply(ImageWrapper<P> input, int[] transformationFunction) {
        typeChecker.accept(input);

        int width = input.getWidth();
        int height = input.getHeight();

        ImageWrapper<T> result = provider.getImage(height, width, ChannelType.GREYSCALE);
        result.applyFunction((image, x, y, c) -> image.setValue(x, y, 0, transformationFunction[(int) (input.getValue(x, y, 0) + 0.5)]));

        return result;
    }
}
