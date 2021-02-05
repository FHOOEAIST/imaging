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
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

/**
 * <p>Tests {@link JavaPolygonMergeFunction}</p>
 *
 * @author Andreas Pointner
 */
public class JavaPolygon2DMergeFunctionTest {

    @Test
    void testFailure1() {
        // given
        JavaPolygonMergeFunction jpmf = new JavaPolygonMergeFunction();
        JavaPolygon2D jp1 = new JavaPolygon2D(
                new JavaPoint2D(0, 0),
                new JavaPoint2D(10, 0),
                new JavaPoint2D(10, 10),
                new JavaPoint2D(0, 10)

        );
        JavaPolygon2D jp2 = new JavaPolygon2D(
                new JavaPoint2D(5, 5),
                new JavaPoint2D(15, 5),
                new JavaPoint2D(15, 15),
                new JavaPoint2D(5, 15)

        );

        // when
        Optional<JavaPolygon2D> apply = jpmf.apply(jp1, jp2);

        // then
        Assert.assertFalse(apply.isPresent());
    }

    @Test
    void testSuccess1() {
        // given
        JavaPolygonMergeFunction jpmf = new JavaPolygonMergeFunction();
        jpmf.setIntersectionRatio(0.24);
        JavaPolygon2D jp1 = new JavaPolygon2D(
                new JavaPoint2D(0, 0),
                new JavaPoint2D(10, 0),
                new JavaPoint2D(10, 10),
                new JavaPoint2D(0, 10)

        );
        JavaPolygon2D jp2 = new JavaPolygon2D(
                new JavaPoint2D(5, 5),
                new JavaPoint2D(15, 5),
                new JavaPoint2D(15, 15),
                new JavaPoint2D(5, 15)

        );

        // when
        Optional<JavaPolygon2D> apply = jpmf.apply(jp1, jp2);

        // then
        Assert.assertTrue(apply.isPresent());
        List<JavaPoint2D> points = apply.get().getPoints();
        Assert.assertEquals(points.get(0), new JavaPoint2D(0, 0));
        Assert.assertEquals(points.get(1), new JavaPoint2D(10, 0));
        Assert.assertEquals(points.get(2), new JavaPoint2D(10, 5));
        Assert.assertEquals(points.get(3), new JavaPoint2D(15, 5));
        Assert.assertEquals(points.get(4), new JavaPoint2D(15, 15));
        Assert.assertEquals(points.get(5), new JavaPoint2D(5, 15));
        Assert.assertEquals(points.get(6), new JavaPoint2D(5, 10));
        Assert.assertEquals(points.get(7), new JavaPoint2D(0, 10));
    }
}
