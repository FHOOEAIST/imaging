/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link JavaTriangle2D}</p>
 *
 * @author Christoph Praschl
 */
public class JavaTriangle2DTest {

    @Test
    public void testTestEquals() {
        // given
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));
        JavaTriangle2D t2 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));

        // when
        boolean equals = t1.equals(t2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testTestEquals2() {
        // given
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));
        JavaTriangle2D t2 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(3, 0));

        // when
        boolean equals = t1.equals(t2);

        // then
        Assert.assertFalse(equals);
    }

    @Test
    public void testContains() {
        // given
        JavaPoint2D jp = new JavaPoint2D(1, 0.5);
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));

        // when
        boolean contains = t1.contains(jp);

        // then
        Assert.assertTrue(contains);
    }

    @Test
    public void testContains2() {
        // given
        JavaPoint2D jp = new JavaPoint2D(10.5, 10.5);
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));

        // when
        boolean contains = t1.contains(jp);

        // then
        Assert.assertFalse(contains);
    }


    @Test
    public void testIsPointInCircumcircle() {
        // given
        JavaPoint2D jp = new JavaPoint2D(1, 0.5);
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));

        // when
        boolean contains = t1.isPointInCircumcircle(jp);

        // then
        Assert.assertTrue(contains);
    }

    @Test
    public void testIsPointInCircumcircle2() {
        // given
        JavaPoint2D jp = new JavaPoint2D(10.5, 10.5);
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));

        // when
        boolean contains = t1.isPointInCircumcircle(jp);

        // then
        Assert.assertFalse(contains);
    }

    @Test
    public void testIsOrientedCCW() {
        // given
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(2, 0), new JavaPoint2D(1, 1), new JavaPoint2D(0, 0));

        // when
        boolean orientedCCW = t1.isOrientedCCW();

        // then
        Assert.assertTrue(orientedCCW);
    }

    @Test
    public void testIsOrientedCCW2() {
        // given
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(1, 1), new JavaPoint2D(2, 0), new JavaPoint2D(0, 0));

        // when
        boolean orientedCCW = t1.isOrientedCCW();

        // then
        Assert.assertFalse(orientedCCW);
    }

    @Test
    public void testIsNeighbour() {
        // given
        JavaLine2D javaLine2D = new JavaLine2D(new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(1, 1), new JavaPoint2D(2, 0), new JavaPoint2D(0, 0));

        // when
        boolean neighbour = t1.isNeighbour(javaLine2D);

        // then
        Assert.assertTrue(neighbour);
    }

    @Test
    public void testIsNeighbour2() {
        // given
        JavaLine2D javaLine2D = new JavaLine2D(new JavaPoint2D(5, 5), new JavaPoint2D(4, 0));
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(1, 1), new JavaPoint2D(2, 0), new JavaPoint2D(0, 0));

        // when
        boolean neighbour = t1.isNeighbour(javaLine2D);

        // then
        Assert.assertFalse(neighbour);
    }

    @Test
    public void testGetNoneEdgeVertex() {
        // given
        JavaLine2D javaLine2D = new JavaLine2D(new JavaPoint2D(1, 1), new JavaPoint2D(2, 0));
        JavaTriangle2D t1 = new JavaTriangle2D(new JavaPoint2D(1, 1), new JavaPoint2D(2, 0), new JavaPoint2D(0, 0));

        JavaPoint2D reference = new JavaPoint2D(0, 0);

        // when
        JavaPoint2D noneEdgeVertex = t1.getNoneEdgeVertex(javaLine2D);

        // then
        Assert.assertEquals(reference, noneEdgeVertex);
    }


}
