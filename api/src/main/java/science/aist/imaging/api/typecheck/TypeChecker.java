/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.typecheck;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * <p>Checks if the image wrapper matches to one of the supported types</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class TypeChecker implements Consumer<ImageWrapper<?>> {
    /**
     * The supported types
     */
    private Collection<ChannelType> supportedTypes;

    /**
     * @param supportedTypes The supported types
     */
    public TypeChecker(Collection<ChannelType> supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    /**
     * @param supportedTypes The supported types
     */
    public TypeChecker(ChannelType... supportedTypes) {
        this.supportedTypes = Arrays.asList(supportedTypes);
    }

    /**
     * Checks if the imageWrapper fits to one of the supported types
     *
     * @param imageWrapper the image wrapper
     * @throws TypeException if the image wrapper is not one of the supported types
     */
    @Override
    public void accept(ImageWrapper<?> imageWrapper) {
        if (!supportedTypes.contains(imageWrapper.getChannelType())) {
            throw new TypeException(supportedTypes, imageWrapper.getChannelType());
        }
    }
}
