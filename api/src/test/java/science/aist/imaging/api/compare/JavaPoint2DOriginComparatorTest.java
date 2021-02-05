/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class JavaPoint2DOriginComparatorTest {
    @Test
    public void testCompare1() {
        // given
        JavaPointOriginComparator comparator = new JavaPointOriginComparator();

        JavaPoint2D p1 = new JavaPoint2D(2, 0);
        JavaPoint2D p2 = new JavaPoint2D(-2, 0);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertEquals(compare, 0);
    }

    @Test
    public void testCompare2() {
        // given
        JavaPointOriginComparator comparator = new JavaPointOriginComparator();

        JavaPoint2D p1 = new JavaPoint2D(2, 0);
        JavaPoint2D p2 = new JavaPoint2D(-5, 0);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertTrue(compare < 0);
    }

    @Test
    public void testCompare3() {
        // given
        JavaPointOriginComparator comparator = new JavaPointOriginComparator();

        JavaPoint2D p1 = new JavaPoint2D(8, 0);
        JavaPoint2D p2 = new JavaPoint2D(-2, 0);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertTrue(compare > 0);
    }

    @Test
    public void testCompare4() {
        // given
        JavaPointOriginComparator comparator = new JavaPointOriginComparator();
        comparator.setOrigin(new JavaPoint2D(7, 0));
        JavaPoint2D p1 = new JavaPoint2D(8, 0);
        JavaPoint2D p2 = new JavaPoint2D(-2, 0);

        // when
        int compare = comparator.compare(p1, p2);

        // then
        Assert.assertTrue(compare < 0);
    }
}
