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
 * <p>Function which is applied for a given pixel in the given image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@FunctionalInterface
public interface PixelFunction {
    /**
     * Method which is applied for a given pixel in the given image
     *
     * @param image image
     * @param x     x coordinate
     * @param y     y coordinate
     * @param c     channel
     */
    void apply(ImageWrapper<?> image, int x, int y, int c);
}
