/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.fitnessfunction;

import lombok.CustomLog;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;


/**
 * <p>Sum of Squared errors implementation of a fitness function</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
public class SSEFitnessFunction extends AbstractFitnessFunction {

    public SSEFitnessFunction() {
        this.isLowerErrorValueBetter = true;
    }

    @Override
    protected double applyFitness(ImageWrapper<?> imageWrapper, ImageWrapper<?> imageWrapper2, int startX, int startY, int endX, int endY, int channels) {
        if (imageWrapper.getChannelType() != imageWrapper2.getChannelType()) {
            log.info("Images are different in type: {} <> {}", imageWrapper.getChannelType().name(), imageWrapper2.getChannelType().name());
            throw new IllegalArgumentException("Images must have same channeltype");
        }

        double totalError = 0.0;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                for (int c = 0; c < channels; c++) {
                    double diff = imageWrapper2.getValue(x, y, c) - imageWrapper.getValue(x, y, c);
                    totalError += diff * diff;
                }
            }
        }
        return totalError;
    }

    @Override
    public double getBestPossibleError() {
        return 0.0;
    }
}
