/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw.circle;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>Class for drawing a symmetrical polygon for a given center point</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class DrawPointAsPolygon<I> implements BiConsumer<ImageWrapper<I>, JavaPoint2D> {

    @NonNull
    private BiConsumer<ImageWrapper<I>, JavaPolygon2D> baseDrawer;

    @NonNull
    private Function<JavaPoint2D, JavaPolygon2D> polygonFunction;


    @Override
    public void accept(ImageWrapper<I> wrapper, JavaPoint2D javaPoint2D) {
        baseDrawer.accept(wrapper, polygonFunction.apply(javaPoint2D));
    }
}
