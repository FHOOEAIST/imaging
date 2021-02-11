/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.jack.general.util.CastUtils;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Returns a image factory based on a given type</p>
 *
 * @author Andreas Pointner
 * @since 1.1
 */
public class ImageFactoryFactory {

    private static Map<Class<?>, ImageFactory<?>> cache;

    private ImageFactoryFactory() {
    }

    /**
     * This method uses the {@link ServiceLoader} to load all available {@link ImageFactory} implementation. Then it
     * checks if the given type matched the type supported by the {@link ImageFactory} ({@link
     * ImageFactory#getSupportedType()}. If multiple image factories exists, that do return the same supported type, an
     * {@link IllegalStateException} with duplicated key, will be raised. If the given type is not supported by any of
     * the loaded factories an {@link IllegalStateException} will be raised.
     *
     * @param type The class of the type of the image factory that should be provided.
     * @param <T>  The type of the resulting image factory.
     * @return If a factory for the type exists, an instance of a suitable {@link ImageFactory} will be returned.
     * Otherwise a {@link IllegalStateException} will be raised.
     * @throws IllegalStateException if two factories have the same support type or if no factory supports the given
     *                               type.
     */
    public static <T> ImageFactory<T> getImageFactory(Class<T> type) {
        if (cache == null)
            cache = CastUtils.cast(StreamSupport.stream(ServiceLoader.load(ImageFactory.class).spliterator(), false)
                    .collect(Collectors.toMap(ImageFactory::getSupportedType, Function.identity())));
        if (!cache.containsKey(type)) throw new IllegalStateException("No ImageFactory for type " + type + " present.");
        return CastUtils.cast(cache.get(type));
    }
}
