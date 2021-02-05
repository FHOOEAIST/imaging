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
 * @author Andreas Pointner
 */
public class JavaPoint2DTest {
    /**
     * Tests JavaPoint.equals function
     */
    @Test
    void testEquals() {
        // given
        JavaPoint2D p1 = new JavaPoint2D(2, 3);
        JavaPoint2D p2 = new JavaPoint2D(2, 3);

        // when
        boolean equals = p1.equals(p2);

        // then
        Assert.assertTrue(equals);
    }

    /**
     * Tests JavaPoint.equals function
     */
    @Test
    void testNotEquals() {
        // given
        JavaPoint2D p1 = new JavaPoint2D(2, 3);
        JavaPoint2D p2 = new JavaPoint2D(3, 3);

        // when
        boolean equals = p1.equals(p2);

        // then
        Assert.assertFalse(equals);
    }

    /**
     * Tests JavaPoint.hashCode function
     */
    @Test
    void testHashCode() {
        // given
        JavaPoint2D p1 = new JavaPoint2D(2, 3);
        JavaPoint2D p2 = new JavaPoint2D(2, 3);

        // when
        boolean equalsHash = p1.hashCode() == p2.hashCode();

        // then
        Assert.assertTrue(equalsHash);
    }

    @Test
    void testXDistance() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(2, 3);

        // when
        double distance = JavaPoint2D.xDistance(jp1, jp2);

        // then
        Assert.assertEquals(distance, 1.0, 0.0001);
    }

    @Test
    void testYDistance() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(2, 3);

        // when
        double distance = JavaPoint2D.yDistance(jp1, jp2);

        // then
        Assert.assertEquals(distance, 2.0, 0.0001);
    }

    @Test
    void testPointDistance() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(1, 2);

        // when
        double distance = JavaPoint2D.pointDistance(jp1, jp2);

        // then
        Assert.assertEquals(distance, 1, 0.0001);
    }

    @Test
    void testPointDistance2() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(3, 3);

        // when
        double distance = JavaPoint2D.pointDistance(jp1, jp2);

        // then
        Assert.assertEquals(distance, Math.sqrt(8), 0.0001);
    }

    @Test
    void testGetIntX1() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(0.5, 0);

        // when
        int xInt = jp1.getIntX();

        // then
        Assert.assertEquals(xInt, 1);
    }

    @Test
    void testGetIntX2() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(0.49, 0);

        // when
        int xInt = jp1.getIntX();

        // then
        Assert.assertEquals(xInt, 0);
    }

    @Test
    void testGetIntY1() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(0, 0.5);

        // when
        int xInt = jp1.getIntY();

        // then
        Assert.assertEquals(xInt, 1);
    }

    @Test
    void testGetIntY2() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(0, 0.49);

        // when
        int xInt = jp1.getIntY();

        // then
        Assert.assertEquals(xInt, 0);
    }

    @Test
    void testRotate() {
        // given
        JavaPoint2D jp = new JavaPoint2D(1, 1);
        double rotate = Math.toRadians(45);

        // when
        JavaPoint2D jpRes = jp.rotate(rotate);

        // then
        Assert.assertEquals(jpRes, new JavaPoint2D(0, Math.sqrt(2)));
    }

    @Test
    void testRotate2() {
        // given
        JavaPoint2D jp = new JavaPoint2D(2, 2);
        JavaPoint2D origin = new JavaPoint2D(1, 1);
        double rotate = Math.toRadians(45);

        // when
        JavaPoint2D jpRes = jp.rotate(rotate, origin);

        // then
        Assert.assertEquals(jpRes, new JavaPoint2D(1, 1 + Math.sqrt(2)));
    }

    @Test
    public void testUnify() {
        // given
        JavaPoint2D jp = new JavaPoint2D(1, 0);

        // when
        JavaPoint2D unify = jp.getUnify();

        // then
        Assert.assertEquals(jp, unify);
    }

    @Test
    public void testUnify2() {
        // given
        JavaPoint2D jp = new JavaPoint2D(3, 7);

        // when
        JavaPoint2D unify = jp.getUnify();

        // then
        Assert.assertEquals(unify.getX(), 0.3939, 0.01);
        Assert.assertEquals(unify.getY(), 0.9191, 0.01);
    }

    @Test
    public void testDiv() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(80, 4);
        JavaPoint2D jp2 = new JavaPoint2D(20, 1);

        // when
        JavaPoint2D div = jp1.div(4);

        // then
        Assert.assertEquals(jp2, div);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDiv2() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(5, 2);

        // when
        JavaPoint2D div = jp1.div(0);

        // then
    }
}
