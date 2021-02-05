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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Abstract Implementation of a pooling filter</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractPoolingFunction<T, R> implements Function<ImageWrapper<T>, ImageWrapper<R>> {
    @NonNull
    protected ImageFactory<R> provider;

    private int yStride = 1;
    private int xStride = 1;
    private int neighborhoodWidth = 3;
    private int neighborhoodHeight = 3;

    /**
     * sets value of field {@link AbstractPoolingFunction#yStride}
     *
     * @param yStride value of field yStride
     * @see AbstractPoolingFunction#yStride
     */
    public void setYStride(int yStride) {
        if (yStride < 0) {
            throw new IllegalArgumentException("yStride must be > 0");
        }
        this.yStride = yStride;
    }

    /**
     * sets value of field {@link AbstractPoolingFunction#xStride}
     *
     * @param xStride value of field xStride
     * @see AbstractPoolingFunction#xStride
     */
    public void setXStride(int xStride) {
        if (xStride < 0) {
            throw new IllegalArgumentException("xStride must be > 0");
        }
        this.xStride = xStride;
    }

    /**
     * sets value of field {@link AbstractPoolingFunction#neighborhoodWidth}
     *
     * @param neighborhoodWidth value of field neighborhoodWidth
     * @see AbstractPoolingFunction#neighborhoodWidth
     */
    public void setNeighborhoodWidth(int neighborhoodWidth) {
        if (neighborhoodWidth < 0 || neighborhoodWidth % 2 == 0) {
            throw new IllegalArgumentException("neighborhoodWidth must be > 0 and odd");
        }
        this.neighborhoodWidth = neighborhoodWidth;
    }

    /**
     * sets value of field {@link AbstractPoolingFunction#neighborhoodHeight}
     *
     * @param neighborhoodHeight value of field neighborhoodHeight
     * @see AbstractPoolingFunction#neighborhoodHeight
     */
    public void setNeighborhoodHeight(int neighborhoodHeight) {
        if (neighborhoodHeight < 0 || neighborhoodHeight % 2 == 0) {
            throw new IllegalArgumentException("neighborhoodHeight must be > 0 and odd");
        }
        this.neighborhoodHeight = neighborhoodHeight;
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> wrapper) {
        ImageWrapper<R> provide = provider.getImage(wrapper.getHeight(), wrapper.getWidth(), wrapper.getChannelType());

        int xRadius = (neighborhoodWidth - 1) / 2;
        int yRadius = (neighborhoodHeight - 1) / 2;
        int width = provide.getWidth();
        int height = provide.getHeight();
        int channels = provide.getChannels();

        provide.applyColumnFunction((image, y) -> {
            for (int x = 0; x < width; x += xStride) {
                for (int c = 0; c < channels; c++) {
                    List<Double> values = new ArrayList<>();
                    for (int yOffset = -yRadius; yOffset <= yRadius; yOffset++) {
                        for (int xOffset = -xRadius; xOffset <= xRadius; xOffset++) {
                            int nbX = x + xOffset;
                            int nbY = y + yOffset;
                            if ((nbX >= 0) && (nbX < width) && (nbY >= 0) && (nbY < height)) {
                                values.add(wrapper.getValue(nbX, nbY, c));
                            } //range check
                        } // xOffset
                    } // yOffset
                    double pooling = pooling(values);
                    image.setValue(x, y, c, pooling);
                }
            }
        }, 0, wrapper.getHeight(), yStride, provide.supportsParallelAccess());
        return provide;
    }

    /**
     * Inner Function that is applied for all values in neighborhood
     *
     * @param values used for pooling
     * @return pooling result
     */
    protected abstract double pooling(List<Double> values);
}
