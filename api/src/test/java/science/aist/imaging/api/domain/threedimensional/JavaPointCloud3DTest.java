/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import science.aist.imaging.api.domain.AbstractJavaPointCloud;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Test class for {@link AbstractJavaPointCloud}</p>
 *
 * @author Christoph Praschl
 */
public class JavaPointCloud3DTest {
    private JavaPointCloud3D getSample() {
        List<JavaPoint3D> points = Arrays.asList(
                new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(1.0, 1.0, 0.0), new JavaPoint3D(1.0, 0.0, 0.0),
                new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(0.0, 1.0, 0.0), new JavaPoint3D(1.0, 1.0, 0.0),
                new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(0.0, 1.0, 1.0), new JavaPoint3D(0.0, 1.0, 0.0),
                new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(0.0, 0.0, 1.0), new JavaPoint3D(0.0, 1.0, 1.0),
                new JavaPoint3D(0.0, 1.0, 0.0), new JavaPoint3D(1.0, 1.0, 1.0), new JavaPoint3D(1.0, 1.0, 0.0),
                new JavaPoint3D(0.0, 1.0, 0.0), new JavaPoint3D(0.0, 1.0, 1.0), new JavaPoint3D(1.0, 1.0, 1.0),
                new JavaPoint3D(1.0, 0.0, 0.0), new JavaPoint3D(1.0, 1.0, 0.0), new JavaPoint3D(1.0, 1.0, 1.0),
                new JavaPoint3D(1.0, 0.0, 0.0), new JavaPoint3D(1.0, 1.0, 1.0), new JavaPoint3D(1.0, 0.0, 1.0),
                new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(1.0, 0.0, 0.0), new JavaPoint3D(1.0, 0.0, 1.0),
                new JavaPoint3D(0.0, 0.0, 0.0), new JavaPoint3D(1.0, 0.0, 1.0), new JavaPoint3D(0.0, 0.0, 1.0),
                new JavaPoint3D(0.0, 0.0, 1.0), new JavaPoint3D(1.0, 0.0, 1.0), new JavaPoint3D(1.0, 1.0, 1.0),
                new JavaPoint3D(0.0, 0.0, 1.0), new JavaPoint3D(1.0, 1.0, 1.0), new JavaPoint3D(0.0, 1.0, 1.0)
        );
        return new JavaPointCloud3D(points);
    }

    @Test
    public void testSub() {
        // given
        AbstractJavaPointCloud<JavaPoint3D> sample = getSample();
        JavaPoint3D subtractor = new JavaPoint3D(1, 1, 1);

        // when
        AbstractJavaPointCloud<JavaPoint3D> sub = sample.sub(subtractor);

        // then
        List<JavaPoint3D> points = sub.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.sub(subtractor)));
        }
    }

    @Test
    public void testAdd() {
        // given
        AbstractJavaPointCloud<JavaPoint3D> sample = getSample();
        JavaPoint3D adder = new JavaPoint3D(5, 8, 9);

        // when
        AbstractJavaPointCloud<JavaPoint3D> add = sample.add(adder);

        // then
        List<JavaPoint3D> points = add.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.add(adder)));
        }
    }

    @Test
    public void testMult() {
        // given
        AbstractJavaPointCloud<JavaPoint3D> sample = getSample();
        JavaPoint3D multiplier = new JavaPoint3D(5, 8, 9);

        // when
        AbstractJavaPointCloud<JavaPoint3D> mult = sample.mult(multiplier);

        // then
        List<JavaPoint3D> points = mult.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.mult(multiplier)));
        }
    }

    @Test
    public void testDiv() {
        // given
        AbstractJavaPointCloud<JavaPoint3D> sample = getSample();

        // when
        AbstractJavaPointCloud<JavaPoint3D> div = sample.div(5);

        // then
        List<JavaPoint3D> points = div.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.div(5)));
        }
    }

    @Test
    public void testRotateAroundCenter() {
        // given
        JavaPointCloud3D sample = getSample();

        // when
        AbstractJavaPointCloud<JavaPoint3D> rotateAroundCenter = sample.rotateAroundCenter(90, 90, 90);

        // then
        Assert.assertEquals(rotateAroundCenter, sample);
    }

    @Test
    public void testRotate() {
        // given
        JavaPointCloud3D sample = getSample();
        JavaPoint3D rotationCenter = new JavaPoint3D(5, 2, 3);

        // when
        JavaPointCloud3D rotateAroundCenter = sample.rotate(rotationCenter, 45, 20, 30);

        // then
        List<JavaPoint3D> points = rotateAroundCenter.getPoints();
        for (JavaPoint3D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.rotate(rotationCenter, 45, 20, 30)));
        }
    }

    @Test
    public void testScale() {
        // given
        AbstractJavaPointCloud<JavaPoint3D> sample = getSample();

        // when
        AbstractJavaPointCloud<JavaPoint3D> scale = sample.scale(new JavaPoint3D(5, 5, 5));

        // then
        Assert.assertEquals(scale.getCenterPoint(), sample.getCenterPoint());
    }

    @Test
    public void testGetCenterPoint() {
        // given
        AbstractJavaPointCloud<JavaPoint3D> sample = getSample();

        // when
        JavaPoint3D centerPoint = sample.getCenterPoint();

        // then
        Assert.assertEquals(centerPoint, new JavaPoint3D(0.5, 0.5, 0.5));
    }
}