/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.registration;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Collection;

/**
 * <p>Interface for different Registration Methods</p>
 *
 * @param <I> Type of Image wrapped by ImageWrapper
 * @author Andreas Pointner
 * @since 1.0
 */
public interface Registration<I> {
    /**
     * This function applies image registration on given elements.
     *
     * @param ref      the ref image, where the elements should fit to
     * @param elements the elements, which should to fit the given ref
     * @return the result elements fitting to the ref image
     */
    Collection<ImageWrapper<I>> register(ImageWrapper<I> ref, Collection<ImageWrapper<I>> elements);
}
