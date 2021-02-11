/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation.morph;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.NeighborType;
import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.jack.math.MathUtils;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Generic implementation of a morph function that allows to dilate/erode any foreground colors (not limited to binary!)
 * Use the isBackgroundFunction to determine if a pixel is a foreground or a background pixel.
 *
 * @param <T> Type of input image
 * @param <P> Type of output image
 * @author Andreas Pointner
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public class GenericMorphFunction<T, P> implements ImageFunction<T, P> {
    @NonNull
    private final ImageFactory<P> tProvider;

    @Setter
    private Predicate<Color> isBackgroundFunction = c -> Arrays.stream(c.getChannels()).allMatch(d -> MathUtils.equals(d, 255.0));
    /**
     * Neighborhood mask, where every value != 0 is used for morphing
     */
    private ImageWrapper<short[][][]> neighborMask = NeighborType.N8.getImageMask();

    /**
     * Constructor that creates a GenericMorphFunction object with a neighbor mask of the given size
     *
     * @param tProvider    used to create the result
     * @param neighborMask used to create a neighbor mask for the morphological change
     */
    public GenericMorphFunction(@NonNull ImageFactory<P> tProvider, int neighborMask) {
        this.tProvider = tProvider;
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(neighborMask, neighborMask, ChannelType.BINARY);
        image.applyFunction((i, x, y, c) -> i.setValue(x, y, c, 255));
        setNeighborMask(image);
    }

    /**
     * sets value of field {@link GenericMorphFunction#neighborMask}
     *
     * @param neighborMask value of field neighborType
     * @see GenericMorphFunction#neighborMask
     */
    public void setNeighborMask(ImageWrapper<short[][][]> neighborMask) {
        if (neighborMask.getChannelType() != ChannelType.BINARY) {
            throw new IllegalArgumentException("Not a binary mask");
        }

        if (neighborMask.getWidth() % 2 != 1 || neighborMask.getHeight() % 2 != 1) {
            throw new IllegalArgumentException("Mask must have a odd size");
        }

        this.neighborMask = neighborMask;
    }

    @Override
    public ImageWrapper<P> apply(ImageWrapper<T> i) {
        int width = i.getWidth();
        int height = i.getHeight();

        ImageWrapper<P> res = tProvider.getImage(height, width, i.getChannelType());
        i.copyTo(res);

        int nHeight = (neighborMask.getHeight() - 1) / 2;
        int nWidth = (neighborMask.getWidth() - 1) / 2;
        short[][][] mask = neighborMask.getImage();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double[] values = i.getValues(x, y);
                // check if current values are foreground pixels
                if (Boolean.FALSE.equals(isBackgroundFunction.test(new Color(values)))) {
                    // check all neighbors of the current pixel if they are background pixels
                    // iterate the neighbors
                    for (int xOffset = -nWidth; xOffset <= nWidth; xOffset++) {
                        for (int yOffset = -nHeight; yOffset <= nHeight; yOffset++) {
                            // check if the current neighbor should be looked up according to the selected mask
                            // also check if the current position is inside of the image
                            if (mask[yOffset + nHeight][xOffset + nWidth][0] != 0 &&
                                    x + xOffset >= 0 && x + xOffset < width &&
                                    y + yOffset >= 0 && y + yOffset < height) {
                                double[] neighbor = i.getValues(x + xOffset, y + yOffset);
                                // if the neighbor pixel is currently a background pixel set it to a foreground pixel for dilation
                                if (Boolean.TRUE.equals(isBackgroundFunction.test(new Color(neighbor)))) {
                                    res.setValues(x + xOffset, y + yOffset, values);
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
}

