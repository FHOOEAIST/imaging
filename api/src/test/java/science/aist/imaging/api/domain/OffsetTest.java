/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import science.aist.imaging.api.domain.offset.OrientationOffset;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OffsetTest {

    /**
     * Tests constructor OrientationOffset()
     */
    @Test
    void testConstructor1() {
        // given

        // when
        OrientationOffset o = new OrientationOffset();

        // then
        Assert.assertEquals(o.getFailure(), Double.MIN_VALUE);
        Assert.assertEquals(o.getHorizontalAngleOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getVerticalAngleOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getRotationalOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getXOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getYOffset(), Double.MAX_VALUE);
    }

    /**
     * Tests constructor OrientationOffset(x,z)
     */
    @Test
    void testConstructor2() {
        // given

        // when
        OrientationOffset o = new OrientationOffset(10, 20);

        // then
        Assert.assertEquals(o.getFailure(), Double.MIN_VALUE);
        Assert.assertEquals(o.getHorizontalAngleOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getVerticalAngleOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getRotationalOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getXOffset(), 10.0);
        Assert.assertEquals(o.getYOffset(), 20.0);
    }

    /**
     * Tests constructor OrientationOffset(x,y,r)
     */
    @Test
    void testConstructor3() {
        // given

        // when
        OrientationOffset o = new OrientationOffset(10, 20, 300);

        // then
        Assert.assertEquals(o.getFailure(), Double.MIN_VALUE);
        Assert.assertEquals(o.getHorizontalAngleOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getVerticalAngleOffset(), Double.MAX_VALUE);
        Assert.assertEquals(o.getRotationalOffset(), 300.0);
        Assert.assertEquals(o.getXOffset(), 10.0);
        Assert.assertEquals(o.getYOffset(), 20.0);
    }

    /**
     * Tests constructor OrientationOffset(x,y,r) with r > 360 throwing exception
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void testConstructor4() {
        // given

        // when
        OrientationOffset o = new OrientationOffset(10, 20, 800);

        // then
    }

    /**
     * Tests OrientationOffset.setRotationalOffset(r) with r = 150
     */
    @Test
    void testSetRotationalOffset1() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setRotationalOffset(150);

        // then
        Assert.assertEquals(o.getRotationalOffset(), 150.0);
    }

    /**
     * Tests OrientationOffset.setRotationalOffset(r) with r = Double.MAX_Value
     */
    @Test
    void testSetRotationalOffset2() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setRotationalOffset(Double.MAX_VALUE);

        // then
        Assert.assertEquals(o.getRotationalOffset(), Double.MAX_VALUE);
    }

    /**
     * Tests OrientationOffset.setRotationalOffset(r)  with r  > 360 && r < Double.MAX_Value (should throw Exception)
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void testSetRotationalOffset3() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setRotationalOffset(400);

        // then
    }


    /**
     * Tests OrientationOffset.setHorizontalAngleOffset(r) with r  = 150
     */
    @Test
    void testSetHorizontalAngleOffset1() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setHorizontalAngleOffset(150.0);

        // then
        Assert.assertEquals(o.getHorizontalAngleOffset(), 150.0);
    }

    /**
     * Tests OrientationOffset.setHorizontalAngleOffset(r) with r  = Double.MAX_Value
     */
    @Test
    void testSetHorizontalAngleOffset2() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setHorizontalAngleOffset(Double.MAX_VALUE);

        // then
        Assert.assertEquals(o.getHorizontalAngleOffset(), Double.MAX_VALUE);
    }

    /**
     * Tests OrientationOffset.setHorizontalAngleOffset(r) with r  > 360 && r < Double.MAX_Value (should throw Exception)
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void testSetHorizontalAngleOffset3() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setHorizontalAngleOffset(400);

        // then
    }

    /**
     * Tests OrientationOffset.setVerticalAngleOffset(r) with r  = 150
     */
    @Test
    void testSetVerticalAngleOffset1() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setVerticalAngleOffset(150.0);

        // then
        Assert.assertEquals(o.getVerticalAngleOffset(), 150.0);
    }

    /**
     * Tests OrientationOffset.setVerticalAngleOffset(r) with r = Double.MAX_Value
     */
    @Test
    void testSetVerticalAngleOffset2() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setVerticalAngleOffset(Double.MAX_VALUE);

        // then
        Assert.assertEquals(o.getVerticalAngleOffset(), Double.MAX_VALUE);
    }

    /**
     * Tests OrientationOffset.setVerticalAngleOffset(r) with r  > 360 && r < Double.MAX_Value (should throw Exception)
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void testSetVerticalAngleOffset3() {
        // given
        OrientationOffset o = new OrientationOffset();

        // when
        o.setVerticalAngleOffset(400);

        // then
    }

}
