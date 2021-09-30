/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.draw.circle;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Draw implementation for drawing a circle filled</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class DrawCircleFilled<I> extends DrawCircle<I> {
    @Override
    public void accept(ImageWrapper<I> iImageWrapper, JavaPoint2D center) {
        if (radius == 0) {
            super.accept(iImageWrapper, center);
        } else {
            Set<JavaPoint2D> points = new HashSet<>();
            for (int r = 0; r <= radius; r++) {
                addCirclePoints(center, points, r);
            }
            addOffsetPoints(points, thickness);
            drawPoints(iImageWrapper, points);
        }
    }
}
