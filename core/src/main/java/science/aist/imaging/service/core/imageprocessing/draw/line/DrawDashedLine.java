/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw.line;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>Drawer for drawing a dashed line</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public class DrawDashedLine<I> implements BiConsumer<ImageWrapper<I>, JavaLine2D> {
    @NonNull
    private BiConsumer<ImageWrapper<I>, JavaLine2D> baseDrawer;

    /**
     * Length of one dash segment
     */
    private int dashLength = 5;

    /**
     * sets value of field {@link DrawDashedLine#dashLength}
     *
     * @param dashLength value of field dashLength
     * @see DrawDashedLine#dashLength
     */
    public void setDashLength(int dashLength) {
        if (dashLength < 2) {
            throw new IllegalArgumentException("DashLength must be >= 2");
        }
        this.dashLength = dashLength;
    }

    public void accept(ImageWrapper<I> imageWrapper, JavaLine2D javaLine2D) {
        List<JavaPoint2D> interpolatedPoints = javaLine2D.getBresenham();

        int size = interpolatedPoints.size();
        for (int i = 0; i < size; i += dashLength * 2) {
            JavaPoint2D p = interpolatedPoints.get(i);
            int j = i + dashLength - 1;
            JavaPoint2D p2;
            if (j < size) {
                p2 = interpolatedPoints.get(j);
            } else {
                p2 = interpolatedPoints.get(size - 1);
            }

            baseDrawer.accept(imageWrapper, new JavaLine2D(p, p2));
        }
    }

}
