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

import java.util.List;

/**
 * <p>Tests JavaRectangle</p>
 *
 * @author Andreas Pointner
 */
public class JavaRectangleRotated2DTest {
    @Test
    public void testConstruct1() {
        // given

        // when
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 2), 3, 4, Math.toRadians(5));

        // then
        Assert.assertEquals(jrr.getHeight(), 4.0, 0.001);
        Assert.assertEquals(jrr.getWidth(), 3.0, 0.001);
        Assert.assertEquals(jrr.getCenterPoint().getX(), 1.0, 0.001);
        Assert.assertEquals(jrr.getCenterPoint().getY(), 2.0, 0.001);
        Assert.assertEquals(jrr.getRotation(), Math.toRadians(5), 0.001);
        Assert.assertEquals(jrr.getBottomLeft().getX(), -0.668, 0.001);
        Assert.assertEquals(jrr.getBottomLeft().getY(), 3.861, 0.001);
        Assert.assertEquals(jrr.getTopLeft().getX(), -0.319, 0.001);
        Assert.assertEquals(jrr.getTopLeft().getY(), -0.123, 0.001);
        Assert.assertEquals(jrr.getTopRight().getX(), 2.668, 0.001);
        Assert.assertEquals(jrr.getTopRight().getY(), 0.138, 0.001);
        Assert.assertEquals(jrr.getBottomRight().getX(), 2.319, 0.001);
        Assert.assertEquals(jrr.getBottomRight().getY(), 4.123, 0.001);
    }

    @Test
    public void testContainsJavaPoint() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(2, 2), 1, 1, Math.toRadians(45));

        // when
        boolean isInside = jrr.containsJavaPoint(new JavaPoint2D(2.0, 1.3));

        // then
        Assert.assertTrue(isInside);
    }

    @Test
    public void testNotContainsJavaPoint() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(2, 2), 1, 1, Math.toRadians(45));

        // when
        boolean isInside = jrr.containsJavaPoint(new JavaPoint2D(2.0, 1.2));

        // then
        Assert.assertFalse(isInside);
    }

    @Test
    public void testRotation() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, 0);

        // when
        JavaRectangleRotated2D rotated = jrr.rotate(Math.toRadians(45));

        // then
        Assert.assertEquals(rotated.getCenterPoint(), new JavaPoint2D(1, 1));
        Assert.assertEquals(rotated.getRotation(), Math.toRadians(45), 0.001);
        Assert.assertEquals(rotated.getWidth(), 2.0, 0.001);
        Assert.assertEquals(rotated.getHeight(), 2.0, 0.001);
    }

    @Test
    public void testNormalize() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, Math.toRadians(45));

        // when
        JavaRectangle2D jr = jrr.normalize();

        // then
        Assert.assertEquals(jr.getTopLeft().getX(), 0, 0.001);
        Assert.assertEquals(jr.getTopLeft().getY(), 0, 0.001);
        Assert.assertEquals(jr.getBottomRight().getX(), 2, 0.001);
        Assert.assertEquals(jr.getBottomRight().getY(), 2, 0.001);
    }

    @Test
    void testMoveTo() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, 0);

        // when
        JavaRectangleRotated2D moved = jrr.moveTo(new JavaPoint2D(2, 2));

        // then
        Assert.assertEquals(moved.getCenterPoint(), new JavaPoint2D(2, 2));
        Assert.assertEquals(moved.getRotation(), 0, 0.001);
        Assert.assertEquals(moved.getWidth(), 2.0, 0.001);
        Assert.assertEquals(moved.getHeight(), 2.0, 0.001);
    }

    @Test
    void testMove() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, 0);

        // when
        JavaRectangleRotated2D moved = jrr.move(new JavaPoint2D(1, 1));

        // then
        Assert.assertEquals(moved.getCenterPoint(), new JavaPoint2D(2, 2));
        Assert.assertEquals(moved.getRotation(), 0, 0.001);
        Assert.assertEquals(moved.getWidth(), 2.0, 0.001);
        Assert.assertEquals(moved.getHeight(), 2.0, 0.001);
    }

    @Test
    void testStretch() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, 0);

        // when
        JavaRectangleRotated2D stretched = jrr.stretch(2, 3);

        // then
        Assert.assertEquals(stretched.getCenterPoint(), new JavaPoint2D(1, 1));
        Assert.assertEquals(stretched.getRotation(), 0, 0.001);
        Assert.assertEquals(stretched.getWidth(), 4.0, 0.001);
        Assert.assertEquals(stretched.getHeight(), 6.0, 0.001);
    }

    @Test
    void testStretchTo() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, 0);

        // when
        JavaRectangleRotated2D stretched = jrr.stretchTo(4, 6);

        // then
        Assert.assertEquals(stretched.getCenterPoint(), new JavaPoint2D(1, 1));
        Assert.assertEquals(stretched.getRotation(), 0, 0.001);
        Assert.assertEquals(stretched.getWidth(), 4.0, 0.001);
        Assert.assertEquals(stretched.getHeight(), 6.0, 0.001);
    }

    @Test
    public void testGetContour() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(1, 1), 2, 2, 0);

        // when
        List<JavaLine2D> contour = jrr.getContour();

        // then
        Assert.assertEquals(contour.size(), 4);
        Assert.assertTrue(contour.contains(new JavaLine2D(new JavaPoint2D(0, 0), new JavaPoint2D(2, 0))));
        Assert.assertTrue(contour.contains(new JavaLine2D(new JavaPoint2D(2, 0), new JavaPoint2D(2, 2))));
        Assert.assertTrue(contour.contains(new JavaLine2D(new JavaPoint2D(2, 2), new JavaPoint2D(0, 2))));
        Assert.assertTrue(contour.contains(new JavaLine2D(new JavaPoint2D(0, 2), new JavaPoint2D(0, 0))));
    }
}
