/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import science.aist.imaging.api.domain.Side;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

/**
 * <p>Tests {@link JavaRectangle2D}</p>
 *
 * @author Andreas Pointner
 */
public class JavaRectangle2DTest {
    @Test
    void testGetCenterPoint() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(0, 0, 4, 4);

        // when
        JavaPoint2D centerPoint = jr.getCenterPoint();

        // then
        Assert.assertEquals(centerPoint, new JavaPoint2D(2, 2));
    }

    @Test
    void testGetWidth() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(0, 0, 3, 4);

        // when
        double width = jr.getWidth();

        // then
        Assert.assertEquals(width, 3, 0.01);
    }

    @Test
    void testGetHeight() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(0, 0, 3, 4);

        // when
        double height = jr.getHeight();

        // then
        Assert.assertEquals(height, 4, 0.01);
    }

    @Test
    void testCreateRectangle() {
        // given

        // when
        JavaRectangle2D jr = JavaRectangle2D.createRectangle(1, 4, 3, 2);

        // then
        Assert.assertEquals(jr.getTopLeft(), new JavaPoint2D(1, 2));
        Assert.assertEquals(jr.getBottomRight(), new JavaPoint2D(3, 4));
    }

    @Test
    void testGetTopRight() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        JavaPoint2D topRight = jr.getTopRight();

        // then
        Assert.assertEquals(topRight, new JavaPoint2D(3, 2));
    }

    @Test
    void testGetBottomLeft() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        JavaPoint2D topRight = jr.getBottomLeft();

        // then
        Assert.assertEquals(topRight, new JavaPoint2D(1, 4));
    }

    @Test
    void testGetTopLine() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        JavaLine2D line = jr.getTopLine();

        // then
        Assert.assertEquals(line, new JavaLine2D(1, 2, 3, 2));
    }

    @Test
    void testGetRightLine() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        JavaLine2D line = jr.getRightLine();

        // then
        Assert.assertEquals(line, new JavaLine2D(3, 2, 3, 4));
    }

    @Test
    void testGetBottomLine() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        JavaLine2D line = jr.getBottomLine();

        // then
        Assert.assertEquals(line, new JavaLine2D(1, 4, 3, 4));
    }

    @Test
    void testGetLeftLine() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        JavaLine2D line = jr.getLeftLine();

        // then
        Assert.assertEquals(line, new JavaLine2D(1, 2, 1, 4));
    }

    @Test
    void testGetBorderLines() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        List<JavaLine2D> borderLines = jr.getBorderLines();

        // then
        Assert.assertNotNull(borderLines);
        Assert.assertEquals(borderLines.size(), 4);
        Assert.assertEquals(borderLines.get(0), new JavaLine2D(1, 2, 3, 2));
        Assert.assertEquals(borderLines.get(1), new JavaLine2D(3, 2, 3, 4));
        Assert.assertEquals(borderLines.get(2), new JavaLine2D(1, 4, 3, 4));
        Assert.assertEquals(borderLines.get(3), new JavaLine2D(1, 2, 1, 4));
    }

    @Test
    void testCreateBoundingBox() {
        // given

        // when
        JavaRectangle2D jr = JavaRectangle2D.createBoundingBox(
                new JavaPoint2D(1, 1),
                new JavaPoint2D(7, 2),
                new JavaPoint2D(6, 8),
                new JavaPoint2D(0, 3)
        );

        // then
        Assert.assertEquals(jr.getTopLeft(), new JavaPoint2D(0, 1));
        Assert.assertEquals(jr.getBottomRight(), new JavaPoint2D(7, 8));
    }

    @Test
    void testArea() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        double area = jr.getArea();

        // then
        Assert.assertEquals(area, 4.0, 0.01);
    }

    @Test
    void testGetIntersection1() {
        // given
        JavaRectangle2D jr1 = new JavaRectangle2D(0, 0, 10, 10);
        JavaRectangle2D jr2 = new JavaRectangle2D(5, 5, 15, 15);

        // when
        Optional<JavaRectangle2D> jrIntOpt = JavaRectangle2D.getIntersection(jr1, jr2);

        // then
        Assert.assertTrue(jrIntOpt.isPresent());
        JavaRectangle2D jrInt = jrIntOpt.get();
        Assert.assertEquals(jrInt.getTopLeft(), new JavaPoint2D(5, 5));
        Assert.assertEquals(jrInt.getBottomRight(), new JavaPoint2D(10, 10));
    }

    @Test
    void testGetIntersection2() {
        // given
        JavaRectangle2D jr1 = new JavaRectangle2D(0, 0, 10, 10);
        JavaRectangle2D jr2 = new JavaRectangle2D(15, 15, 25, 25);

        // when
        Optional<JavaRectangle2D> jrIntOpt = JavaRectangle2D.getIntersection(jr1, jr2);

        // then
        Assert.assertFalse(jrIntOpt.isPresent());
    }

    @Test
    void testGetIntersection3() {
        // given
        JavaRectangle2D jr1 = new JavaRectangle2D(0, 0, 10, 10);
        JavaRectangle2D jr2 = new JavaRectangle2D(10, 10, 20, 20);

        // when
        Optional<JavaRectangle2D> jrIntOpt = JavaRectangle2D.getIntersection(jr1, jr2);

        // then
        Assert.assertTrue(jrIntOpt.isPresent());
        JavaRectangle2D jrInt = jrIntOpt.get();
        Assert.assertEquals(jrInt.getArea(), 0, 0.001);
    }


    @Test
    void testTouchingSide1() {
        // given
        JavaRectangle2D jr1 = new JavaRectangle2D(0, 0, 10, 10);
        JavaRectangle2D jr2 = new JavaRectangle2D(8, 10, 20, 20);

        // when
        Optional<Side> touchingSideOpt = JavaRectangle2D.touchingSide(jr1, jr2);

        // then
        Assert.assertTrue(touchingSideOpt.isPresent());
        Assert.assertEquals(touchingSideOpt.get(), Side.BOTTOM);
    }

    @Test
    void testTouchingSide2() {
        // given
        // I guess, when the corner points are touching, you cannot call it a touching side, as this would always
        // effect two sides, and the corner points could also be touched in case of a specific side is touching.
        // e.g. compare testTouchingSide1 test method
        JavaRectangle2D jr1 = new JavaRectangle2D(0, 0, 10, 10);
        JavaRectangle2D jr2 = new JavaRectangle2D(10, 10, 20, 20);

        // when
        Optional<Side> touchingSideOpt = JavaRectangle2D.touchingSide(jr1, jr2);

        // then
        Assert.assertFalse(touchingSideOpt.isPresent());
    }

    @Test
    void testTouchingSide3() {
        // given
        JavaRectangle2D jr1 = new JavaRectangle2D(0, 0, 10, 10);
        JavaRectangle2D jr2 = new JavaRectangle2D(2, 10, 3, 20);

        // when
        Optional<Side> touchingSideOpt = JavaRectangle2D.touchingSide(jr1, jr2);

        // then
        Assert.assertTrue(touchingSideOpt.isPresent());
        Assert.assertEquals(touchingSideOpt.get(), Side.BOTTOM);
    }

    @Test
    void testContainsJavaPoint1() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(10, 10, 20, 20);

        // when
        boolean b = jr.containsJavaPoint(new JavaPoint2D(10, 10));

        // then
        Assert.assertTrue(b);
    }

    @Test
    void testContainsJavaPoint2() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(10, 10, 20, 20);

        // when
        boolean b = jr.containsJavaPoint(new JavaPoint2D(9, 10));

        // then
        Assert.assertFalse(b);
    }

    @Test
    void testContainsJavaPoint3() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(10, 10, 20, 20);

        // when
        boolean b = jr.containsJavaPoint(new JavaPoint2D(14, 18));

        // then
        Assert.assertTrue(b);
    }

    @Test
    void testSetTopLeftPoint() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        jr.setTopLeft(new JavaPoint2D(5, 6));

        // then
        // we can directly verify it by checking set and get function, as the internal calculation are tested
        // in JavaRectangleRotatedTest, so we just need to test if everything is called correctly in JavaRectangle
        Assert.assertEquals(jr.getTopLeft().getX(), 5.0, 0.001);
        Assert.assertEquals(jr.getTopLeft().getY(), 6.0, 0.001);
    }

    @Test
    void testSetBottomRightPoint() {
        // given
        JavaRectangle2D jr = new JavaRectangle2D(1, 2, 3, 4);

        // when
        jr.setBottomRight(new JavaPoint2D(5, 6));

        // then
        // we can directly verify it by checking set and get function, as the internal calculation are tested
        // in JavaRectangleRotatedTest, so we just need to test if everything is called correctly in JavaRectangle
        Assert.assertEquals(jr.getBottomRight().getX(), 5.0, 0.001);
        Assert.assertEquals(jr.getBottomRight().getY(), 6.0, 0.001);
    }
}
