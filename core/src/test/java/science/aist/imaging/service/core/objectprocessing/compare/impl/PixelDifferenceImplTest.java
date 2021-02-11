/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.objectprocessing.compare.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Test class for {@link PixelDifferenceImpl}</p>
 *
 * @author Christoph Praschl
 */
public class PixelDifferenceImplTest {

    @Test
    public void testCalculatePixelDifferences() {
        //GIVEN
        //We have two objects with different (but some overlapping) coordinates.
        //Lets calculate the non-overlapping coordinates.
        RecognizedObject<Double, Double> object1 = new RecognizedObject<>();

        List<JavaPoint2D> coordinates1 = new ArrayList<>();
        coordinates1.add(new JavaPoint2D(0, 0));
        coordinates1.add(new JavaPoint2D(0, 1));
        coordinates1.add(new JavaPoint2D(1, 0));
        coordinates1.add(new JavaPoint2D(1, 1));

        object1.setCoordinates(coordinates1);

        RecognizedObject<Double, Double> object2 = new RecognizedObject<>();

        List<JavaPoint2D> coordinates2 = new ArrayList<>();
        coordinates2.add(new JavaPoint2D(1, 0));
        coordinates2.add(new JavaPoint2D(1, 1));
        coordinates2.add(new JavaPoint2D(2, 0));
        coordinates2.add(new JavaPoint2D(1, 2));
        coordinates2.add(new JavaPoint2D(2, 2));

        object2.setCoordinates(coordinates2);

        PixelDifferenceImpl<Double, Double> pixelDifference = new PixelDifferenceImpl<>();

        //WHEN
        List<JavaPoint2D> nonOverlapping = pixelDifference.calculateDifference(object1, object2);

        //THEN
        Assert.assertNotNull(nonOverlapping);
        Assert.assertEquals(nonOverlapping.size(), 5);
        Assert.assertTrue(nonOverlapping.contains(new JavaPoint2D(0, 0)));
        Assert.assertTrue(nonOverlapping.contains(new JavaPoint2D(0, 1)));
        Assert.assertTrue(nonOverlapping.contains(new JavaPoint2D(2, 0)));
        Assert.assertTrue(nonOverlapping.contains(new JavaPoint2D(1, 2)));
        Assert.assertTrue(nonOverlapping.contains(new JavaPoint2D(2, 2)));
    }
}