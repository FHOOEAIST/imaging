/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.houghspace;

import lombok.Getter;
import lombok.NonNull;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class is used as a result for the houghspace</p>
 *
 * @author Andreas Pointner
 * @author Gerald Zwettler
 * @since 1.0
 */
@Getter
public class HoughSpaceLines {
    /**
     * internal representation of the hough space
     */
    private final Map<JavaPoint2D, double[]> houghSpace;
    /**
     * width of the original image (as well as of the hough Space, but this will maybe not hold every pixel value
     */
    private final int width;
    /**
     * height of the original image (as well as of the hough Space, but this will maybe not hold every pixel value
     */
    private final int height;
    /**
     * end getRotation of the lines
     */
    private final double maxRotation;
    /**
     * start getRotation of the lines
     */
    private final double minRotation;
    /**
     * getRotation steps in degrees
     */
    private final double rotationStep;
    /**
     * the x-coordinate of the best value
     */
    private int bestX;
    /**
     * the y-coordinate of the best value
     */
    private int bestY;
    /**
     * best getRotation index.
     */
    private int bestRidx;
    /**
     *
     */
    private double bestWeight = 0.0;

    /**
     * @param width         width of the source image
     * @param height        height of the source image
     * @param maxRotation   end getRotation of the lines
     * @param minRotation   start getRotation of the lines
     * @param rotationSteps getRotation steps in degrees
     */
    public HoughSpaceLines(int width, int height, double maxRotation, double minRotation, double rotationSteps) {
        // Initialize with default values
        this.width = width;
        this.height = height;
        this.bestX = -1;
        this.bestY = -1;
        this.bestWeight = 0.0;
        this.minRotation = minRotation;
        this.maxRotation = maxRotation;
        this.rotationStep = rotationSteps;

        // the first value specifies the initial capacity of the hash map, we already know, how many elements we are going to
        // put into the hash map, which is width + height. (when using inner cross only)
        // "The load factor is a measure of how full the hash table is allowed to get before its capacity is automatically increased."
        // We don't want a resizing of the hash map to be more efficient, therefore we set the second value to 1.
        houghSpace = new HashMap<>(width + height + 1, 1);
    }

    /**
     * set a value into the hough space
     *
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param rotationIdx getRotation index
     * @param value       value
     */
    public void setHoughSpaceValue(int x, int y, int rotationIdx, double value) {
        JavaPoint2D jp = new JavaPoint2D(x, y);
        double[] arr;
        if (!houghSpace.containsKey(jp)) {
            arr = new double[getNumOfRotations()];
            houghSpace.put(jp, arr);
        } else {
            arr = houghSpace.get(jp);
        }
        arr[rotationIdx] = value;
        if (value > getBestWeight()) {
            bestWeight = value;
            bestX = x;
            bestY = y;
            bestRidx = rotationIdx;
        }
    }

    /**
     * get a value from the hough space
     *
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param rotationIdx getRotation index
     * @return return value
     */
    public double getHoughSpaceValue(int x, int y, int rotationIdx) {
        if (!houghSpace.containsKey(new JavaPoint2D(x, y)))
            return 0.0;
        return houghSpace.get(new JavaPoint2D(x, y))[rotationIdx];
    }

    /**
     * get the best value in a specific sector
     *
     * @param xMin start search x-coordinate
     * @param xMax end search x-coordinate
     * @param yMin start search y-coordinate
     * @param yMax end search y-coordinate
     * @return returns an array with [0] = x; [1] = y; [2] = getRotation
     */
    public double[] getBestXYradInSector(int xMin, int xMax, int yMin, int yMax) {
        double[] returnArr = new double[3];
        double currBest = Double.MIN_VALUE;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if (houghSpace.containsKey(new JavaPoint2D(x, y))) {
                    for (int rad = 0; rad < getNumOfRotations(); rad++) {
                        double currFitness = getHoughSpaceValue(x, y, rad);
                        if (currFitness > currBest) {
                            currBest = currFitness;
                            returnArr[0] = x;
                            returnArr[1] = y;
                            returnArr[2] = rad * getRotationStep() + getMinRotation();
                        }
                    }
                }
            }
        }
        return returnArr;
    }

    /**
     * get the x best value in a specific sector
     *
     * @param best which value should be found. e.g. 1 means best value. 2 means second best value. and so on
     * @param xMin start search x-coordinate
     * @param xMax end search x-coordinate
     * @param yMin start search y-coordinate
     * @param yMax end search y-coordinate
     * @return returns an array with [0] = x; [1] = y; [2] = getRotation
     */
    public double[] getXBestXYradInSector(int best, int xMin, int xMax, int yMin, int yMax) {
        if (best < 1) throw new IllegalArgumentException("Best cannot be smaller than 1");
        double[][] bestRes = new double[best][4];

        for (int i = 0; i < best; i++) {
            for (int j = 0; j < 4; j++) {
                bestRes[i][j] = Double.MIN_VALUE;
            }
        }

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if (houghSpace.containsKey(new JavaPoint2D(x, y))) {
                    for (int rad = 0; rad < getNumOfRotations(); rad++) {
                        double currFitness = getHoughSpaceValue(x, y, rad);
                        if (currFitness > bestRes[best - 1][0]) {
                            int idx = 0;
                            for (int i = best - 1; i >= 0; i--) {
                                if (currFitness > bestRes[i][0])
                                    idx = i;
                            }
                            for (int i = best - 1; i > idx; i--) {
                                System.arraycopy(bestRes[i - 1], 0, bestRes[i], 0, 4);
                            }
                            bestRes[idx][0] = currFitness;
                            bestRes[idx][1] = x;
                            bestRes[idx][2] = y;
                            bestRes[idx][3] = rad * getRotationStep() + getMinRotation();
                        }
                    }
                }
            }
        }

        return new double[]{bestRes[best - 1][1], bestRes[best - 1][2], bestRes[best - 1][3]};
    }

    /**
     * get the best value in a specific sector in a specific direction
     *
     * @param xMin        start search x-coordinate
     * @param xMax        end search x-coordinate
     * @param yMin        start search y-coordinate
     * @param yMax        end search y-coordinate
     * @param rotationMin getRotation in degrees where to start
     * @param rotationMax getRotation in degrees where to end
     * @return returns an array with [0] = x; [1] = y; [2] = getRotation
     */
    public double[] getBestXYradInSectorForRotation(int xMin, int xMax, int yMin, int yMax, double rotationMin, double rotationMax) {
        double[] returnArr = new double[3];
        double currBest = Double.MIN_VALUE;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if (houghSpace.containsKey(new JavaPoint2D(x, y))) {
                    for (int rad = 0; rad < getNumOfRotations(); rad++) {
                        double currFitness = getHoughSpaceValue(x, y, rad);
                        double radVal = rad * getRotationStep() + getMinRotation();
                        if (radVal > rotationMax) radVal -= 180;
                        else if (radVal < rotationMin) radVal += 180;
                        if ((radVal <= rotationMax) && (radVal >= rotationMin) &&
                                currFitness > currBest) {
                            currBest = currFitness;
                            returnArr[0] = x;
                            returnArr[1] = y;
                            returnArr[2] = rad * getRotationStep();
                        }
                    }
                }
            }
        }
        return returnArr;
    }

    /**
     * @return number of getRotation (maxRotation - minRotation) / rotationStep + 0.5
     */
    public int getNumOfRotations() {
        return (int) ((maxRotation - minRotation) / rotationStep + 0.5);
    }

    /**
     * extracts the best value for each point and adds it to the java image
     *
     * @param expoScale the value how high the exponent should be for scaling
     * @param provider the provider to create the resulting image
     * @param <T> The result type of the image.
     * @return a java image representation of the hough space
     */
    public <T> ImageWrapper<T> toImage(final int expoScale, @NonNull ImageFactory<T> provider) {
        final ImageWrapper<T> ji = provider.getImage(height, width, ChannelType.GREYSCALE);

        // convert the hough space into java image
        houghSpace.forEach((p, dA) -> {
            double max = -1;
            for (double val : dA)
                if (val > max)
                    max = val;
            // create exponential scale to find out higher values more easy
            max = Math.pow(max, expoScale);
            ji.setValue(p.getIntX(), p.getIntY(), 0, max);
        });

        double totalMax = 0;
        // find total max
        for (JavaPoint2D p : houghSpace.keySet()) {
            double val = ji.getValue(p.getIntX(), p.getIntY(), 0);
            if (val > totalMax)
                totalMax = val;
        }

        // scale colors, so that max value = 255
        if (Math.abs(totalMax) < 0.000001 || totalMax == 0) return ji;
        double scale = 255 / totalMax;
        for (JavaPoint2D p : houghSpace.keySet()) {
            double val = ji.getValue(p.getIntX(), p.getIntY(), 0) * scale;
            ji.setValue(p.getIntX(), p.getIntY(), 0, val);
        }

        return ji;
    }

    /**
     * extracts the best value for each point and adds it to the java image
     *
     * @param <T> The type of the resulting image
     * @param provider The image provider used to create the result image.
     * @return a java image representation of the hough space
     */
    public <T> ImageWrapper<T> toImage(@NonNull ImageFactory<T> provider) {
        return toImage(25, provider);
    }

}
