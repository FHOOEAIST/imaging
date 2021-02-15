/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.jack.general.util.CastUtils;

import java.util.function.BiConsumer;

/**
 * <p>Generic image consumer that applies a wrapped consumer to a given image</p>
 *
 * <p>Note: The {@link GenericImageConsumer} converts the input image if necessary, and for this effects the performance because of the required copy operation.</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 * @param <I> Input type of this generic image consumer
 * @param <I2> Input type of the wrapped image consumer
 * @param <T> type of the secondary consumed class
 */
public class GenericImageConsumer<I, I2, T> implements BiConsumer<ImageWrapper<I>, T> {
    private final BiConsumer<ImageWrapper<I2>, T> consumer;
    private final ImageFactory<I2> consumerFactory;

    public GenericImageConsumer(BiConsumer<ImageWrapper<I2>, T> consumer, Class<I2> consumerInputType) {
        this(consumer, ImageFactoryFactory.getImageFactory(consumerInputType));
    }

    public GenericImageConsumer(BiConsumer<ImageWrapper<I2>, T> consumer, ImageFactory<I2> consumerFactory) {
        this.consumer = consumer;
        this.consumerFactory = consumerFactory;
    }

    @Override
    public void accept(ImageWrapper<I> iImageWrapper, T t) {
        if(iImageWrapper.getSupportedType().equals(consumerFactory.getSupportedType())){
            consumer.accept(CastUtils.cast(iImageWrapper), t);
        } else {
            ImageWrapper<I2> copy = iImageWrapper.createCopy(consumerFactory);
            consumer.accept(copy, t);
            copy.copyTo(iImageWrapper);
        }
    }
}
