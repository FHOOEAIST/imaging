/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

/**
 * <p>Function which is applied for a given column in the given image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@FunctionalInterface
public interface ColumnFunction {
    /**
     * Method which is applied for a given column in the given image
     *
     * @param image image
     * @param y     y coordinate
     */
    void apply(ImageWrapper<?> image, int y);
}

