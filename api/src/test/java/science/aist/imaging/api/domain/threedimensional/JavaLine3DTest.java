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

import java.util.List;

/**
 * <p>Test class for {@link JavaLine3D}</p>
 *
 * @author Christoph Praschl
 */
public class JavaLine3DTest {

    @Test
    public void testGetLineDirection() {
        // given
        JavaPoint3D compare = new JavaPoint3D(10, 10, 10);
        JavaLine3D line = new JavaLine3D(new JavaPoint3D(), compare);

        // when
        JavaPoint3D lineDirection = line.getLineDirection();

        // then
        Assert.assertEquals(lineDirection, compare);
    }

    @Test
    public void testGetLineDirection2() {
        // given
        JavaPoint3D compare = new JavaPoint3D(4, 1, -10);
        JavaLine3D line = new JavaLine3D(new JavaPoint3D(4, 5, 6), new JavaPoint3D(8, 6, -4));

        // when
        JavaPoint3D lineDirection = line.getLineDirection();

        // then
        Assert.assertEquals(lineDirection, compare);
    }

    @Test
    public void testGetPointAlongLine() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(0, 0, 0);
        JavaPoint3D jp2 = new JavaPoint3D(1, 1, 1);
        JavaLine3D line = new JavaLine3D(jp1, jp2);
        double distance = 0.5;

        // when
        JavaPoint3D pointAlongLine = line.getPointAlongLine(distance);

        // then
        Assert.assertEquals(JavaPoint3D.pointDistance(jp1, pointAlongLine), distance, 0.0001);
        Assert.assertEquals(line.length() - JavaPoint3D.pointDistance(jp1, pointAlongLine), JavaPoint3D.pointDistance(jp2, pointAlongLine), 0.0000001);
    }

    @Test
    public void testGetInterpolatedPoints() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(0, 0, 0);
        JavaPoint3D jp2 = new JavaPoint3D(1, 1, 1);
        JavaLine3D line = new JavaLine3D(jp1, jp2);

        // when
        List<JavaPoint3D> pointAlongLine = line.getInterpolatedPoints(1);

        // then
        Assert.assertEquals(pointAlongLine.size(), 1);
        Assert.assertEquals(pointAlongLine.get(0), new JavaPoint3D(0.5, 0.5, 0.5));
    }

    @Test
    public void testGetInterpolatedPoints2() {
        // given
        JavaPoint3D jp1 = new JavaPoint3D(0, 0, 0);
        JavaPoint3D jp2 = new JavaPoint3D(1, 1, 1);
        JavaLine3D line = new JavaLine3D(jp1, jp2);
        int n = 9;

        // when
        List<JavaPoint3D> pointAlongLine = line.getInterpolatedPoints(n, true);

        // then
        Assert.assertEquals(pointAlongLine.size(), n + 2);
        for (int i = 0; i < n; i++) {
            Assert.assertEquals(pointAlongLine.get(i), new JavaPoint3D(0.1 * i, 0.1 * i, 0.1 * i));

        }
    }
}