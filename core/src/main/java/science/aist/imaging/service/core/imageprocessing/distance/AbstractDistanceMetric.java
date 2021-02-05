/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.distance;

/**
 * <p>Abstract implementation of a distance metric</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractDistanceMetric {

    /**
     * Creates a distance metric mask with given masksize
     *
     * @param masksize masksize of the distance metric must be odd and &gt;= 3 (because a distance metric of 1 is always 0)
     * @return distance metric
     */
    public double[][] create(int masksize) {
        if (masksize <= 0) {
            throw new IllegalArgumentException("Masksize must be greater than 2");
        }

        if (masksize % 2 != 1) {
            throw new IllegalArgumentException("Masksize must be odd");
        }

        double[][] mask = new double[masksize][masksize];
        int center = Math.floorDiv(masksize, 2);

        for (int x = -center; x <= center; x++) {
            for (int y = -center; y <= center; y++) {
                mask[center + x][center + y] = calculateMaskValue(x, y);
            }
        }
        return mask;
    }

    /**
     * Method which calculates the mask value at the given position
     *
     * @param x x position
     * @param y y position
     * @return mask value for the given position
     */
    protected abstract double calculateMaskValue(int x, int y);
}
