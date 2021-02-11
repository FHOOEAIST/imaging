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
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.function.Function;

/**
 * <p>Function for adding padding to a given image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class PaddingFunction<I, R> implements Function<ImageWrapper<I>, ImageWrapper<R>> {

    @NonNull
    private ImageFactory<R> provider;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    @NonNull
    private double[] defaultValue;

    /**
     * Sets {@link PaddingFunction#paddingLeft} and {@link PaddingFunction#paddingRight}
     *
     * @param value to be set
     */
    public void setPaddingHorizontal(int value) {
        setPaddingLeft(value);
        setPaddingRight(value);
    }

    /**
     * Sets {@link PaddingFunction#paddingTop} and {@link PaddingFunction#paddingBottom}
     *
     * @param value to be set
     */
    public void setPaddingVertical(int value) {
        setPaddingTop(value);
        setPaddingBottom(value);
    }

    /**
     * Sets all paddings equally
     *
     * @param value to be set
     */
    public void setPaddingUnify(int value) {
        setPaddingHorizontal(value);
        setPaddingVertical(value);
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<I> wrapper) {
        int origWidth = wrapper.getWidth();
        int origHeight = wrapper.getHeight();
        int width = origWidth + paddingLeft + paddingRight;
        int height = origHeight + paddingBottom + paddingTop;

        ImageWrapper<R> provide = provider.getImage(height, width, wrapper.getChannelType(), defaultValue);
        provide.applyFunction((image, x, y, c) -> {
                    double value = wrapper.getValue(x - paddingLeft, y - paddingTop, c);
                    image.setValue(x, y, c, value);
                },
                paddingLeft, paddingTop,
                paddingLeft + origWidth,
                paddingTop + origHeight);

        return provide;
    }
}
