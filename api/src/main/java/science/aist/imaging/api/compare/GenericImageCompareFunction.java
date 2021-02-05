/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.util.ToBooleanBiFunction;
import lombok.CustomLog;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>Generic image compare function, to compare any image wrappers, and check if they are equals. Allows to set a epsilon to
 * configure when two values are seen as equal.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
@Setter
@Accessors(chain = true)
public class GenericImageCompareFunction implements ToBooleanBiFunction<ImageWrapper<?>, ImageWrapper<?>> {

    private double epsilon = 0.001;

    @Override
    public boolean applyAsBoolean(ImageWrapper<?> imageWrapper, ImageWrapper<?> imageWrapper2) {
        if (imageWrapper.getWidth() != imageWrapper2.getWidth()) {
            log.info("Images are different in width: {} <> {}", imageWrapper.getWidth(), imageWrapper2.getWidth());
            return false;
        }
        if (imageWrapper.getHeight() != imageWrapper2.getHeight()) {
            log.info("Images are different in height: {} <> {}", imageWrapper.getHeight(), imageWrapper2.getHeight());
            return false;
        }
        if (imageWrapper.getChannelType() != imageWrapper2.getChannelType()) {
            log.info("Images are different in type: {} <> {}", imageWrapper.getChannelType().name(), imageWrapper2.getChannelType().name());
            return false;
        }

        for (int y = 0; y < imageWrapper.getHeight(); y++) {
            for (int x = 0; x < imageWrapper.getWidth(); x++) {
                for (int c = 0; c < imageWrapper.getChannelType().getNumberOfChannels(); c++) {
                    if (Math.abs(imageWrapper.getValue(x, y, c) - imageWrapper2.getValue(x, y, c)) > epsilon) {
                        log.info("Images are different at: x={} y={} c={} with: img1: {} and img2: {}", x, y, c, imageWrapper.getValue(x, y, c), imageWrapper2.getValue(x, y, c));
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
