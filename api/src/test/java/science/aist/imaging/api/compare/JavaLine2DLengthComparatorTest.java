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
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;

/**
 * @author Christoph Praschl
 */
public class JavaLine2DLengthComparatorTest {
    @Test
    public void testCompareTest1() {
        // given
        JavaLineLengthComparator comparator = new JavaLineLengthComparator();

        JavaLine2D j1 = new JavaLine2D(0, 0, 5, 0);
        JavaLine2D j2 = new JavaLine2D(0, 0, 2, 0);

        // when
        int compare = comparator.compare(j1, j2);

        // then
        Assert.assertTrue(compare > 0);
    }

    @Test
    public void testCompareTest2() {
        // given
        JavaLineLengthComparator comparator = new JavaLineLengthComparator();

        JavaLine2D j1 = new JavaLine2D(0, 0, 5, 0);
        JavaLine2D j2 = new JavaLine2D(5, 0, 10, 0);

        // when
        int compare = comparator.compare(j1, j2);

        // then
        Assert.assertEquals(compare, 0);
    }

    @Test
    public void testCompareTest3() {
        // given
        JavaLineLengthComparator comparator = new JavaLineLengthComparator();

        JavaLine2D j1 = new JavaLine2D(0, 0, 3, 0);
        JavaLine2D j2 = new JavaLine2D(0, 0, 8, 0);

        // when
        int compare = comparator.compare(j1, j2);

        // then
        Assert.assertTrue(compare < 0);
    }
}
