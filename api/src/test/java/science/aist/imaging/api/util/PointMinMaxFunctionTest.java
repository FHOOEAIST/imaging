/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.util;

import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.jack.data.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Test class for {@link PointMinMaxFunction}</p>
 *
 * @author Christoph Praschl
 */
public class PointMinMaxFunctionTest {

    @Test
    public void testApply() {
        // given
        List<JavaPoint3D> list = Arrays.asList(new JavaPoint3D(1, 2, 3), new JavaPoint3D(5, -3, 3), new JavaPoint3D(-10, 2, 3));
        PointMinMaxFunction<JavaPoint3D> function = new PointMinMaxFunction<>();

        // when
        Pair<JavaPoint3D, JavaPoint3D> apply = function.apply(list);

        // then
        Assert.assertEquals(apply.getFirst(), new JavaPoint3D(-10, -3, 3));
        Assert.assertEquals(apply.getSecond(), new JavaPoint3D(5, 2, 3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testApply2() {
        // given
        PointMinMaxFunction<JavaPoint3D> function = new PointMinMaxFunction<>();

        // when
        Pair<JavaPoint3D, JavaPoint3D> apply = function.apply(null);

        // then - exception
    }
}