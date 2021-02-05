/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import science.aist.imaging.api.domain.AbstractJavaPointCloud;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


/**
 * <p>Test class for {@link JavaPointCloud2D}</p>
 *
 * @author Christoph Praschl
 */
public class JavaPointCloud2DTest {
    private JavaPointCloud2D getSample() {
        List<JavaPoint2D> points = Arrays.asList(
                new JavaPoint2D(0.0, 0.0),
                new JavaPoint2D(1.0, 1.0),
                new JavaPoint2D(1.0, 0.0),
                new JavaPoint2D(0.0, 1.0)
        );
        return new JavaPointCloud2D(points);
    }

    @Test
    public void testSub() {
        // given
        AbstractJavaPointCloud<JavaPoint2D> sample = getSample();
        JavaPoint2D subtractor = new JavaPoint2D(1, 1);

        // when
        AbstractJavaPointCloud<JavaPoint2D> sub = sample.sub(subtractor);

        // then
        List<JavaPoint2D> points = sub.getPoints();
        for (JavaPoint2D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.sub(subtractor)));
        }
    }

    @Test
    public void testAdd() {
        // given
        AbstractJavaPointCloud<JavaPoint2D> sample = getSample();
        JavaPoint2D adder = new JavaPoint2D(5, 8);

        // when
        AbstractJavaPointCloud<JavaPoint2D> add = sample.add(adder);

        // then
        List<JavaPoint2D> points = add.getPoints();
        for (JavaPoint2D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.add(adder)));
        }
    }

    @Test
    public void testMult() {
        // given
        AbstractJavaPointCloud<JavaPoint2D> sample = getSample();
        JavaPoint2D multiplier = new JavaPoint2D(5, 8);

        // when
        AbstractJavaPointCloud<JavaPoint2D> mult = sample.mult(multiplier);

        // then
        List<JavaPoint2D> points = mult.getPoints();
        for (JavaPoint2D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.mult(multiplier)));
        }
    }

    @Test
    public void testDiv() {
        // given
        AbstractJavaPointCloud<JavaPoint2D> sample = getSample();

        // when
        AbstractJavaPointCloud<JavaPoint2D> div = sample.div(5);

        // then
        List<JavaPoint2D> points = div.getPoints();
        for (JavaPoint2D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.div(5)));
        }
    }

    @Test
    public void testRotateAroundCenter() {
        // given
        JavaPointCloud2D sample = getSample();

        // when
        AbstractJavaPointCloud<JavaPoint2D> rotateAroundCenter = sample.rotateAroundCenter(Math.toRadians(90));

        // then
        Assert.assertEquals(rotateAroundCenter, sample);
    }

    @Test
    public void testRotate() {
        // given
        JavaPointCloud2D sample = getSample();
        JavaPoint2D rotationCenter = new JavaPoint2D(5, 2);

        // when
        JavaPointCloud2D rotateAroundCenter = sample.rotate(45, rotationCenter);

        // then
        List<JavaPoint2D> points = rotateAroundCenter.getPoints();
        for (JavaPoint2D point : sample.getPoints()) {
            Assert.assertTrue(points.contains(point.rotate(45, rotationCenter)));
        }
    }

    @Test
    public void testScale() {
        // given
        AbstractJavaPointCloud<JavaPoint2D> sample = getSample();

        // when
        AbstractJavaPointCloud<JavaPoint2D> scale = sample.scale(new JavaPoint2D(5, 5));

        // then
        Assert.assertEquals(scale.getCenterPoint(), sample.getCenterPoint());
    }

    @Test
    public void testGetCenterPoint() {
        // given
        AbstractJavaPointCloud<JavaPoint2D> sample = getSample();

        // when
        JavaPoint2D centerPoint = sample.getCenterPoint();

        // then
        Assert.assertEquals(centerPoint, new JavaPoint2D(0.5, 0.5));
    }
}