/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import science.aist.imaging.api.domain.AbstractJavaPoint;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class JavaPoint3DTest {
    @Test
    void testPointDistance() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(0, 0, 0);
        JavaPoint3D jp2 = new JavaPoint3D(10, 10, 10);

        // when
        double v = JavaPoint3D.pointDistance(jp1, jp2);

        // then
        Assert.assertEquals(v, 17.32, 0.001);
    }

    @Test
    void testSub() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(0, 0, 0);
        JavaPoint3D jp2 = new JavaPoint3D(10, 10, 10);

        // when
        JavaPoint3D sub = jp1.sub(jp2);

        // then
        Assert.assertEquals(sub, new JavaPoint3D(-10, -10, -10));
    }

    @Test
    void testSub2() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(32.5, 12.8, 17.5);
        JavaPoint3D jp2 = new JavaPoint3D(5.2, 2.8, 3.3);

        // when
        JavaPoint3D sub = jp1.sub(jp2);

        // then
        Assert.assertEquals(sub, new JavaPoint3D(27.3, 10.0, 14.2));
    }


    @Test
    void testAdd() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 2, 7);
        JavaPoint3D jp2 = new JavaPoint3D(10, 10, 10);

        // when
        JavaPoint3D add = jp1.add(jp2);

        // then
        Assert.assertEquals(add, new JavaPoint3D(15, 12, 17));
    }

    @Test
    void testMult() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 2, 7);
        double factor = 10;

        // when
        JavaPoint3D mult = jp1.mult(factor);

        // then
        Assert.assertEquals(mult, new JavaPoint3D(50, 20, 70));
    }

    @Test
    void testMag() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 2, 7);

        // when
        double mag = jp1.getMag();

        // then
        Assert.assertEquals(mag, 8.831, 0.001);
    }

    @Test
    void testDot() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 2, 7);
        JavaPoint3D jp2 = new JavaPoint3D(10, 10, 10);

        // when
        double dot = jp1.dot(jp2);

        // then
        Assert.assertEquals(dot, 140, 0.001);
    }

    @Test
    public void testUnify() {
        // given
        JavaPoint3D jp = new JavaPoint3D(1, 0, 0);

        // when
        JavaPoint3D unify = jp.getUnify();

        // then
        Assert.assertEquals(jp, unify);
    }

    @Test
    public void testUnify2() {
        // given
        JavaPoint3D jp = new JavaPoint3D(5, 4, 3);

        // when
        JavaPoint3D unify = jp.getUnify();

        // then
        Assert.assertEquals(unify.getX(), 0.7071, 0.01);
        Assert.assertEquals(unify.getY(), 0.5656, 0.01);
        Assert.assertEquals(unify.getZ(), 0.4242, 0.01);
    }

    @Test
    public void testRotate() {
        // given
        JavaPoint3D jp = new JavaPoint3D(6, 0, 0);

        // when
        JavaPoint3D rotate = jp.rotate(0, 0, 90);

        // then
        Assert.assertEquals(rotate.getX(), 0, 0.001);
        Assert.assertEquals(rotate.getY(), 6, 0.001);
        Assert.assertEquals(rotate.getZ(), 0, 0.001);
    }

    @Test
    public void testRotate1() {
        // given
        JavaPoint3D jp = new JavaPoint3D(6, 0, 0);

        // when
        JavaPoint3D rotate = jp.rotate(0, 90, 0);

        // then
        Assert.assertEquals(rotate.getX(), 0, 0.001);
        Assert.assertEquals(rotate.getY(), 0, 0.001);
        Assert.assertEquals(rotate.getZ(), -6, 0.001);
    }

    @Test
    public void testRotate2() {
        // given
        JavaPoint3D jp = new JavaPoint3D(6, 0, 0);

        // when
        JavaPoint3D rotate = jp.rotate(90, 0, 0);

        // then
        Assert.assertEquals(rotate.getX(), 6, 0.001);
        Assert.assertEquals(rotate.getY(), 0, 0.001);
        Assert.assertEquals(rotate.getZ(), 0, 0.001);
    }

    @Test
    public void testRotate3() {
        // given
        JavaPoint3D jp = new JavaPoint3D(6, 0, 0);

        // when
        JavaPoint3D rotate = jp.rotate(30, 40, 20);

        // then
        Assert.assertEquals(rotate.getX(), 4.319, 0.001);
        Assert.assertEquals(rotate.getY(), 1.572, 0.001);
        Assert.assertEquals(rotate.getZ(), -3.856, 0.001);
    }

    @Test
    public void testRotate4() {
        // given
        JavaPoint3D jp = new JavaPoint3D(6, 0, 0);

        // when
        JavaPoint3D rotate = jp.rotate(new JavaPoint3D(5, 5, 5), 0, 90, 0);

        // then
        Assert.assertEquals(rotate.getX(), 0, 0.001);
        Assert.assertEquals(rotate.getY(), 0, 0.001);
        Assert.assertEquals(rotate.getZ(), 4, 0.001);
    }

    @Test
    public void testCrossProduct() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 3, 2);
        JavaPoint3D jp2 = new JavaPoint3D(4, 8, 5);

        // when
        JavaPoint3D c = jp1.crossProduct(jp2);

        // then
        Assert.assertEquals(c.getX(), -1, 0.0001);
        Assert.assertEquals(c.getY(), -17, 0.0001);
        Assert.assertEquals(c.getZ(), 28, 0.0001);
    }

    @Test
    public void testDiv() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 2, 1);
        JavaPoint3D jp2 = new JavaPoint3D(2.5, 1, 0.5);

        // when
        JavaPoint3D div = jp1.div(2);

        // then
        Assert.assertEquals(jp2, div);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDiv2() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(5, 2, 1);

        // when
        JavaPoint3D div = jp1.div(0);

        // then
    }

    @Test
    public void testTestEquals() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(10.0, 5.0, 2.0);
        JavaPoint3D jp2 = new JavaPoint3D(10.00001, 5.00001, 2.00001);

        // when
        boolean equals = jp1.equals(jp2, 0.0001);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testTestEquals2() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(10.0, 5.0, 2.0);
        JavaPoint3D jp2 = new JavaPoint3D(10.00001, 5.00001, 2.00001);

        // when
        boolean equals = jp1.equals(jp2, 0.0000000001);

        // then
        Assert.assertFalse(equals);
    }

    @Test
    public void testTestEquals3() {
        // given
        AbstractJavaPoint<?> jp1 = new JavaPoint3D(10.0, 5.0, 2.0);
        AbstractJavaPoint<?> jp2 = new JavaPoint2D(10.00001, 5.00001);

        // when
        boolean equals = jp1.equals(jp2, 0.0000000001);

        // then
        Assert.assertFalse(equals);
    }

    @Test
    public void testPositionalEqual() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(10.0, 5.0, 2.0);
        JavaPoint3D jp2 = new JavaPoint3D(10.00000000001, 5.000000000000001, 2.000000000000001);

        // when
        boolean equals = jp1.positionalEqual(jp2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testGetInt() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(11.1, -4.2, -4.5);

        // when
        int x = jp1.getIntX();
        int y = jp1.getIntY();
        int z = jp1.getIntZ();

        // then
        Assert.assertEquals(x, 11);
        Assert.assertEquals(y, -4);
        Assert.assertEquals(z, -5);
    }
}
