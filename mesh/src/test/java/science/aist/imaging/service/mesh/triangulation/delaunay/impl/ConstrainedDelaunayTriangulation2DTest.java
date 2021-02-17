/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.triangulation.delaunay.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Test class for {@link ConstrainedDelaunayTriangulation2D}</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class ConstrainedDelaunayTriangulation2DTest {

    private List<JavaPoint2D> getPoints() {
        return Arrays.asList(
                new JavaPoint2D(0, 0),
                new JavaPoint2D(4, 0),
                new JavaPoint2D(4, 4),
                new JavaPoint2D(2, 2),
                new JavaPoint2D(0, 4)
        );
    }

    @Test
    public void testTriangulate() {
        // given
        List<JavaPoint2D> points = getPoints();

        ConstrainedDelaunayTriangulation2D triangulation = new ConstrainedDelaunayTriangulation2D();

        List<JavaPolygon2D> reference = Arrays.asList(
                new JavaPolygon2D(new JavaPoint2D(0.0, 0.0), new JavaPoint2D(2.0, 2.0), new JavaPoint2D(0.0, 4.0)),
                new JavaPolygon2D(new JavaPoint2D(0.0, 0.0), new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 0.0)),
                new JavaPolygon2D(new JavaPoint2D(4.0, 0.0), new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 4.0)),
                new JavaPolygon2D(new JavaPoint2D(2.0, 2.0), new JavaPoint2D(0.0, 4.0), new JavaPoint2D(4.0, 4.0))
        );

        // when
        List<JavaPolygon2D> triangulate = triangulation.triangulate(points);

        // then
        Assert.assertEquals(triangulate.size(), 4);
        Assert.assertEquals(triangulate, reference);
    }

    @Test
    public void testTriangulate2() {
        // given
        List<JavaPoint2D> points = getPoints();

        ConstrainedDelaunayTriangulation2D triangulation = new ConstrainedDelaunayTriangulation2D(false, true);
        List<JavaLine2D> constraints = Collections.singletonList(
                new JavaLine2D(new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 2.0))
        );

        List<JavaPolygon2D> reference = Arrays.asList(
                new JavaPolygon2D(new JavaPoint2D(0.0, 0.0), new JavaPoint2D(2.0, 2.0), new JavaPoint2D(0.0, 4.0)),
                new JavaPolygon2D(new JavaPoint2D(0.0, 0.0), new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 0.0)),
                new JavaPolygon2D(new JavaPoint2D(4.0, 0.0), new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 2.0)),
                new JavaPolygon2D(new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 4.0), new JavaPoint2D(4.0, 2.0)),
                new JavaPolygon2D(new JavaPoint2D(2.0, 2.0), new JavaPoint2D(4.0, 4.0), new JavaPoint2D(0.0, 4.0))
        );

        // when
        List<JavaPolygon2D> triangulate = triangulation.triangulate(points, constraints);

        // then
        Assert.assertEquals(triangulate.size(), 5);
        Assert.assertEquals(triangulate, reference);
    }


}
