/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Test class for {@link JavaModel3D}</p>
 *
 * @author Christoph Praschl
 */
public class JavaModel3DTest {

    /**
     * @return a simple {@link JavaModel3D} instance representing a cube defined by 12 {@link JavaTriangle3D} (2 per side)
     */
    private JavaModel3D getSample() {
        List<JavaPolygon3D> mesh = new ArrayList<>();
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(1.0, 1.0, 0.0), new JavaPoint3D(1.0, 0.0, 0.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(0.0, 1.0, 0.0), new JavaPoint3D(1.0, 1.0, 0.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(0.0, 1.0, 1.0), new JavaPoint3D(0.0, 1.0, 0.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(0.0, 0.0, 1.0), new JavaPoint3D(0.0, 1.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 1.0, 0.0), new JavaPoint3D(1.0, 1.0, 1.0), new JavaPoint3D(1.0, 1.0, 0.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 1.0, 0.0), new JavaPoint3D(0.0, 1.0, 1.0), new JavaPoint3D(1.0, 1.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(1.0, 0.0, 0.0), new JavaPoint3D(1.0, 1.0, 0.0), new JavaPoint3D(1.0, 1.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(1.0, 0.0, 0.0), new JavaPoint3D(1.0, 1.0, 1.0), new JavaPoint3D(1.0, 0.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(1.0, 0.0, 0.0), new JavaPoint3D(1.0, 0.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(1.0, 0.0, 1.0), new JavaPoint3D(0.0, 0.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 1.0), new JavaPoint3D(1.0, 0.0, 1.0), new JavaPoint3D(1.0, 1.0, 1.0)));
        mesh.add(new JavaPolygon3D(new JavaPoint3D(0.0, 0.0, 1.0), new JavaPoint3D(1.0, 1.0, 1.0), new JavaPoint3D(0.0, 1.0, 1.0)));
        return new JavaModel3D(mesh);
    }

    @Test
    public void testMove() {
        // given
        JavaModel3D sample = getSample();
        JavaPoint3D moveVector = new JavaPoint3D(1, -2, 5);

        // when
        JavaModel3D moved = sample.move(moveVector);

        // then
        List<JavaPoint3D> points = moved.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.add(moveVector)));
        }

    }

    @Test
    public void testRotate() {
        // given
        JavaModel3D sample = getSample();
        JavaPoint3D centerPoint = sample.getCenterPoint();

        // when
        JavaModel3D rotated = sample.rotate(90, 90, 90);

        // then
        Assert.assertEquals(rotated.getCenterPoint(), centerPoint.rotate(90, 90, 90));
        List<JavaPoint3D> points = rotated.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.rotate(90, 90, 90)));
        }
        Assert.assertEquals(rotated.getPitch(), 90.0);
        Assert.assertEquals(rotated.getYaw(), 90.0);
        Assert.assertEquals(rotated.getRoll(), 90.0);
    }

    @Test
    public void testRotateAroundCenter() {
        // given
        JavaModel3D sample = getSample();
        JavaPoint3D centerPoint = sample.getCenterPoint();

        // when
        JavaModel3D rotated = sample.rotateAroundCenter(45, 45, 45);

        // then
        Assert.assertEquals(rotated.getCenterPoint(), centerPoint);
        List<JavaPoint3D> points = rotated.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.rotate(centerPoint, 45, 45, 45)));
        }
        Assert.assertEquals(rotated.getPitch(), 45.0);
        Assert.assertEquals(rotated.getYaw(), 45.0);
        Assert.assertEquals(rotated.getRoll(), 45.0);
    }

    @Test
    public void testScale() {
        // given
        JavaModel3D sample = getSample();
        JavaPoint3D centerPoint = sample.getCenterPoint();

        // when
        JavaModel3D scale = sample.scale(5);

        // then
        Assert.assertEquals(scale.getCenterPoint(), centerPoint);
        Assert.assertEquals(scale.getWidth(), sample.getWidth() * 5);
    }

    @Test
    public void testGetCenterPoint() {
        // given
        JavaModel3D sample = getSample();

        // when
        JavaPoint3D centerPoint = sample.getCenterPoint();

        // then
        Assert.assertEquals(centerPoint, new JavaPoint3D(0.5, 0.5, 0.5));
    }
}