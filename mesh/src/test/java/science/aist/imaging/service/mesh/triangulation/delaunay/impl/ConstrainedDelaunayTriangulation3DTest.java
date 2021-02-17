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
import science.aist.imaging.api.domain.threedimensional.JavaLine3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.domain.threedimensional.JavaPolygon3D;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Test class for {@link ConstrainedDelaunayTriangulation3D}</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class ConstrainedDelaunayTriangulation3DTest {

    private List<JavaPoint3D> getPoints() {
        return Arrays.asList(
                new JavaPoint3D(0, 0, 0),
                new JavaPoint3D(4, 0, 0),
                new JavaPoint3D(4, 4, 0),
                new JavaPoint3D(2, 2, 0),
                new JavaPoint3D(0, 4, 0)
        );
    }

    @Test
    public void testTriangulate() {
        // given
        List<JavaPoint3D> points = getPoints();

        ConstrainedDelaunayTriangulation3D triangulation = new ConstrainedDelaunayTriangulation3D();

        List<JavaPolygon3D> reference = Arrays.asList(
                new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(0.0, 4.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 0.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(4.0, 0.0, 0.0), new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 4.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(0.0, 4.0, 0.0), new JavaPoint3D(4.0, 4.0, 0.0))
        );

        // when
        List<JavaPolygon3D> triangulate = triangulation.triangulate(points);

        // then
        Assert.assertEquals(triangulate.size(), 4);
        Assert.assertEquals(triangulate, reference);
    }

    @Test
    public void testTriangulate2() {
        // given
        List<JavaPoint3D> points = getPoints();

        ConstrainedDelaunayTriangulation3D triangulation = new ConstrainedDelaunayTriangulation3D(false, true);
        List<JavaLine3D> constraints = Collections.singletonList(
                new JavaLine3D(new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 2.0, 0.0))
        );

        List<JavaPolygon3D> reference = Arrays.asList(
                new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(0.0, 4.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 0.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(4.0, 0.0, 0.0), new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 2.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 4.0, 0.0), new JavaPoint3D(4.0, 2.0, 0.0)),
                new JavaPolygon3D(new JavaPoint3D(2.0, 2.0, 0.0), new JavaPoint3D(4.0, 4.0, 0.0), new JavaPoint3D(0.0, 4.0, 0.0))
        );

        // when
        List<JavaPolygon3D> triangulate = triangulation.triangulate(points, constraints);

        // then
        Assert.assertEquals(triangulate.size(), 5);
        Assert.assertEquals(triangulate, reference);
    }
}
