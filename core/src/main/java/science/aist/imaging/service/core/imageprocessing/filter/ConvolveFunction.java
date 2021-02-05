/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.BiFunction;

/**
 * <p>Applies a given kernel to the given image wrapper. kernel y-coordinate is the first array, and x the second. access via: kernel[y][x]</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class ConvolveFunction<T, R> implements BiFunction<ImageWrapper<T>, double[][], ImageWrapper<R>> {
    private boolean normalize = true;

    private int numberOfIterations = 1;

    @NonNull
    private ImageFactory<R> provider;

    private ImageWrapper<R> innerApply(ImageWrapper<?> imageWrapper, double[][] kernel) {
        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();
        int channels = imageWrapper.getChannels();
        ImageWrapper<R> resultWrapper = provider.getImage(height, width, imageWrapper.getChannelType());

        resultWrapper.applyColumnFunction((image, y) -> {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channels; c++) {
                    double sum = 0.0;
                    double maskSum = 0.0;
                    int yRadius = (kernel.length - 1) / 2;
                    for (int yOffset = -yRadius; yOffset <= yRadius; yOffset++) {
                        int xRadius = (kernel[yOffset + yRadius].length - 1) / 2;
                        for (int xOffset = -xRadius; xOffset <= xRadius; xOffset++) {
                            int nbX = x + xOffset;
                            int nbY = y + yOffset;
                            if ((nbX >= 0) && (nbX < width) && (nbY >= 0) && (nbY < height)) {
                                sum += imageWrapper.getValue(nbX, nbY, 0) * kernel[yOffset + yRadius][xOffset + xRadius];
                                maskSum += kernel[yOffset + yRadius][xOffset + xRadius];
                            } //range check
                        } // xOffset
                    } // yOffset
                    if (normalize) {
                        if (maskSum == 0) {
                            throw new IllegalStateException("KernelSum is 0 but normalizing is active");
                        }
                        sum *= 1.0 / maskSum;
                    } // normalize

                    image.setValue(x, y, c, sum);
                }
            } // x
        }, 0, imageWrapper.getHeight(), 1, imageWrapper.supportsParallelAccess());

        return resultWrapper;
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper, double[][] kernel) {
        ImageWrapper<R> returnImg = innerApply(imageWrapper, kernel);
        for (int i = 1; i < numberOfIterations; i++) {
            returnImg = innerApply(returnImg, kernel);
        }
        return returnImg;
    }
}
