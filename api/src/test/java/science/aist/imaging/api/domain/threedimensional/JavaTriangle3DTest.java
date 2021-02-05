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
import java.util.Optional;

/**
 * <p>Test class for {@link JavaTriangle3D}</p>
 *
 * @author Christoph Praschl
 */
public class JavaTriangle3DTest {

    @Test
    public void testNormalvector() {
        // given
        JavaPoint3D a = new JavaPoint3D(0, 0, 0);
        JavaPoint3D b = new JavaPoint3D(10, 0, 0);
        JavaPoint3D c = new JavaPoint3D(0, 10, 0);
        JavaTriangle3D jt = new JavaTriangle3D(a, b, c);

        // when
        JavaPoint3D normalvector = jt.getNormalvector();

        // then
        Assert.assertEquals(normalvector.getX(), 0, 0.0001);
        Assert.assertEquals(normalvector.getY(), 0, 0.0001);
        Assert.assertEquals(normalvector.getZ(), 100, 0.0001);
    }

    @Test
    public void testGetContour() {
        // given
        JavaPoint3D a = new JavaPoint3D(0, 0, 0);
        JavaPoint3D b = new JavaPoint3D(10, 0, 0);
        JavaPoint3D c = new JavaPoint3D(0, 10, 0);
        JavaTriangle3D jt = new JavaTriangle3D(a, b, c);

        // when
        List<JavaLine3D> contour = jt.getContour();

        // then

        Assert.assertEquals(contour.size(), 3);
        Assert.assertTrue(contour.contains(new JavaLine3D(new JavaPoint3D(0, 0, 0), new JavaPoint3D(10, 0, 0))));
        Assert.assertTrue(contour.contains(new JavaLine3D(new JavaPoint3D(10, 0, 0), new JavaPoint3D(0, 10, 0))));
        Assert.assertTrue(contour.contains(new JavaLine3D(new JavaPoint3D(0, 10, 0), new JavaPoint3D(0, 0, 0))));
    }

    @Test
    public void testLineIntersection() {
        // given
        JavaTriangle3D t = new JavaTriangle3D(new JavaPoint3D(0, 0, 0), new JavaPoint3D(10, 0, 0), new JavaPoint3D(5, 10, 0));
        JavaLine3D l = new JavaLine3D(new JavaPoint3D(5, 3, 5), new JavaPoint3D(5, 3, 1));

        JavaPoint3D compare = new JavaPoint3D(5, 3, 0);

        // when
        Optional<JavaPoint3D> point3D = t.getIntersection(l);

        // then
        Assert.assertTrue(point3D.isPresent());
        Assert.assertEquals(point3D.get(), compare);
    }

    @Test
    public void testLineIntersection2() {
        // given
        JavaTriangle3D t = new JavaTriangle3D(new JavaPoint3D(0, 0, 0), new JavaPoint3D(10, 0, 0), new JavaPoint3D(5, 10, 0));
        JavaLine3D l = new JavaLine3D(new JavaPoint3D(5, 3, 5), new JavaPoint3D(7, 3, 5));

        // when
        Optional<JavaPoint3D> point3D = t.getIntersection(l);

        // then
        Assert.assertFalse(point3D.isPresent());
    }
}