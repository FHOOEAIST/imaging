/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Test {@link MooreNeighborInnerBoundaryTracing}</p>
 *
 * @author Andreas Pointner
 */
public class MooreNeighborInnerBoundaryTracingTest {
    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    void testExtractBoundary() {
        // given
        MooreNeighborInnerBoundaryTracing<JavaPoint2D> tracing = new MooreNeighborInnerBoundaryTracing<>();
        List<JavaPoint2D> area = Arrays.asList(
                new JavaPoint2D(3, 2),
                new JavaPoint2D(4, 2),
                new JavaPoint2D(5, 2),

                new JavaPoint2D(2, 3),
                new JavaPoint2D(3, 3),
                new JavaPoint2D(4, 3),

                new JavaPoint2D(2, 4),
                new JavaPoint2D(3, 4),
                new JavaPoint2D(4, 4),

                new JavaPoint2D(2, 5),
                new JavaPoint2D(3, 5),
                new JavaPoint2D(4, 5)
        );

        // when
        List<JavaPoint2D> result = tracing.apply(area);

        // then
        Assert.assertEquals(result.get(0), new JavaPoint2D(2, 3));
        Assert.assertEquals(result.get(1), new JavaPoint2D(2, 4));
        Assert.assertEquals(result.get(2), new JavaPoint2D(2, 5));
        Assert.assertEquals(result.get(3), new JavaPoint2D(3, 5));
        Assert.assertEquals(result.get(4), new JavaPoint2D(4, 5));
        Assert.assertEquals(result.get(5), new JavaPoint2D(4, 4));
        Assert.assertEquals(result.get(6), new JavaPoint2D(4, 3));
        Assert.assertEquals(result.get(7), new JavaPoint2D(5, 2));
        Assert.assertEquals(result.get(8), new JavaPoint2D(4, 2));
        Assert.assertEquals(result.get(9), new JavaPoint2D(3, 2));
    }

    @Test
    void testExtractBoundary2() {
        // given
        MooreNeighborInnerBoundaryTracing<JavaPoint2D> tracing = new MooreNeighborInnerBoundaryTracing<>();
        List<JavaPoint2D> area = Arrays.asList(
                new JavaPoint2D(1, 1),
                new JavaPoint2D(2, 1),
                new JavaPoint2D(1, 2)
        );

        // when
        List<JavaPoint2D> result = tracing.apply(area);

        // then
        Assert.assertEquals(result.get(0), new JavaPoint2D(1, 2));
        Assert.assertEquals(result.get(1), new JavaPoint2D(2, 1));
        Assert.assertEquals(result.get(2), new JavaPoint2D(1, 1));
    }

    @Test
    void testExtractBoundary3() {
        // given
        MooreNeighborInnerBoundaryTracing<JavaPoint2D> tracing = new MooreNeighborInnerBoundaryTracing<>();
        List<JavaPoint2D> area = Collections.singletonList(new JavaPoint2D(1, 1));

        // when
        List<JavaPoint2D> result = tracing.apply(area);

        // then
        Assert.assertEquals(result.get(0), new JavaPoint2D(1, 1));
    }

    @Test
    void testExtractBoundary4() {
        // given
        MooreNeighborInnerBoundaryTracing<JavaPoint2D> tracing = new MooreNeighborInnerBoundaryTracing<>();
        List<JavaPoint2D> area = Arrays.asList(
                new JavaPoint2D(1, 1),
                new JavaPoint2D(1, 2)
        );

        // when
        List<JavaPoint2D> result = tracing.apply(area);

        // then
        Assert.assertEquals(result.get(0), new JavaPoint2D(1, 2));
        Assert.assertEquals(result.get(1), new JavaPoint2D(1, 1));
    }
}
