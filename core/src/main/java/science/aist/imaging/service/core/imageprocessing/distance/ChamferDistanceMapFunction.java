/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.distance;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.NonNull;

/**
 * <p>Implementation of the chamfer distance map algorithm</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class ChamferDistanceMapFunction<T> extends AbstractDistanceMapFunction<T> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.BINARY);

    @NonNull
    private final ImageFactory<T> provider;

    public ChamferDistanceMapFunction(double contourColour, AbstractDistanceMetric distanceMetric, @NonNull ImageFactory<T> provider) {
        super(contourColour, distanceMetric);
        this.provider = provider;
    }

    /**
     * Calculates the Chamfer LO distance metric based on the given base distance metric
     *
     * @param distanceMetric base distance metric
     * @return the chamfer LO distance metric
     */
    private static double[][] getLO(double[][] distanceMetric) {
        double[][] mask = new double[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == 0 || (x == 1 && y == 0)) {
                    mask[x][y] = Double.MAX_VALUE;
                } else {
                    mask[x][y] = distanceMetric[x][y];
                }
            }
        }
        return mask;
    }

    /**
     * Calculates the Chamfer RU distance metric based on the given base distance metric
     *
     * @param distanceMetric base distance metric
     * @return the chamfer RU distance metric
     */
    private static double[][] getRU(double[][] distanceMetric) {
        double[][] mask = new double[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == 2 || (x == 1 && y == 2)) {
                    mask[x][y] = Double.MAX_VALUE;
                } else {
                    mask[x][y] = distanceMetric[x][y];
                }
            }
        }
        return mask;
    }

    private void applyChamfer(int x, int y, int width, int height, ImageWrapper<T> result, double[][] distanceMask, int masksize) {
        int center = Math.floorDiv(masksize, 2);
        double min = result.getValue(x, y, 0);
        if (min > 0) {
            for (int i = -center; i <= center; i++) {
                for (int j = -center; j <= center; j++) {
                    if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height) {
                        double imgValue = result.getValue(x + i, y + j, 0);
                        double distance = distanceMask[center + i][center + j];

                        double temp = imgValue + distance;
                        if (temp < min) {
                            min = temp;
                        }
                    }

                }
            }
            result.setValue(x, y, 0, min);
        }
    }

    @Override
    public ImageWrapper<T> apply(ImageWrapper<?> imageWrapper) {
        typeChecker.accept(imageWrapper);

        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();

        int masksize = 3; // ru and lo implementations only suitable with masksize 3
        double[][] ru = getRU(distanceMetric.create(masksize));
        double[][] lo = getLO(distanceMetric.create(masksize));


        ImageWrapper<T> result = provider.getImage(height, width, ChannelType.GREYSCALE);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int val = (int) imageWrapper.getValue(x, y, 0);
                if (val == contourColour) {
                    result.setValue(x, y, 0, 0);
                } else {
                    result.setValue(x, y, 0, Double.MAX_VALUE);
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                applyChamfer(x, y, width, height, result, ru, masksize);
            }
        }

        for (int x = width - 1; x >= 0; x--) {
            for (int y = height - 1; y >= 0; y--) {
                applyChamfer(x, y, width, height, result, lo, masksize);
            }
        }

        return result;
    }
}
