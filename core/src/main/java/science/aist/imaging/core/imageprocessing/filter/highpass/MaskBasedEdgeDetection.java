/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.filter.highpass;

import lombok.NonNull;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.core.imageprocessing.filter.ConvolveFunction;
import science.aist.jack.math.MathUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>Generic implementation of edge detection for mask based edge detection operators</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class MaskBasedEdgeDetection<T, R> implements ImageFunction<T, R> {
    private final Object synchronizer = new Object();
    private List<double[][]> masks;
    @NonNull
    private ImageFactory<R> provider;
    private BiFunction<ImageWrapper<T>, double[][], ImageWrapper<R>> convolveFunction;

    public MaskBasedEdgeDetection(ImageFactory<R> provider) {
        this.provider = provider;
        ConvolveFunction<T, R> convolve8Byte = new ConvolveFunction<>(provider);
        convolve8Byte.setNormalize(false);
        convolveFunction = convolve8Byte;
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        List<ImageWrapper<R>> subResults = masks.stream()
                .map(m -> convolveFunction.apply(imageWrapper, m))
                .collect(Collectors.toList());

        final int height = imageWrapper.getHeight();
        final int width = imageWrapper.getWidth();
        ImageWrapper<R> resultWrapper = provider.getImage(height, width, ChannelType.GREYSCALE);

        final AtomicLong maxGradient = new AtomicLong(0);
        IntStream.range(0, height).parallel().forEach(y -> {
            for (int x = 0; x < width; x++) {
                final int innerY = y;
                final int innerX = x;
                resultWrapper.setValue(x, y, 0, subResults
                        .stream()
                        .mapToDouble(val -> val.getValue(innerX, innerY, 0))
                        .map(Math::abs)
                        .sum());

                synchronized (synchronizer) {
                    if (resultWrapper.getValue(x, y, 0) > Double.longBitsToDouble(maxGradient.get()))
                        maxGradient.set(Double.doubleToLongBits(resultWrapper.getValue(x, y, 0)));
                }
            }
        });

        if (MathUtils.equals(Double.longBitsToDouble(maxGradient.get()), 0.0))
            throw new IllegalStateException("Max gradient is zero!");

        double normFactor = 255.0 / Double.longBitsToDouble(maxGradient.get());

        IntStream.range(0, height).parallel().forEach(y -> {
            for (int x = 0; x < width; x++) {
                double value = resultWrapper.getValue(x, y, 0);
                resultWrapper.setValue(x, y, 0, value * normFactor);
            }
        });

        return resultWrapper;
    }
}
