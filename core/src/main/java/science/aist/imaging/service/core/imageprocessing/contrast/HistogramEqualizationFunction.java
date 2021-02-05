/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.contrast;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.core.imageprocessing.helper.HistogramFunction;
import science.aist.imaging.service.core.imageprocessing.transformation.ValueTransformationFunction;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.NonNull;
import lombok.Setter;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>Implementation of a histogram equalization</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class HistogramEqualizationFunction<T, R> implements ImageFunction<T, R> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE);
    private BiFunction<ImageWrapper<T>, int[], ImageWrapper<R>> transformationFunction;
    private Function<ImageWrapper<T>, int[]> histogramFunction;

    private int maxVal = 256;

    public HistogramEqualizationFunction(@NonNull ImageFactory<R> provider) {
        this.transformationFunction = new ValueTransformationFunction<>(provider);
        this.histogramFunction = new HistogramFunction<>();
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        typeChecker.accept(imageWrapper);

        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        int numOfPixels = width * height;

        int[] histogram = histogramFunction.apply(imageWrapper);

        int[] transferFunction = new int[maxVal];

        short amin = 0;
        while (histogram[amin] == 0) amin++;

        for (short i = 0; i < maxVal; i++) {
            int mult = histogram.length - amin + 1;

            double cdf = 0;
            for (int k = 0; k <= i && k < histogram.length; k++) {
                cdf += (double) histogram[k] / (double) numOfPixels;
            }
            // floor by rounding to int.
            transferFunction[i] = (short) (((int) (cdf * mult) + 1) + amin);

            if (transferFunction[i] > 255) transferFunction[i] = 255;
            if (transferFunction[i] < 0) transferFunction[i] = 0;
        }

        return transformationFunction.apply(imageWrapper, transferFunction);
    }
}
