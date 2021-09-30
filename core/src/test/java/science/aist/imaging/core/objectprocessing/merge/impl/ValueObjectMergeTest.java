/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.objectprocessing.merge.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Unittest-class for {@link ValueObjectMerge}
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 */
public class ValueObjectMergeTest {

    @Test
    public void testMergeByValue() {
        //GIVEN
        //We have two different objects which should be merged.
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

        ValueObjectMerge<Double, Double> merge = new ValueObjectMerge<>();

        object1.setValue(5.0);
        object2.setValue(6.0);

        //WHEN
        RecognizedObject<Double, Double> merged = merge.merge(object1, object2, 2);

        //THEN
        Assert.assertNotNull(merged);
        Assert.assertEquals(merged.getCoordinates().size(), 7);
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(0, 0)));
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(0, 1)));
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(1, 0)));
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(1, 1)));
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(2, 0)));
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(1, 2)));
        Assert.assertTrue(merged.getCoordinates().contains(new JavaPoint2D(2, 2)));
    }

    @Test
    public void testMergeByValueThresholdFail() {
        //GIVEN
        //We have two different objects which should be merged.
        //The threshold is too strict and can't be fulfilled.
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

        ValueObjectMerge<Double, Double> merge = new ValueObjectMerge<>();


        object1.setValue(5.0);
        object2.setValue(6.0);

        //WHEN
        RecognizedObject<Double, Double> merged = merge.merge(object1, object2, 0.5);

        //THEN
        Assert.assertNull(merged);
    }
}
