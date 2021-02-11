/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing.convexhull;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Christoph Praschl
 */
public class GrahamConvexHullTest {
    @Test
    public void testGetConvexHull() {
        // given
        List<JavaPoint2D> points = Arrays.asList(new JavaPoint2D(0, 0), new JavaPoint2D(10, 0), new JavaPoint2D(0, 10), new JavaPoint2D(
                0, 10));
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        List<JavaPoint2D> convexHull = hull.apply(points);

        // then
        Assert.assertEquals(convexHull.size(), 4);
    }

    @Test
    public void testGetConvexHull2() {
        // given
        List<JavaPoint2D> points = Arrays.asList(new JavaPoint2D(0, 0),
                new JavaPoint2D(10, 0),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(0, 0),
                new JavaPoint2D(4, 5),
                new JavaPoint2D(9.9, 9),
                new JavaPoint2D(1, 1),
                new JavaPoint2D(1, 8));
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        List<JavaPoint2D> convexHull = hull.apply(points);

        // then
        Assert.assertEquals(convexHull.size(), 5);
    }

    /*
        6 |       d
        5 |     b   g
        4 |   a   e   i
        3 |     c   h
        2 |       f
        1 |
        0 '------------
          0 1 2 3 4 5 6
    */
    @Test
    public void testGetConvexHull3() {
        // given

        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(3, 5);
        JavaPoint2D c = new JavaPoint2D(3, 3);
        JavaPoint2D d = new JavaPoint2D(4, 6);
        JavaPoint2D e = new JavaPoint2D(4, 4);
        JavaPoint2D f = new JavaPoint2D(4, 2);
        JavaPoint2D g = new JavaPoint2D(5, 5);
        JavaPoint2D h = new JavaPoint2D(5, 3);
        JavaPoint2D i = new JavaPoint2D(6, 4);
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        List<JavaPoint2D> convexHull = hull.apply(Arrays.asList(a, b, c, d, e, f, g, h, i));

        // then
        Assert.assertEquals(convexHull.size(), 5);
        Assert.assertTrue(convexHull.contains(f));
        Assert.assertTrue(convexHull.contains(i));
        Assert.assertTrue(convexHull.contains(d));
        Assert.assertTrue(convexHull.contains(a));
    }

    /*
      6       |       d   m
      5       |     b   g
      4       |   a   e   i
      3 j     |     c   h
      2       |       f
      1       |
      0       '------------
     -1
     -2                   k
     -3
        -2 -1 0 1 2 3 4 5 6
  */
    @Test
    public void testGetConvexHull4() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(3, 5);
        JavaPoint2D c = new JavaPoint2D(3, 3);
        JavaPoint2D d = new JavaPoint2D(4, 6);
        JavaPoint2D e = new JavaPoint2D(4, 4);
        JavaPoint2D f = new JavaPoint2D(4, 2);
        JavaPoint2D g = new JavaPoint2D(5, 5);
        JavaPoint2D h = new JavaPoint2D(5, 3);
        JavaPoint2D i = new JavaPoint2D(6, 4);
        JavaPoint2D j = new JavaPoint2D(-2, 3);
        JavaPoint2D k = new JavaPoint2D(6, -2);
        JavaPoint2D m = new JavaPoint2D(6, 6);

        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        List<JavaPoint2D> convexHull = hull.apply(Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, m));

        // then
        Assert.assertEquals(convexHull.size(), 5);
        Assert.assertTrue(convexHull.contains(k));
        Assert.assertTrue(convexHull.contains(m));
        Assert.assertTrue(convexHull.contains(d));
        Assert.assertTrue(convexHull.contains(j));
    }

    /*
      large   |                         m
      .       |
      .       |
      7  j    |
      6       |       d
      5       |     b   g
      4       |   a   e   i
      3       |     c   h
      2       |       f
      1       |
      0       '--------------------------
     -1
     -2                       k
     -3
        -2 -1 0 1 2 3 4 5 6 7 8 . . large
  */
    @Test
    public void testGetConvexHull5() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(3, 5);
        JavaPoint2D c = new JavaPoint2D(3, 3);
        JavaPoint2D d = new JavaPoint2D(4, 6);
        JavaPoint2D e = new JavaPoint2D(4, 4);
        JavaPoint2D f = new JavaPoint2D(4, 2);
        JavaPoint2D g = new JavaPoint2D(5, 5);
        JavaPoint2D h = new JavaPoint2D(5, 3);
        JavaPoint2D i = new JavaPoint2D(6, 4);
        JavaPoint2D j = new JavaPoint2D(-2, 7);
        JavaPoint2D k = new JavaPoint2D(8, -2);
        JavaPoint2D m = new JavaPoint2D(Integer.MAX_VALUE, Integer.MAX_VALUE);

        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        List<JavaPoint2D> convexHull = hull.apply(Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, m));

        // then
        Assert.assertEquals(convexHull.size(), 4);
        Assert.assertTrue(convexHull.contains(k));
        Assert.assertTrue(convexHull.contains(m));
        Assert.assertTrue(convexHull.contains(j));
    }

    /*
      6 |
      5 |
      4 |   a
      3 |     c
      2 |       f
      1 |
      0 '------------
        0 1 2 3 4 5 6
  */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetConvexHullFail() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(3, 3);
        JavaPoint2D c = new JavaPoint2D(4, 2);
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        hull.apply(Arrays.asList(a, b, c));

        // then
        // Exception
    }

    /*
       6 |       d   b
       5 |         f
       4 |   a   e
       3 |     c
       2 |
       1 |
       0 '------------
         0 1 2 3 4 5 6
   */
    @Test
    public void testAreAllCollinear() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(6, 6);
        JavaPoint2D c = new JavaPoint2D(3, 3);
        JavaPoint2D d = new JavaPoint2D(4, 6);
        JavaPoint2D e = new JavaPoint2D(4, 4);
        JavaPoint2D f = new JavaPoint2D(5, 5);

        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        boolean b1 = hull.areAllCollinear(Collections.singletonList(c));
        boolean b2 = hull.areAllCollinear(Arrays.asList(c, e));
        boolean b3 = hull.areAllCollinear(Arrays.asList(c, e, f));
        boolean b4 = hull.areAllCollinear(Arrays.asList(c, b, e, e, e, f, c));
        boolean b5 = hull.areAllCollinear(Arrays.asList(a, b, d));

        // then
        Assert.assertTrue(b1);
        Assert.assertTrue(b2);
        Assert.assertTrue(b3);
        Assert.assertTrue(b4);
        Assert.assertFalse(b5);
    }

    /*
      6    |       d
      5    |     b   g
      4    |   a   e   i
      3    |     c   h
      2    |       f
      1    |
      0    '------------
     -1
     -2
        -1 0 1 2 3 4 5 6
  */
    @Test
    public void testGetLowestPoint() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(3, 5);
        JavaPoint2D c = new JavaPoint2D(3, 3);
        JavaPoint2D d = new JavaPoint2D(4, 6);
        JavaPoint2D e = new JavaPoint2D(4, 4);
        JavaPoint2D f = new JavaPoint2D(4, 2);
        JavaPoint2D g = new JavaPoint2D(5, 5);
        JavaPoint2D h = new JavaPoint2D(5, 3);
        JavaPoint2D i = new JavaPoint2D(6, 4);
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        JavaPoint2D lowest = hull.getLowestPoint(Arrays.asList(a, b, c, d, e, f, g, h, i));

        // then
        Assert.assertEquals(lowest, f);
    }

    /*
      6    |       d
      5    |     b   g
      4    |   a   e   i
      3    |     c   h
      2    |       f
      1    |
      0    '------------
     -1  j             k
     -2
        -1 0 1 2 3 4 5 6
  */
    @Test
    public void testGetLowestPoint2() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 4);
        JavaPoint2D b = new JavaPoint2D(3, 5);
        JavaPoint2D c = new JavaPoint2D(3, 3);
        JavaPoint2D d = new JavaPoint2D(4, 6);
        JavaPoint2D e = new JavaPoint2D(4, 4);
        JavaPoint2D f = new JavaPoint2D(4, 2);
        JavaPoint2D g = new JavaPoint2D(5, 5);
        JavaPoint2D h = new JavaPoint2D(5, 3);
        JavaPoint2D i = new JavaPoint2D(6, 4);
        JavaPoint2D j = new JavaPoint2D(-1, -1);
        JavaPoint2D k = new JavaPoint2D(6, -1);
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        JavaPoint2D lowest = hull.getLowestPoint(Arrays.asList(a, b, c, d, e, f, g, h, i, j, k));

        // then
        Assert.assertEquals(lowest, j);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetPointsFail1() {
        // given
        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        hull.apply(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4});

        // then
        // exception
    }

    /*
        6    |
        5    |
        4    |
        3    |
        2    g   a
        1    f b
        0    c-e-d--------
       -1
       -2
          -1 0 1 2 3 4 5 6
    */
    @Test
    public void testGetSortedPointSet() {
        // given
        JavaPoint2D a = new JavaPoint2D(2, 2);
        JavaPoint2D b = new JavaPoint2D(1, 1);
        JavaPoint2D c = new JavaPoint2D(0, 0);
        JavaPoint2D d = new JavaPoint2D(2, 0);
        JavaPoint2D e = new JavaPoint2D(1, 0);
        JavaPoint2D f = new JavaPoint2D(0, 1);
        JavaPoint2D g = new JavaPoint2D(0, 2);
        JavaPoint2D h = new JavaPoint2D(2, 2); // duplicate
        JavaPoint2D i = new JavaPoint2D(2, 2); // duplicate

        List<JavaPoint2D> points = Arrays.asList(a, b, c, d, e, f, g, h, i);

        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        Set<JavaPoint2D> set = hull.getSortedPointSet(points);

        // then
        JavaPoint2D[] array = set.toArray(new JavaPoint2D[0]);
        Assert.assertEquals(set.size(), 7);

        Assert.assertEquals(array[0], c);
        Assert.assertEquals(array[1], e);
        Assert.assertEquals(array[2], d);
        Assert.assertEquals(array[3], b);
        Assert.assertEquals(array[4], a);
        Assert.assertEquals(array[5], f);
        Assert.assertEquals(array[6], g);
    }

    /*
      9       |             d
      8       |               c
      7       |                 e
      6       |
      5       |
      4       |       b
      3       |
      2       |
      1   h   | a
      0     g '------------------
     -1       f
     -2
        -2 -1 0 1 2 3 4 5 6 7 8 9
  */
    @Test
    public void testGetTurn() {
        // given
        JavaPoint2D a = new JavaPoint2D(1, 1);
        JavaPoint2D b = new JavaPoint2D(4, 4);
        JavaPoint2D c = new JavaPoint2D(8, 8);
        JavaPoint2D d = new JavaPoint2D(7, 9);
        JavaPoint2D e = new JavaPoint2D(9, 7);
        JavaPoint2D f = new JavaPoint2D(0, -1);
        JavaPoint2D g = new JavaPoint2D(-1, 0);
        JavaPoint2D h = new JavaPoint2D(-2, 1);

        GrahamConvexHull hull = new GrahamConvexHull();

        // when
        Turn t00 = hull.getTurn(a, b, c);
        Turn t01 = hull.getTurn(a, c, b);
        Turn t02 = hull.getTurn(b, a, c);
        Turn t03 = hull.getTurn(c, b, a);
        Turn t04 = hull.getTurn(e, d, c);
        Turn t05 = hull.getTurn(h, f, g);
        Turn t06 = hull.getTurn(a, b, e);
        Turn t07 = hull.getTurn(a, b, f);
        Turn t08 = hull.getTurn(a, c, e);
        Turn t09 = hull.getTurn(a, c, f);
        Turn t10 = hull.getTurn(c, b, g);
        Turn t11 = hull.getTurn(d, b, f);
        Turn t12 = hull.getTurn(a, b, d);
        Turn t13 = hull.getTurn(a, e, d);
        Turn t14 = hull.getTurn(e, c, f);
        Turn t15 = hull.getTurn(b, d, a);
        Turn t16 = hull.getTurn(a, g, f);
        Turn t17 = hull.getTurn(f, b, a);


        // then
        Assert.assertEquals(t00, Turn.COLLINEAR);
        Assert.assertEquals(t01, Turn.COLLINEAR);
        Assert.assertEquals(t02, Turn.COLLINEAR);
        Assert.assertEquals(t03, Turn.COLLINEAR);
        Assert.assertEquals(t04, Turn.COLLINEAR);
        Assert.assertEquals(t05, Turn.COLLINEAR);
        Assert.assertEquals(t06, Turn.CLOCKWISE);
        Assert.assertEquals(t07, Turn.CLOCKWISE);
        Assert.assertEquals(t08, Turn.CLOCKWISE);
        Assert.assertEquals(t09, Turn.CLOCKWISE);
        Assert.assertEquals(t10, Turn.CLOCKWISE);
        Assert.assertEquals(t11, Turn.CLOCKWISE);
        Assert.assertEquals(t12, Turn.COUNTER_CLOCKWISE);
        Assert.assertEquals(t13, Turn.COUNTER_CLOCKWISE);
        Assert.assertEquals(t14, Turn.COUNTER_CLOCKWISE);
        Assert.assertEquals(t15, Turn.COUNTER_CLOCKWISE);
        Assert.assertEquals(t16, Turn.COUNTER_CLOCKWISE);
        Assert.assertEquals(t17, Turn.COUNTER_CLOCKWISE);
    }

}
