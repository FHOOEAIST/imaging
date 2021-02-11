/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw;

import lombok.Setter;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * <p>Abstract class for drawing on a given image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractDrawer<I, T> implements BiConsumer<ImageWrapper<I>, T> {
    @Setter
    protected double[] color = new double[]{255};

    @Setter
    protected int thickness = 0;

    protected void addOffsetPoints(Set<JavaPoint2D> points, int thickness) {
        Set<JavaPoint2D> addPoints = new HashSet<>();
        for (JavaPoint2D point : points) {
            double x = point.getX();
            double y = point.getY();
            for (int xoffset = -thickness; xoffset <= thickness; xoffset++) {
                for (int yoffset = -thickness; yoffset <= thickness; yoffset++) {
                    addPoints.add(new JavaPoint2D(x + xoffset, y + yoffset));
                }
            }
        }
        points.addAll(addPoints);
    }

    protected void drawPoints(ImageWrapper<I> iImageWrapper, Collection<JavaPoint2D> points) {
        int width = iImageWrapper.getWidth();
        int height = iImageWrapper.getHeight();

        if (color.length != iImageWrapper.getChannels()) {
            throw new IllegalArgumentException("Given color does not fit for the expected channel type");
        }

        for (JavaPoint2D p : points) {
            if (p.getIntX() >= 0 && p.getIntY() >= 0 && p.getIntX() < width && p.getIntY() < height) {
                iImageWrapper.setValues(p.getIntX(), p.getIntY(), color);
            }
        }
    }
}
