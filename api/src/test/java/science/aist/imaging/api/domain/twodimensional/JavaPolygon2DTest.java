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

import java.util.Arrays;
import java.util.List;

/**
 * @author Christoph Praschl
 */
public class JavaPolygon2DTest {
    @Test
    void testIsInConvexHull() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(3, 0), new JavaPoint2D(3, 3), new JavaPoint2D(0, 3));

        // when
        boolean b = polygon.isInConvexHull(new JavaPoint2D(2, 2));

        // then
        Assert.assertTrue(b);
    }

    @Test
    void testIsInConvexHull2() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(3, 0), new JavaPoint2D(3, 3), new JavaPoint2D(0, 3));

        // when
        boolean b = polygon.isInConvexHull(new JavaPoint2D(0, 0));

        // then
        Assert.assertTrue(b);
    }

    @Test
    void testIsInConvexHull3() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(3, 0), new JavaPoint2D(3, 3), new JavaPoint2D(0, 3));

        // when
        boolean b = polygon.isInConvexHull(new JavaPoint2D(-1, 0));

        // then
        Assert.assertFalse(b);
    }

    @Test
    void testIsInConvexHull4() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(2, 1), new JavaPoint2D(4, 4), new JavaPoint2D(4, 1), new JavaPoint2D(3, 1), new JavaPoint2D(3, 0));

        // when
        boolean b = polygon.isInConvexHull(new JavaPoint2D(3, 2));

        // then
        Assert.assertTrue(b);
    }

    @Test
    void testIsInConvexHull5() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(2, 0), new JavaPoint2D(2, 2), new JavaPoint2D(0, 2));

        // when
        boolean b = polygon.isInConvexHull(new JavaPoint2D(2, 1), true);

        // then
        Assert.assertTrue(b);
    }

    @Test
    void testIsInConvexHull6() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(2, 0), new JavaPoint2D(2, 2), new JavaPoint2D(0, 2));

        // when
        boolean b = polygon.isInConvexHull(new JavaPoint2D(2, 1), false);

        // then
        Assert.assertFalse(b);
    }

    @Test
    void testGetCentroid() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(1, 2), new JavaPoint2D(2, 0));

        // when
        JavaPoint2D jp = polygon.getCentroid();

        // then
        Assert.assertEquals(jp.getX(), 1.0);
        Assert.assertTrue(1 - jp.getY() < 0.5);
    }

    @Test
    void testGetCentroid2() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 2),
                new JavaPoint2D(2, 2),
                new JavaPoint2D(2, 0));

        // when
        JavaPoint2D jp = polygon.getCentroid();

        // then
        Assert.assertEquals(jp.getX(), 1.0);
        Assert.assertEquals(jp.getY(), 1.0);
    }

    @Test
    void testGetWidth1() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 2),
                new JavaPoint2D(2, 2),
                new JavaPoint2D(2, 0));

        // when
        double jp = polygon.getWidth();

        // then
        Assert.assertEquals(jp, 2.0);
    }

    @Test
    void testGetWidth2() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 4),
                new JavaPoint2D(8, 2),
                new JavaPoint2D(7, 0),
                new JavaPoint2D(9, 3),
                new JavaPoint2D(-1, 8));

        // when
        double jp = polygon.getWidth();

        // then
        Assert.assertEquals(jp, 10.0);
    }

    @Test
    void testGetWidth3() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 4),
                new JavaPoint2D(0, 2));

        // when
        double jp = polygon.getWidth();

        // then
        Assert.assertEquals(jp, 0.0);
    }

    @Test
    void testGetHeight1() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 2),
                new JavaPoint2D(2, 2),
                new JavaPoint2D(2, 0));

        // when
        double jp = polygon.getHeight();

        // then
        Assert.assertEquals(jp, 2.0);
    }

    @Test
    void testGetHeight2() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 4),
                new JavaPoint2D(8, 2),
                new JavaPoint2D(7, 0),
                new JavaPoint2D(9, 3),
                new JavaPoint2D(-1, 8));

        // when
        double jp = polygon.getHeight();

        // then
        Assert.assertEquals(jp, 8.0);
    }

    @Test
    void testGetHeight3() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(0, 0),
                new JavaPoint2D(2, 0),
                new JavaPoint2D(3, 0));

        // when
        double jp = polygon.getHeight();

        // then
        Assert.assertEquals(jp, 0.0);
    }

    @Test
    void testGetArea() {
        // given
        JavaPolygon2D polygon = new JavaPolygon2D(new JavaPoint2D(4, 10),
                new JavaPoint2D(9, 7),
                new JavaPoint2D(11, 2),
                new JavaPoint2D(2, 2));

        // when
        double area = polygon.getArea();

        // then
        Assert.assertEquals(area, 45.5, 0.01);
    }

    @Test
    void testGetPolygonFromUnsortedPointCloud() {
        // given

        // when
        JavaPolygon2D polygon = JavaPolygon2D.getPolygonFromUnsortedPointCloud(new JavaPoint2D(4, 10),
                new JavaPoint2D(11, 2),
                new JavaPoint2D(2, 2),
                new JavaPoint2D(9, 7)
        );

        // then
        Assert.assertEquals(polygon.getArea(), 45.5, 0.01);
    }

    @Test
    void testSort() {
        // given
        JavaPolygon2D unsorted = new JavaPolygon2D(new JavaPoint2D(4, 10),
                new JavaPoint2D(11, 2),
                new JavaPoint2D(9, 7),
                new JavaPoint2D(2, 2));

        // when
        JavaPolygon2D sorted = unsorted.sort();

        // then
        Assert.assertTrue(sorted.isSorted());
    }

    @Test
    void testIsSorted1() {
        // given
        JavaPolygon2D unsorted = new JavaPolygon2D(new JavaPoint2D(4, 10),
                new JavaPoint2D(11, 2),
                new JavaPoint2D(9, 7),
                new JavaPoint2D(2, 2));

        // when
        boolean sorted = unsorted.isSorted();

        // then
        Assert.assertFalse(sorted);
    }

    @Test
    void testIsSorted2() {
        // given
        JavaPolygon2D sortedJP = new JavaPolygon2D(new JavaPoint2D(2, 2),
                new JavaPoint2D(11, 2),
                new JavaPoint2D(9, 7),
                new JavaPoint2D(4, 10));

        // when
        boolean sorted = sortedJP.isSorted();

        // then
        Assert.assertTrue(sorted);
    }

    @Test
    void testGetIntersection() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D();
        JavaPolygon2D jp2 = new JavaPolygon2D();

        // when
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(jp1, jp2);

        // then
        Assert.assertTrue(intersection.isEmpty());
    }

    @Test
    void testGetIntersection2() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D(new JavaPoint2D(1, 1), new JavaPoint2D(3, 3), new JavaPoint2D(2, 2));
        JavaPolygon2D jp2 = new JavaPolygon2D(new JavaPoint2D(0, 2), new JavaPoint2D(0, 0), new JavaPoint2D(2, 0), new JavaPoint2D(2, 2));

        // when
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(jp1, jp2);

        // then
        Assert.assertTrue(intersection.isEmpty());
    }

    @Test
    void testGetIntersection3() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D(new JavaPoint2D(1, 3), new JavaPoint2D(1, 1), new JavaPoint2D(3, 1), new JavaPoint2D(3, 3));
        JavaPolygon2D jp2 = new JavaPolygon2D(new JavaPoint2D(0, 2), new JavaPoint2D(0, 0), new JavaPoint2D(2, 0), new JavaPoint2D(2, 2));

        // when
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(jp1, jp2);

        // then
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(1, 1)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(2, 1)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(2, 2)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(1, 2)));
        Assert.assertEquals(intersection.getPoints().size(), 4);
    }

    @Test
    void testGetIntersection4() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D(new JavaPoint2D(0, 2), new JavaPoint2D(0, 0), new JavaPoint2D(2, 0), new JavaPoint2D(2, 2));

        // when
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(jp1, jp1);

        // then
        Assert.assertEquals(intersection.getSize(), 4);
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(0, 2)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(0, 0)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(2, 0)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(2, 2)));
    }

    @Test
    void testGetIntersection5() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D(new JavaPoint2D(0, 2), new JavaPoint2D(0, 0), new JavaPoint2D(2, 0), new JavaPoint2D(2, 2));
        JavaPolygon2D jp2 = new JavaPolygon2D(new JavaPoint2D(1, 2), new JavaPoint2D(2, 1), new JavaPoint2D(3, 2), new JavaPoint2D(2, 3));

        // when
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(jp1, jp2);

        // then
        Assert.assertEquals(intersection.getSize(), 3);
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(1, 2)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(2, 1)));
        Assert.assertTrue(intersection.getPoints().contains(new JavaPoint2D(2, 2)));
    }

    @Test
    void testGetIntersection6() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D(new JavaPoint2D(10, 10), new JavaPoint2D(9, 10), new JavaPoint2D(9, 9), new JavaPoint2D(9, 10));
        JavaPolygon2D jp2 = new JavaPolygon2D(new JavaPoint2D(1, 2), new JavaPoint2D(2, 1), new JavaPoint2D(3, 2), new JavaPoint2D(2, 3));

        // when
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(jp1, jp2);

        // then
        Assert.assertTrue(intersection.isEmpty());
    }

    @Test
    void testGetPathSegment() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(new JavaPoint2D(1, 3), new JavaPoint2D(1, 1), new JavaPoint2D(3, 1));

        // when
        List<JavaLine2D> pathSegments = jp.getContour();

        // then
        Assert.assertEquals(pathSegments.size(), 3);
        Assert.assertTrue(pathSegments.contains(new JavaLine2D(new JavaPoint2D(1, 3), new JavaPoint2D(1, 1))));
        Assert.assertTrue(pathSegments.contains(new JavaLine2D(new JavaPoint2D(1, 1), new JavaPoint2D(3, 1))));
        Assert.assertTrue(pathSegments.contains(new JavaLine2D(new JavaPoint2D(3, 1), new JavaPoint2D(1, 3))));
    }

    @Test
    void testGetPathSegment2() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D();

        // when
        List<JavaLine2D> pathSegments = jp.getContour();

        // then
        Assert.assertTrue(pathSegments.isEmpty());
    }

    @Test
    void testGetBoundingBoxNull() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D();

        // when
        JavaRectangle2D boundingBox = jp.getBoundingBox();

        // then
        Assert.assertNull(boundingBox);
    }

    @Test
    void testGetBoundingBox1() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(5, 0),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(10, 10)
        );

        // when
        JavaRectangle2D boundingBox = jp.getBoundingBox();

        // then
        Assert.assertNotNull(boundingBox);
        Assert.assertEquals(boundingBox.getTopLeft(), new JavaPoint2D(0, 0));
        Assert.assertEquals(boundingBox.getBottomRight(), new JavaPoint2D(10, 10));
    }

    @Test
    void testGetBoundingBox2() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(5, 0),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(10, 10),
                new JavaPoint2D(5, 20)
        );

        // when
        JavaRectangle2D boundingBox = jp.getBoundingBox();

        // then
        Assert.assertNotNull(boundingBox);
        Assert.assertEquals(boundingBox.getTopLeft(), new JavaPoint2D(0, 0));
        Assert.assertEquals(boundingBox.getBottomRight(), new JavaPoint2D(10, 20));
    }

    @Test
    void testGetAreaPointsEmpty() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D();

        // when
        List<JavaPoint2D> areaPoints = jp.getAreaPoints();

        // then
        Assert.assertNotNull(areaPoints);
        Assert.assertTrue(areaPoints.isEmpty());
    }

    @Test
    void testGetAreaPoints1() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(5, 0),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(10, 10)
        );

        // when
        List<JavaPoint2D> areaPoints = jp.getAreaPoints();

        // then
        Assert.assertEquals(areaPoints.size(), 50);
    }

    @Test
    void testGetAreaPoints2() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(5, 0),
                new JavaPoint2D(10, 10),
                new JavaPoint2D(5, 20),
                new JavaPoint2D(0, 10)
        );

        // when
        List<JavaPoint2D> areaPoints = jp.getAreaPoints();

        // then
        Assert.assertEquals(areaPoints.size(), 109);
    }

    @Test
    public void testIsConvex() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(10, 10),
                new JavaPoint2D(10, 0)
        );

        // when
        boolean convex = jp.isConvex();

        // then
        Assert.assertTrue(convex);
    }

    @Test
    public void testIsConvex2() {
        // given
        JavaPolygon2D jp = new JavaPolygon2D(
                new JavaPoint2D(0, 0),
                new JavaPoint2D(0, 10),
                new JavaPoint2D(10, 10),
                new JavaPoint2D(10, 0),
                new JavaPoint2D(5, 5)
        );

        // when
        boolean convex = jp.isConvex();

        // then
        Assert.assertFalse(convex);
    }

    @Test
    public void testEquals() {
        // given
        List<JavaPoint2D> points = Arrays.asList(new JavaPoint2D(5, 2), new JavaPoint2D(1, 8), new JavaPoint2D(0, 6));
        JavaPolygon2D jp1 = new JavaPolygon2D(points);
        JavaPolygon2D jp2 = new JavaPolygon2D(points);

        // when
        boolean equals = jp1.equals(jp2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testEquals2() {
        // given
        JavaPolygon2D jp1 = new JavaPolygon2D(Arrays.asList(new JavaPoint2D(5, 2), new JavaPoint2D(1, 8), new JavaPoint2D(0, 6)));
        JavaPolygon2D jp2 = new JavaPolygon2D(Arrays.asList(new JavaPoint2D(5, 2), new JavaPoint2D(1, 5), new JavaPoint2D(0, 6)));

        // when
        boolean equals = jp1.equals(jp2);

        // then
        Assert.assertFalse(equals);
    }
}
