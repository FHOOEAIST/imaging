/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Test class for {@link JavaPolygon3D}</p>
 *
 * @author Christoph Praschl
 */
public class JavaPolygon3DTest {

    private static JavaPolygon3D getSample() {
        return new JavaPolygon3D(new JavaPoint3D(0, 0, 0), new JavaPoint3D(5, 0, 0), new JavaPoint3D(0, 3, 9));
    }

    @Test
    public void testGetCentroid() {
        // given
        JavaPolygon3D polygon3D = getSample();

        // when
        JavaPoint3D centroid = polygon3D.getCentroid();

        // then
        Assert.assertEquals(centroid, new JavaPoint3D(1.666666666666666666666, 1.0, 3.0));
    }

    @Test
    public void testGetWidth() {
        // given
        JavaPolygon3D polygon3D = getSample();

        // when
        double width = polygon3D.getWidth();

        // then
        Assert.assertEquals(width, 5.0);
    }

    @Test
    public void testGetHeight() {
        // given
        JavaPolygon3D polygon3D = getSample();

        // when
        double width = polygon3D.getHeight();

        // then
        Assert.assertEquals(width, 3.0);
    }

    @Test
    public void testGetDepth() {
        // given
        JavaPolygon3D polygon3D = getSample();

        // when
        double width = polygon3D.getDepth();

        // then
        Assert.assertEquals(width, 9.0);
    }

    @Test
    public void testGetContour() {
        // given
        JavaPolygon3D sample = getSample();

        // when
        List<JavaLine3D> pathSegments3D = sample.getContour();

        // then
        Assert.assertEquals(pathSegments3D.size(), 3);
        Assert.assertTrue(pathSegments3D.contains(new JavaLine3D(new JavaPoint3D(0, 0, 0), new JavaPoint3D(5, 0, 0))));
        Assert.assertTrue(pathSegments3D.contains(new JavaLine3D(new JavaPoint3D(5, 0, 0), new JavaPoint3D(0, 3, 9))));
        Assert.assertTrue(pathSegments3D.contains(new JavaLine3D(new JavaPoint3D(0, 3, 9), new JavaPoint3D(0, 0, 0))));
    }

    @Test
    public void testEquals() {
        // given
        List<JavaPoint3D> points = Arrays.asList(new JavaPoint3D(5, 2, 3), new JavaPoint3D(1, 8, 1), new JavaPoint3D(0, 6, 4));
        JavaPolygon3D jp1 = new JavaPolygon3D(points);
        JavaPolygon3D jp2 = new JavaPolygon3D(points);

        // when
        boolean equals = jp1.equals(jp2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testEquals2() {
        // given
        JavaPolygon3D jp1 = new JavaPolygon3D(Arrays.asList(new JavaPoint3D(5, 2, 3), new JavaPoint3D(1, 8, 1), new JavaPoint3D(0, 6, 4)));
        JavaPolygon3D jp2 = new JavaPolygon3D(Arrays.asList(new JavaPoint3D(5, 2, 3), new JavaPoint3D(0, 6, 4), new JavaPoint3D(1, 8, 1)));

        // when
        boolean equals = jp1.equals(jp2);

        // then
        Assert.assertFalse(equals);
    }

    @Test
    public void testCalculateNormalvector() {
        // given
        JavaPolygon3D jp1 = new JavaPolygon3D(Arrays.asList(new JavaPoint3D(5, 2, 3), new JavaPoint3D(1, 8, 1), new JavaPoint3D(0, 6, 4)));

        JavaPoint3D reference = new JavaPoint3D(2.0, 5.333333, 2.6666666);

        // when
        JavaPoint3D javaPoint3D = jp1.calculateNormalvector();

        // then
        Assert.assertTrue(javaPoint3D.positionalEqual(reference, 0.001));
    }

    @Test
    public void testCalculateNormalvector2() {
        // given
        JavaPoint3D jp = new JavaPoint3D();
        JavaPolygon3D jp1 = new JavaPolygon3D(Arrays.asList(new NormalJavaPoint3D(jp, 5, 2, 3), new NormalJavaPoint3D(jp, 1, 8, 1), new NormalJavaPoint3D(jp, 0, 6, 4)));

        JavaPoint3D reference = new JavaPoint3D(2.0, 5.333333, 2.6666666);

        // when
        JavaPoint3D javaPoint3D = jp1.calculateNormalvector();

        // then
        Assert.assertTrue(javaPoint3D.positionalEqual(reference, 0.001));
    }

}