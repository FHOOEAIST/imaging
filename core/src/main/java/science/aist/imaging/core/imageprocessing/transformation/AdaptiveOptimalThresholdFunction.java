/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.jack.math.MathUtils;

import java.util.Arrays;

/**
 * <p>Adaptive Optimal ThresholdFunction implementation</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class AdaptiveOptimalThresholdFunction<T, R> implements ImageFunction<T, R> {

    @NonNull
    private ImageFactory<R> provider;

    private short bgVal = 0;
    private short fgVal = 255;

    private int clusterSize = 30;
    private double overlappingFactor = 0.25;
    private int eps = 2;

    private double meanOf(ImageWrapper<R> segment, ImageWrapper<T> in, int width, int height, int startX, int startY, double comp) {
        int sum = 0;
        int n = 0;
        for (int x = startX; x < width; x++) {
            for (int y = startY; y < height; y++) {
                if (MathUtils.equals(segment.getValue(x, y, 0), comp)) {
                    sum += in.getValue(x, y, 0);
                    n++;
                }
            }
        }
        if (n == 0){
            return 0;
        }
        return (double) sum / (double) n;
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        ImageWrapper<R> resultWrapper = provider.getImage(height, width);

        int clusterInX = (int) (width / (double) clusterSize);
        if (width % clusterSize != 0) clusterInX++;

        int clusterInY = (int) (height / (double) clusterSize);
        if (height % clusterSize != 0) clusterInY++;

        short[][] thresholds = new short[clusterInX][clusterInY];
        for (short[] threshold : thresholds) Arrays.fill(threshold, (short) 128);

        int dist = Integer.MAX_VALUE;

        while (dist > eps) {
            dist = 0;
            for (int cX = 0; cX < clusterInX; cX++) {
                for (int cY = 0; cY < clusterInY; cY++) {
                    // segmentation
                    int startX = cX * clusterSize;
                    if (startX > width) startX = width;
                    int startY = cY * clusterSize;
                    if (startY > height) startY = height;
                    int clusterWidth = startX + clusterSize;
                    if (clusterWidth > width) clusterWidth = width;
                    int clusterHeight = startY + clusterSize;
                    if (clusterHeight > height) clusterHeight = height;

                    segment(resultWrapper, imageWrapper, clusterWidth, clusterHeight, startX, startY, thresholds[cX][cY]);

                    // mean calculation
                    int factorX = 2;
                    int factorY = 2;
                    int meanStartX = (int) (cX * clusterSize - clusterSize * (1 - overlappingFactor) + 0.5);
                    if (meanStartX < 0) {
                        meanStartX = 0;
                        factorX = 1;
                    }
                    int meanStartY = (int) (cY * clusterSize - clusterSize * (1 - overlappingFactor) + 0.5);
                    if (meanStartY < 0) {
                        meanStartY = 0;
                        factorY = 1;
                    }

                    int meanWidth = (int) (startX + clusterSize * (1 + factorX * overlappingFactor) + 0.5);
                    if (meanWidth > width) meanWidth = width;
                    int meanHeight = (int) (startY + clusterSize * (1 + factorY * overlappingFactor) + 0.5);
                    if (meanHeight > height) meanHeight = height;

                    double uFG = meanOf(resultWrapper, imageWrapper, meanWidth, meanHeight, meanStartX, meanStartY, fgVal);
                    double uBG = meanOf(resultWrapper, imageWrapper, meanWidth, meanHeight, meanStartX, meanStartY, bgVal);
                    short thresholdNew = (short) ((uBG + uFG) / 2 + 0.5);
                    dist += Math.abs(thresholds[cX][cY] - thresholdNew);
                    thresholds[cX][cY] = thresholdNew;
                }
            }
        }

        return resultWrapper;
    }

    private void segment(ImageWrapper<R> segment, ImageWrapper<T> in, int width, int height, int startX, int startY, short threshold) {
        segment.applyFunction((image, x, y, c) -> image.setValue(x, y, 0, in.getValue(x, y, 0) > threshold ? fgVal : bgVal), startX, startY, width, height);
    }
}