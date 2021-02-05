/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import science.aist.imaging.api.domain.threedimensional.JavaLine3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.jack.data.Pair;
import science.aist.jack.math.MathUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Andreas Pointner
 */
public class JavaLine2DTest {
    /**
     * Tests JavaLine.getIntersectionPoint function
     */
    @Test
    void testGetIntersectionPoint() {
        // given
        JavaLine2D line1 = new JavaLine2D(1, 1, 5, 1);
        JavaLine2D line2 = new JavaLine2D(3, 0, 3, 3);

        // when
        JavaPoint2D jp = JavaLine2D.getIntersectionPoint(line1, line2);

        // then
        Assert.assertEquals(jp, new JavaPoint2D(3, 1));
    }

    /**
     * Tests JavaLine.createLine function
     */
    @Test
    void testCreateLine() {
        // given

        // when
        JavaLine2D j = JavaLine2D.createLine(new JavaPoint2D(0, 0), 45, 4);

        // then
        Assert.assertEquals(Math.abs(j.getStartPoint().getX()), Math.abs(j.getEndPoint().getX()));
        Assert.assertEquals(Math.abs(j.getStartPoint().getY()), Math.abs(j.getEndPoint().getY()));
    }

    /**
     * Tests JavaLine.cutAngle function
     */
    @Test
    void testCutAngle1() {
        // given
        JavaLine2D jl1 = new JavaLine2D(1, 1, 8, 2);
        JavaLine2D jl2 = new JavaLine2D(7, 0, 6, 10);

        // when
        double res = Math.toDegrees(JavaLine2D.cutAngle(jl1, jl2));

        // then
        Assert.assertEquals(res, 76.534, 0.001);
    }

    /**
     * Tests JavaLine.cutAngle function with gradient nearly unlimited
     */
    @Test
    void testCutAngle2() {
        // given
        JavaLine2D jl1 = new JavaLine2D(1, 1, 8, 2);
        JavaLine2D jl2 = new JavaLine2D(7, 0, 7, 10);

        // when
        double res = Math.toDegrees(JavaLine2D.cutAngle(jl1, jl2));

        // then
        Assert.assertEquals(res, 81.869, 0.001);
    }

    /**
     * Tests JavaLine2D.cutAngle function when the lines cut angle is 90°
     */
    @Test
    void testCutAngle3() {
        // given
        JavaLine2D jl1 = new JavaLine2D(2, 4, 4, 6);
        JavaLine2D jl2 = new JavaLine2D(4, 4, 2, 6);

        // when
        double res = Math.toDegrees(JavaLine2D.cutAngle(jl1, jl2));

        // then
        Assert.assertEquals(res, 90, 0.001);
    }

    /**
     * Tests JavaLine2D.getGradient function
     */
    @Test
    void testGetGradient() {
        // given
        JavaLine2D jl = new JavaLine2D(7, 0, 6, 10);

        // when
        double gradient = jl.getGradient();

        // then
        Assert.assertEquals(gradient, -10.0);
    }

    /**
     * Tests JavaLine2D.getGradient function when gradient is unlimited so startPoint.x == endPoint.x
     */
    @Test
    void testGetGradientUnlimited() {
        // given
        JavaLine2D jl = new JavaLine2D(7, 0, 7, 10);

        // when
        double gradient = jl.getGradient();

        // then
        Assert.assertEquals(gradient, Double.MAX_VALUE);
    }

    @Test
    void testLineLength() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(3, 3);
        JavaLine2D l = new JavaLine2D(jp1, jp2);

        // when
        double length = l.length();

        // then
        Assert.assertEquals(length, Math.sqrt(8), 0.0001);
    }

    @Test
    void testLineRotation1() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(3, 3);
        JavaLine2D l = new JavaLine2D(jp1, jp2);

        // when
        double rotation = l.getRotation();

        // then
        Assert.assertEquals(rotation, Math.PI / 4 /*45°*/, 0.0001);
    }

    @Test
    void testLineRotation2() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(1, 2);
        JavaLine2D l = new JavaLine2D(jp1, jp2);

        // when
        double rotation = l.getRotation();

        // then
        Assert.assertEquals(rotation, Math.PI / 2 /*45°*/, 0.0001);
    }

    @Test
    void testLineRotation3() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(2, 1);
        JavaLine2D l = new JavaLine2D(jp1, jp2);

        // when
        double rotation = l.getRotation();

        // then
        Assert.assertEquals(rotation, 0 /*0°*/, 0.0001);
    }

    @Test
    void testGetCenterPoint() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(1, 1);
        JavaPoint2D jp2 = new JavaPoint2D(3, 3);
        JavaLine2D l = new JavaLine2D(jp1, jp2);

        // when
        JavaPoint2D center = l.getCenterPoint();

        // then
        Assert.assertEquals(center, new JavaPoint2D(2, 2));
    }

    @Test
    void testBresenham() {
        // given
        JavaLine2D l = new JavaLine2D(10, 10, 12, 12);

        // when
        List<JavaPoint2D> bresenham = l.getBresenham();

        // then
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(10, 10)));
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(11, 11)));
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(12, 12)));
    }

    @Test
    void testBresenham2() {
        // given
        JavaLine2D l = new JavaLine2D(10, 10, 11, 14);

        // when
        List<JavaPoint2D> bresenham = l.getBresenham();

        // then
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(10, 10)));
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(10, 11)));
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(10, 12)));
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(11, 13)));
        Assert.assertTrue(bresenham.contains(new JavaPoint2D(11, 14)));
    }

    @Test
    void testCalculateSquareAroundLine() {
        // given
        JavaLine2D l = new JavaLine2D(10, 10, 12, 12);

        // when
        JavaPolygon2D rectangle = l.calculateSquareAroundLine(10);

        // then
        Assert.assertTrue(rectangle.getPoints().contains(new JavaPoint2D(10, 0)));
        Assert.assertTrue(rectangle.getPoints().contains(new JavaPoint2D(0, 10)));
        Assert.assertTrue(rectangle.getPoints().contains(new JavaPoint2D(12, 22)));
        Assert.assertTrue(rectangle.getPoints().contains(new JavaPoint2D(22, 12)));
    }

    @Test
    void testMoveLine() {
        // given
        JavaLine2D l = new JavaLine2D(0, 0, 0, 5);

        // when
        JavaLine2D res = l.move(5);

        // then
        Assert.assertEquals(res.getStartPoint(), new JavaPoint2D(5, 0));
        Assert.assertEquals(res.getEndPoint(), new JavaPoint2D(5, 5));
    }

    @Test
    void testMoveLine2() {
        // given
        JavaLine2D l = new JavaLine2D(0, 0, 5, 5);

        // when
        JavaLine2D res = l.move(Math.sqrt(2));

        // then
        Assert.assertEquals(res.getStartPoint(), new JavaPoint2D(1, -1));
        Assert.assertEquals(res.getEndPoint(), new JavaPoint2D(6, 4));
    }

    @Test
    void testMoveLine3() {
        // given
        JavaLine2D l = new JavaLine2D(1, 3, 3, 5);

        // when
        JavaLine2D res = l.move(Math.sqrt(8));

        // then
        Assert.assertEquals(res.getStartPoint(), new JavaPoint2D(3, 1));
        Assert.assertEquals(res.getEndPoint(), new JavaPoint2D(5, 3));
    }

    @Test
    void testPointOnLine() {
        // given
        JavaLine2D l = new JavaLine2D(0, 0, 2, 2);
        JavaPoint2D p = new JavaPoint2D(1, 1);

        // when
        boolean pointOnLine = l.isPointOnLine(p);

        // then
        Assert.assertTrue(pointOnLine);
    }

    @Test
    void testPointOnLine2() {
        // given
        JavaLine2D l = new JavaLine2D(0, 0, 2, 2);
        JavaPoint2D p = new JavaPoint2D(1, 3);

        // when
        boolean pointOnLine = l.isPointOnLine(p);

        // then
        Assert.assertFalse(pointOnLine);
    }

    @Test
    void testPointOnLine3() {
        // given
        JavaLine2D l = new JavaLine2D(0, 0, 20, 0);
        JavaPoint2D p = new JavaPoint2D(1, 0);

        // when
        boolean pointOnLine = l.isPointOnLine(p);

        // then
        Assert.assertTrue(pointOnLine);
    }

    @Test
    void testCreateByCenterRotationAndLength1() {
        // given
        JavaPoint2D center = new JavaPoint2D(5, 5);
        double rotation = 45;
        double length = 10;

        // when
        JavaLine2D jl = JavaLine2D.createByCenterRotationAndLength(center, Math.toRadians(rotation), length);

        // then
        Assert.assertEquals(Math.toDegrees(jl.getRotation()), rotation, 0.01);
        Assert.assertEquals(jl.length(), length, 0.01);
        Assert.assertEquals(jl.getCenterPoint(), center);
    }

    @Test
    void testCreateByCenterRotationAndLength2() {
        // given
        JavaPoint2D center = new JavaPoint2D(18, 13);
        double rotation = 27;
        double length = 5.5;

        // when
        JavaLine2D jl = JavaLine2D.createByCenterRotationAndLength(center, Math.toRadians(rotation), length);

        // then
        Assert.assertEquals(Math.toDegrees(jl.getRotation()), rotation, 0.01);
        Assert.assertEquals(jl.length(), length, 0.01);
        Assert.assertEquals(jl.getCenterPoint(), center);
    }

    @Test
    void testCreateByCenterRotationAndLength3() {
        // given
        JavaPoint2D center = new JavaPoint2D(1, 1);
        double rotation = 45;
        double length = 2 * Math.sqrt(2);

        // when
        JavaLine2D jl = JavaLine2D.createByCenterRotationAndLength(center, Math.toRadians(rotation), length);

        // then
        Assert.assertEquals(jl.getStartPoint(), new JavaPoint2D(0, 0));
        Assert.assertEquals(jl.getEndPoint(), new JavaPoint2D(2, 2));
    }

    @Test
    void testSplit1() {
        // given
        JavaLine2D javaLine = new JavaLine2D(0, 0, 10, 0);

        // when
        Optional<Pair<JavaLine2D, JavaLine2D>> split = javaLine.split(new JavaPoint2D(5, 0));

        // then
        Assert.assertTrue(split.isPresent());
        Assert.assertEquals(split.get().getFirst(), new JavaLine2D(0, 0, 5, 0));
        Assert.assertEquals(split.get().getSecond(), new JavaLine2D(5, 0, 10, 0));
    }

    @Test
    void testSplit2() {
        // given
        JavaLine2D javaLine = new JavaLine2D(0, 0, 8, 16);
        JavaLine2D javaLine2 = new JavaLine2D(0, 16, 8, 0);
        JavaPoint2D intersectionPoint = JavaLine2D.getIntersectionPoint(javaLine, javaLine2);

        // when
        Optional<Pair<JavaLine2D, JavaLine2D>> split = javaLine.split(intersectionPoint);

        // then
        Assert.assertTrue(split.isPresent());
        Assert.assertEquals(split.get().getFirst().getStartPoint(), new JavaPoint2D(0, 0));
        Assert.assertEquals(split.get().getFirst().getEndPoint(), intersectionPoint);
        Assert.assertEquals(split.get().getSecond().getStartPoint(), intersectionPoint);
        Assert.assertEquals(split.get().getSecond().getEndPoint(), new JavaPoint2D(8, 16));
    }

    @Test
    void testSplit3() {
        // given
        JavaLine2D javaLine = new JavaLine2D(0, 0, 8, 16);

        // when
        Optional<Pair<JavaLine2D, JavaLine2D>> split = javaLine.split(new JavaPoint2D(10, 10));

        // then
        Assert.assertFalse(split.isPresent());
    }

    @Test
    void testSplit4() {
        // given
        JavaLine2D javaLine = new JavaLine2D(0, 0, 10, 0);

        // when
        Set<JavaLine2D> split = javaLine.split(new JavaPoint2D(2, 0), new JavaPoint2D(4, 0), new JavaPoint2D(6, 0), new JavaPoint2D(8, 0));

        // then
        Assert.assertFalse(split.isEmpty());
        Assert.assertEquals(split.size(), 5);
        for (JavaLine2D line : split) {
            Assert.assertEquals(line.length(), javaLine.length() / 5.0, 0.000001);
        }
    }

    @Test
    void testSplit5() {
        // given
        JavaLine2D javaLine = new JavaLine2D(0, 0, 10, 0);

        // when
        Set<JavaLine2D> split = javaLine.split(new JavaPoint2D(5, 0), new JavaPoint2D(4, 5), new JavaPoint2D(12, 0));

        // then
        Assert.assertFalse(split.isEmpty());
        Assert.assertEquals(split.size(), 2);
        for (JavaLine2D line : split) {
            Assert.assertEquals(line.length(), javaLine.length() / 2.0, 0.000001);
        }
    }

    @Test
    void testSplit6() {
        // given
        JavaLine2D javaLine = new JavaLine2D(0, 0, 10, 0);

        // when
        Set<JavaLine2D> split = javaLine.split(new JavaPoint2D(15, 0), new JavaPoint2D(4, 5), new JavaPoint2D(12, 0));

        // then
        Assert.assertTrue(split.isEmpty());
    }

    @Test
    public void testGetPointAlongLine() {
        // given
        JavaPoint2D jp1 = new JavaPoint2D(0, 0);
        JavaPoint2D jp2 = new JavaPoint2D(1, 1);
        JavaLine2D line = new JavaLine2D(jp1, jp2);
        double distance = Math.sqrt(2) / 2;

        // when
        JavaPoint2D pointAlongLine = line.getPointAlongLine(distance);

        // then
        Assert.assertEquals(pointAlongLine, new JavaPoint2D(0.5, 0.5));
    }

    @Test
    public void testGetInterpolatedPoints() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(0, 0, 0);
        JavaPoint3D jp2 = new JavaPoint3D(5, 5, 0);
        JavaLine3D line = new JavaLine3D(jp1, jp2);
        int n = 4;

        // when
        List<JavaPoint3D> pointAlongLine = line.getInterpolatedPoints(n);

        // then
        Assert.assertEquals(pointAlongLine.size(), n);
        for (int i = 1; i <= n; i++) {
            Assert.assertEquals(pointAlongLine.get(i - 1), new JavaPoint3D(i, i, 0));

        }
    }

    @Test
    public void testRotate() {
        // given
        JavaLine2D line = new JavaLine2D(new JavaPoint2D(0, 0), new JavaPoint2D(10, 10));
        double rotation = Math.toDegrees(line.getRotation());

        double angle = 45;

        // when
        JavaLine2D rotate = line.rotate(Math.toRadians(angle));

        // then
        double newRotation = Math.toDegrees(rotate.getRotation());
        Assert.assertTrue(MathUtils.equals(newRotation, rotation + angle));
    }

}
