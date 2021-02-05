/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.distance;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * <p>Test class for {@link ManhattanDistanceMetric}</p>
 *
 * @author Christoph Praschl
 */
public class ManhattanDistanceMetricTest {

    @Test
    public void testCreate() {
        // given
        ManhattanDistanceMetric distanceMetric = new ManhattanDistanceMetric();
        double[][] compare = new double[][]{
                new double[]{2.0, 1.0, 2.0},
                new double[]{1.0, 0.0, 1.0},
                new double[]{2.0, 1.0, 2.0}
        };

        int size = 3;

        // when
        double[][] doubles = distanceMetric.create(size);

        // then
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                System.out.println(doubles[x][y]);
                Assert.assertEquals(compare[x][y], doubles[x][y], 0.00001);
            }
        }
    }
}