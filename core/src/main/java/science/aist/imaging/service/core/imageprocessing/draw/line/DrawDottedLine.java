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
 * <p>Drawer for drawing a dotted line</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public class DrawDottedLine<I> implements BiConsumer<ImageWrapper<I>, JavaLine2D> {
    @NonNull
    private BiConsumer<ImageWrapper<I>, JavaPoint2D> baseDrawer;

    private int dotDistance = 0;

    /**
     * sets value of field {@link DrawDottedLine#dotDistance}
     *
     * @param dotDistance value of field dotDistance
     * @see DrawDottedLine#dotDistance
     */
    public void setDotDistance(int dotDistance) {
        if (dotDistance < 0) {
            throw new IllegalArgumentException("DotDistance must be >= 0");
        }
        this.dotDistance = dotDistance;
    }

    public void accept(ImageWrapper<I> imageWrapper, JavaLine2D javaLine2D) {
        List<JavaPoint2D> interpolatedPoints = javaLine2D.getBresenham();
        for (int i = 0; i < interpolatedPoints.size(); i += dotDistance + 1) {
            JavaPoint2D p = interpolatedPoints.get(i);
            baseDrawer.accept(imageWrapper, p);
        }
    }

}
