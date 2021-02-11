/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;

/**
 * @author Christoph Praschl
 */
public class JavaPoint2DPathPositionComparatorTest {
    @Test
    public void testCompare1() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(4, 0), new JavaPoint2D(2, 2), new JavaPoint2D(2, -2));
        JavaPointPathPositionComparator comparator = new JavaPointPathPositionComparator(jp);

        JavaPoint2D p1 = new JavaPoint2D(2, 0);
        JavaPoint2D p2 = new JavaPoint2D(2, -1);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertTrue(compare < 0);
    }

    @Test
    public void testCompare2() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(4, 0), new JavaPoint2D(2, 2), new JavaPoint2D(2, -2));
        JavaPointPathPositionComparator comparator = new JavaPointPathPositionComparator(jp);

        JavaPoint2D p1 = new JavaPoint2D(2, 0);
        JavaPoint2D p2 = new JavaPoint2D(2, 0);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertEquals(compare, 0);
    }

    @Test
    public void testCompare3() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(4, 0), new JavaPoint2D(2, 2), new JavaPoint2D(4, -4));
        JavaPointPathPositionComparator comparator = new JavaPointPathPositionComparator(jp);

        JavaPoint2D p1 = new JavaPoint2D(4, -4);
        JavaPoint2D p2 = new JavaPoint2D(2, 0);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertTrue(compare > 0);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCompare4() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(4, 0), new JavaPoint2D(2, 2), new JavaPoint2D(4, -4));
        JavaPointPathPositionComparator comparator = new JavaPointPathPositionComparator(jp);

        JavaPoint2D p1 = new JavaPoint2D(4, -4);
        JavaPoint2D p2 = new JavaPoint2D(10, 10);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        // - Exception
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testConstructor() {
        // given
        new JavaPointPathPositionComparator(null);

        // when

        // then
        // - Exception
    }

}
