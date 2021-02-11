/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.stream.IntStream;

/**
 * <p>Crops a given image where {@link TwoByteCropFunction#from} is the topLeft point and {@link TwoByteCropFunction#to} is the bottom right point to crop.</p>
 * <p>{@link TwoByteCropFunction#from} is inclusive {@link TwoByteCropFunction#to} is exclusive</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
@Setter
public class TwoByteCropFunction<T, R> implements ImageFunction<T, R> {
    @NonNull
    private final ImageFactory<R> provider;

    /**
     * Start Point
     */
    private JavaPoint2D from;

    /**
     * End Point
     */
    private JavaPoint2D to;

    /**
     * @param x coordinate of start point for cropping
     * @param y coordinate of start point for cropping
     */
    public void setFrom(double x, double y) {
        this.from = new JavaPoint2D(x, y);
    }

    /**
     * @param x coordinate of end point for cropping
     * @param y coordinate of end point for cropping
     */
    public void setTo(double x, double y) {
        this.to = new JavaPoint2D(x, y);
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        ChannelType channelType = imageWrapper.getChannelType();

        if (!(0 <= from.getX() && from.getX() <= to.getX() && to.getX() <= width))
            throw new IllegalArgumentException("0 < from.x < to.x < width");
        if (!(0 <= from.getY() && from.getY() <= to.getY() && to.getY() <= height))
            throw new IllegalArgumentException("0 < from.y < to.y < height");


        int startY = from.getIntY();
        int startX = from.getIntX();

        ImageWrapper<R> resultWrapper = provider.getImage(to.getIntY() - startY, to.getIntX() - startX, channelType);

        IntStream.range(startY, to.getIntY()).parallel().forEach(y -> {
            for (int x = startX; x < to.getIntX(); x++) {
                for (int c = 0; c < imageWrapper.getChannels(); c++) {
                    resultWrapper.setValue(x - startX, y - startY, c, imageWrapper.getValue(x, y, c));

                }
            }
        });
        return resultWrapper;
    }
}
