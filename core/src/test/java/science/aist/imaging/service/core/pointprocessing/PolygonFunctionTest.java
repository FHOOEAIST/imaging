/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Test class for {@link PolygonFunction}</p>
 *
 * @author Christoph Praschl
 */
public class PolygonFunctionTest {

    @Test
    public void testCreate() {
        // given
        PolygonFunction polygonFunction = new PolygonFunction(10, 5, 0);

        List<JavaPoint2D> reference = Arrays.asList(new JavaPoint2D(15, 5), new JavaPoint2D(8, 15), new JavaPoint2D(-3, 11), new JavaPoint2D(-3, -1), new JavaPoint2D(8, -5));

        // when
        JavaPolygon2D javaPolygon2D = polygonFunction.apply(new JavaPoint2D(5, 5));

        // then
        for (JavaPoint2D point : javaPolygon2D.getPoints()) {
            Assert.assertTrue(reference.contains(new JavaPoint2D(point.getIntX(), point.getIntY())));
        }
    }

}
