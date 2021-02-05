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

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>Exception if a ImageWrapper does not matches a specific channel type</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class TypeException extends RuntimeException {
    /**
     * @param supportedTypes a list of the channel types which would be supported
     * @param actual the channel type that was provided.
     */
    public TypeException(Collection<ChannelType> supportedTypes, ChannelType actual) {
        super("Does not support " + actual.name() + " use one of: " + supportedTypes.stream().map(ChannelType::name).collect(Collectors.joining(", ")));
    }
}
