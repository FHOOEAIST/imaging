/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.conversion.greyscale;

import science.aist.imaging.core.imageprocessing.conversion.ColorToGreyScaleConverter;

/**
 * <p>Calculates Greycsale using Luminosity</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class GreyscaleLuminosityConverter implements ColorToGreyScaleConverter {
    @Override
    public double toGreyscale(double r, double g, double b) {
        double v = (r * .21 + g * .72 + b * .07 + 0.5);
        return v > 255 ? 255 : v;
    }
}
