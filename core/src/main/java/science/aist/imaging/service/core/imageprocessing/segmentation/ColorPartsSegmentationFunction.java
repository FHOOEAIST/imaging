/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Arrays;

/**
 * <p>This function segments color parts in a given image. It returns a greyscale value as a result.
 * Or if maxDiff is not set then with: value &gt;= minDiff --&gt; foreground &amp;&amp; value &lt; minDiff --&gt; background
 * With greyscale values: minDiff = background, maxDiff = foreground, between minDiff and maxDiff something between background and foreground</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class ColorPartsSegmentationFunction<T, R> implements ImageFunction<T, R> {

    @NonNull
    private ImageFactory<R> provider;

    /**
     * Foreground value
     */
    private double foreground;

    /**
     * Background value
     */
    private double background;

    /**
     * Min diff value
     */
    private double minDiff;

    /**
     * max diff value
     */
    private Double maxDiff = null;

    private static double findMin(double[] array) {
        return Arrays.stream(array)
                .min()
                .orElseThrow(IllegalStateException::new);
    }

    private static double findMax(double[] array) {
        return Arrays.stream(array)
                .max()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();
        ImageWrapper<R> result = provider.getImage(height, width);


        result.applyFunction((image, x, y, c) -> {
            double[] channels = imageWrapper.getValues(x, y);
            double minChannelVal = findMin(channels);
            double maxChannelVal = findMax(channels);

            double currDiff = maxChannelVal - minChannelVal;

            if ((maxDiff == null && currDiff >= minDiff) || (maxDiff != null && currDiff > maxDiff)) {
                image.setValue(x, y, 0, foreground);
            } else if (currDiff < minDiff) {
                image.setValue(x, y, 0, background);
            } else {
                image.setValue(x, y, 0, foreground * ((currDiff - minDiff) / maxDiff) + 0.5);
            }
        });

        return result;
    }
}
