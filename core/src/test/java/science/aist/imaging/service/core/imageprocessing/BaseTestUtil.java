/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import lombok.experimental.UtilityClass;

/**
 * <p>Base test class for some basic methods</p>
 *
 * @author Christoph Praschl
 */
@UtilityClass
public class BaseTestUtil {
    public ImageWrapper<double[][][]> getGreyScaleSample(int width, int height, double[] values) {
        return getGreyScaleSample(width, height, values, true);
    }

    public ImageWrapper<double[][][]> getGreyScaleSample(int width, int height, double[] values, boolean isGreyScaleOrBinary) {
        if (values.length != width * height) {
            throw new IllegalArgumentException("Non matching values for width and height");
        }

        ImageWrapper<double[][][]> imageWrapper = Image8ByteFactory.getInstance().getImage(height, width, isGreyScaleOrBinary ? ChannelType.GREYSCALE : ChannelType.BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                imageWrapper.setValue(x, y, 0, values[y * width + x]);
            }
        }


        return imageWrapper;
    }
}
