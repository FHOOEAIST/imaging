/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.draw.circle;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.function.BiConsumer;

/**
 * <p>Method for drawing an X for a given position</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
@Setter
public class DrawX<I> implements BiConsumer<ImageWrapper<I>, JavaPoint2D> {
    @NonNull
    private BiConsumer<ImageWrapper<I>, JavaLine2D> baseDrawer;

    /**
     * Length of each diagonal line of the X
     */
    private int length = 5;

    /**
     * Rotation of the X in degrees
     */
    private int rotation = 45;

    @Override
    public void accept(ImageWrapper<I> wrapper, JavaPoint2D javaPoint2D) {
        JavaPoint2D center = javaPoint2D.sub(new JavaPoint2D(1, 1));
        JavaLine2D l1 = JavaLine2D.createByCenterRotationAndLength(center, Math.toRadians(rotation), length);
        JavaLine2D l2 = JavaLine2D.createByCenterRotationAndLength(center, Math.toRadians(rotation + 90), length);
        baseDrawer.accept(wrapper, l1);
        baseDrawer.accept(wrapper, l2);
    }

}
