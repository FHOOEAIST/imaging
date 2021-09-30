/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformers.color;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.color.YUVColor;
import science.aist.jack.general.transformer.Transformer;

/**
 * Transforms RGB colors into YUV colors and back.
 * <p>
 * Formula from: https://www.fourcc.org/fccyvrgb.php
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public class YUVTransformer implements Transformer<RGBColor, YUVColor> {

    @Override
    public RGBColor transformTo(YUVColor yuvColor) {

        /*
        R = Y + 1.140V
        G = Y - 0.395U - 0.581V
        B = Y + 2.032U
        */

        double r = yuvColor.getYLuma() + 1.140 * yuvColor.getVChroma();
        double g = yuvColor.getYLuma() - 0.395 * yuvColor.getUChroma() - 0.581 * yuvColor.getVChroma();
        double b = yuvColor.getYLuma() + 2.032 * yuvColor.getUChroma();

        //rgb is range from [0,255], but current values are from [0,1]
        r = Math.round(r * 255);
        g = Math.round(g * 255);
        b = Math.round(b * 255);

        if (r > 255) {
            r = 255;
        } else if (r < 0) {
            r = 0;
        }

        if (g > 255) {
            g = 255;
        } else if (g < 0) {
            g = 0;
        }

        if (b > 255) {
            b = 255;
        } else if (b < 0) {
            b = 0;
        }

        return new RGBColor(r, g, b);
    }

    @Override
    public YUVColor transformFrom(RGBColor rgbColor) {

        /*
         Y =  0.299R + 0.587G + 0.114B
         U = -0.147R - 0.289G + 0.436B
         V =  0.615R - 0.515G - 0.100B
         */

        //rgb is range from [0,255], but we require them for the range [0,1]
        double r = rgbColor.getRed() / 255;
        double g = rgbColor.getGreen() / 255;
        double b = rgbColor.getBlue() / 255;

        double y = 0.299 * r + 0.587 * g + 0.114 * b;
        double u = -0.147 * r - 0.289 * g + 0.436 * b;
        double v = 0.615 * r - 0.515 * g - 0.100 * b;

        return new YUVColor(y, u, v);
    }
}
