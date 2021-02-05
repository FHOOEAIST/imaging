/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion;

/**
 * <p>Interface to convert a color pixel into a greyscale one</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public interface ColorToGreyScaleConverter {
    /**
     * Converts a color pixel into a greyscale pixel
     *
     * @param r the red value in range [0,255]
     * @param g the green value in range [0, 255]
     * @param b the blue value in range [0, 255]
     * @return the resulting greyscale value in range [0, 255]
     */
    double toGreyscale(double r, double g, double b);
}
