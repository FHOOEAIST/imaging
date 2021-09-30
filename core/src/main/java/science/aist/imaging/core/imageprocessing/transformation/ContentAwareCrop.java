/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformation;

import lombok.Setter;
import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.SubImageWrapper;
import science.aist.jack.math.MathUtils;

import java.util.function.Function;

/**
 * <p>Function for applying a content aware cropping based on a given background color</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class ContentAwareCrop<I> implements Function<ImageWrapper<I>, SubImageWrapper<I>> {
    @Setter
    private Color backgroundColor = new Color(0, 0, 0);
    @Setter
    private int marginLeft;
    @Setter
    private int marginTop;
    @Setter
    private int marginRight;
    @Setter
    private int marginBottom;
    @Setter
    private boolean noCropLeft;
    @Setter
    private boolean noCropRight;
    @Setter
    private boolean noCropBottom;
    @Setter
    private boolean noCropTop;

    /**
     * Sets the same value to all margins
     *
     * @param margin in all directions
     */
    public void setMarginUnify(int margin) {
        marginLeft = margin;
        marginBottom = margin;
        marginTop = margin;
        marginRight = margin;
    }

    @Override
    public SubImageWrapper<I> apply(ImageWrapper<I> imageWrapper) {

        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean isBackground = true;
                double[] current = new double[imageWrapper.getChannels()];
                for (int c = 0; c < current.length; c++) {
                    if (!MathUtils.equals(imageWrapper.getValue(x, y, c), backgroundColor.getChannel(c))) {
                        isBackground = false;
                        break;
                    }
                }

                if (!isBackground) {
                    if (x < minX) {
                        minX = x;
                    }

                    if (x > maxX) {
                        maxX = x;
                    }

                    if (y < minY) {
                        minY = y;
                    }

                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }

        minX -= marginLeft;
        minY -= marginTop;
        maxX += marginRight + 1; // +1 necessary because of crop implementation
        maxY += marginBottom + 1; // +1 necessary because of crop implementation

        if (minX < 0 || minX > width) {
            minX = 0;
        }

        if (minY < 0 || minY > height) {
            minY = 0;
        }

        if (maxX < 0 || maxX > width) {
            maxX = width;
        }

        if (maxY < 0 || maxY > height) {
            maxY = height;
        }

        if (noCropLeft) {
            minX = 0;
        }

        if (noCropRight) {
            maxX = width;
        }
        if (noCropBottom) {
            maxY = height;
        }

        if (noCropTop) {
            minY = 0;
        }

        return new SubImageWrapper<>(imageWrapper, minX, minY, maxX - minX, maxY - minY);
    }
}
