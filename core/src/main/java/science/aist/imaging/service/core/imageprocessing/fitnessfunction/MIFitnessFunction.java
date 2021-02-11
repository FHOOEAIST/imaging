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
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;
import science.aist.imaging.api.typecheck.TypeChecker;

/**
 * <p>Mutual information implementation of a fitness function for a greyscale image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
public class MIFitnessFunction extends AbstractFitnessFunction {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE, ChannelType.BINARY);

    public MIFitnessFunction() {
        this.isLowerErrorValueBetter = false;
    }

    @Override
    protected double applyFitness(ImageWrapper<?> imageWrapper, ImageWrapper<?> imageWrapper2, int startX, int startY, int endX, int endY, int channels) {
        typeChecker.accept(imageWrapper);
        if (imageWrapper.getChannelType() != imageWrapper2.getChannelType()) {
            log.info("Images are different in type: {} <> {}", imageWrapper.getChannelType().name(), imageWrapper2.getChannelType().name());
            throw new IllegalArgumentException("Images must have same channeltype");
        }

        double amountPixels = (double) (endX - startX) * (endY - startY);

        int[] cntRef = new int[256];
        int[] cntTest = new int[256];
        int[][] cntRT = new int[256][256];

        // Count every color value from each image
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int refVal = (int) imageWrapper.getValue(x, y, 0);
                int testVal = (int) imageWrapper2.getValue(x, y, 0);

                cntRef[refVal]++;
                cntTest[testVal]++;
                cntRT[refVal][testVal]++;
            }
        }

        double hRef = 0;
        double hTest = 0;
        double hRefTest = 0;

        // Calculate the probability (with amount of all pixels)
        // But only if there are such pixels -> log(0) = infinity
        for (int x = 0; x < 256; x++) {
            if (cntRef[x] != 0) {
                hRef += (cntRef[x] / amountPixels) * Math.log(cntRef[x] / amountPixels);
            }

            if (cntTest[x] != 0) {
                hTest += (cntTest[x] / amountPixels) * Math.log(cntTest[x] / amountPixels);
            }

            for (int y = 0; y < 256; y++) {
                if (cntRT[x][y] != 0) {
                    hRefTest += (cntRT[x][y] / amountPixels) * Math.log(cntRT[x][y] / amountPixels);
                }
            }
        }

        hRef *= -1;
        hTest *= -1;
        hRefTest *= -1;

        return (hTest + hRef - hRefTest);
    }

    @Override
    public double getBestPossibleError() {
        return Double.MAX_VALUE;
    }
}
