/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformers;

import science.aist.imaging.api.domain.color.RGBColor;
import org.opencv.core.Scalar;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Transformer for Scalar (in BGR style; Transparency will be ignored) and RGB Color</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVScalarRGBColorTransformer implements Transformer<Scalar, RGBColor> {
    @Override
    public Scalar transformTo(RGBColor rgbColor) {
        return new Scalar(rgbColor.getBlue(), rgbColor.getGreen(), rgbColor.getRed());
    }

    @Override
    public RGBColor transformFrom(Scalar scalar) {
        double[] values = scalar.val;
        return new RGBColor(values[2], values[1], values[0]);
    }
}
