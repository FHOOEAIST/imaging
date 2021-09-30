/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import org.opencv.core.KeyPoint;
import org.opencv.core.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Testclass for OpenCVFeatureWrapper
 *
 * @author Christoph Praschl
 */
public class OpenCVFeatureWrapperTest extends OpenCVTest {
    /**
     * Tests FeatureWrapper.transformFeatures function
     */
    @Test
    void testTransform() {
        // given
        List<KeyPoint> col = new ArrayList<>();
        col.add(new KeyPoint(10, 10, 1));
        col.add(new KeyPoint(15, 10, 1));
        col.add(new KeyPoint(20, 30, 1));

        FeatureWrapper<KeyPoint> feature = new OpenCVFeatureWrapper(col);

        // when
        Collection<KeyPoint> res = feature.getTransformedFeatures(100, 100, 10, 13, 0);

        // then
        int counter = 0;
        for (KeyPoint k : res) {
            Point p = col.get(counter).pt;

            Assert.assertEquals(k.pt.x, p.x - 10);
            Assert.assertEquals(k.pt.y, p.y - 13);

            counter++;
        }
    }

    /**
     * Tests FeatureWrapper.transformFeatures function
     */
    @Test
    void testTransform2() {
        // given
        List<KeyPoint> col = new ArrayList<>();
        col.add(new KeyPoint(10, 10, 1));
        col.add(new KeyPoint(15, 10, 1));

        FeatureWrapper<KeyPoint> feature = new OpenCVFeatureWrapper(col);

        // when
        Collection<KeyPoint> temp = feature.getTransformedFeatures(130, 160, 0, 0, 90);

        // then
        KeyPoint[] res = temp.toArray(new KeyPoint[0]);
        Point p1 = res[0].pt;
        Assert.assertEquals(p1.x, 135.0);
        Assert.assertEquals(p1.y, 25.0);

        Point p2 = res[1].pt;
        Assert.assertEquals(p2.x, 135.0);
        Assert.assertEquals(p2.y, 30.0);
    }

}
