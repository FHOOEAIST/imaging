/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.fitnessfunction;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.imaging.service.core.imageprocessing.distance.AbstractDistanceMapFunction;
import science.aist.jack.math.MathUtils;

/**
 * <p>Distance map based compare value fitness function</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class DistanceMapFitnessFunction extends AbstractFitnessFunction {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE, ChannelType.BINARY);
    private static final TypeChecker typeChecker2 = new TypeChecker(ChannelType.BINARY);

    private AbstractDistanceMapFunction<?> distanceMapFunction;

    public DistanceMapFitnessFunction(AbstractDistanceMapFunction<?> distanceMapFunction) {
        this.distanceMapFunction = distanceMapFunction;
        this.isLowerErrorValueBetter = true;
    }

    @Override
    protected double applyFitness(ImageWrapper<?> imageWrapper, ImageWrapper<?> imageWrapper2, int startX, int startY, int endX, int endY, int channels) {
        typeChecker.accept(imageWrapper);
        typeChecker2.accept(imageWrapper2);

        ImageWrapper<?> apply = distanceMapFunction.apply(imageWrapper2);

        double totalError = 0.0;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                for (int c = 0; c < channels; c++) {
                    if (MathUtils.equals(apply.getValue(x, y, 0), 0.0)) {
                        double diff = imageWrapper2.getValue(x, y, c) - imageWrapper.getValue(x, y, c);
                        totalError += diff * diff;
                    }
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
