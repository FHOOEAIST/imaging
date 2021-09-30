/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformers.color;

import science.aist.imaging.api.domain.color.HSVColor;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.jack.general.transformer.Transformer;

import java.awt.*;

/**
 * <p>Transformer between HSV and RGB</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class HSVTransformer implements Transformer<RGBColor, HSVColor> {

    private static final double EPSILON = 0.0000000001;

    /**
     * Method which compares to doubles
     *
     * @param a First double to compare with second double
     * @param b Second double which should be compared
     * @return True if a == b; else false
     */
    protected boolean equalDoubles(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     * @param hsvColor to be transformed
     * @return From corresponding to To
     */
    @Override
    public RGBColor transformTo(HSVColor hsvColor) {
        return new RGBColor(Color.HSBtoRGB((float) hsvColor.getHue(), (float) hsvColor.getSaturation(), (float) hsvColor.getValue()));
    }

    /**
     * @param rgbColor to be transformed
     * @return To corresponding to From
     */
    @Override
    public HSVColor transformFrom(RGBColor rgbColor) {
        float[] values = new float[3];
        Color.RGBtoHSB((int) rgbColor.getRed(), (int) rgbColor.getGreen(), (int) rgbColor.getBlue(), values);
        return new HSVColor(values[0], values[1], values[2]);
    }
}
