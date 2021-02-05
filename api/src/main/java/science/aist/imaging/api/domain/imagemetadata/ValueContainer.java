/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.imagemetadata;

import java.io.Serializable;

/**
 * <p>Interface for containers that contain a value and want to provide a public getter</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public interface ValueContainer<T> extends Serializable {
    /**
     * Returns the value of the container
     *
     * @return the contained value
     */
    T getValue();
}
