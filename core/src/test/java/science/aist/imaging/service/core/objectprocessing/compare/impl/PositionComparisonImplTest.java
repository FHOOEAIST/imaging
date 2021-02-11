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
 * <p>Test class for {@link PositionComparisonImpl}</p>
 *
 * @author Christoph Praschl
 */
public class PositionComparisonImplTest {

    @Test
    public void testComparePositionSameObject() {
        //GIVEN
        //We have the same object twice and compare it.
        //Resulting similarity has to be 1.0 (100%).
        RecognizedObject<Double, Double> object = new RecognizedObject<>();

        List<JavaPoint2D> coordinates = new ArrayList<>();
        coordinates.add(new JavaPoint2D(0, 0));
        coordinates.add(new JavaPoint2D(0, 1));
        coordinates.add(new JavaPoint2D(1, 0));
        coordinates.add(new JavaPoint2D(1, 1));

        object.setCoordinates(coordinates);

        PositionComparisonImpl<Double, Double> comparison = new PositionComparisonImpl<>();

        //WHEN
        double similarity = comparison.compare(object, object);

        //THEN
        Assert.assertEquals(similarity, 1.0);
    }

    @Test
    public void testComparePositionDifferentObject() {
        //GIVEN
        //We have two different objects, overlapping with each other.
        //Resulting similarity should be 50% (2 pixels overlap for the 4 pixel image).
        RecognizedObject<Double, Double> object = new RecognizedObject<>();

        List<JavaPoint2D> coordinates = new ArrayList<>();
        coordinates.add(new JavaPoint2D(0, 0));
        coordinates.add(new JavaPoint2D(0, 1));
        coordinates.add(new JavaPoint2D(1, 0));
        coordinates.add(new JavaPoint2D(1, 1));

        object.setCoordinates(coordinates);

        RecognizedObject<Double, Double> object2 = new RecognizedObject<>();

        List<JavaPoint2D> coordinates2 = new ArrayList<>();
        coordinates2.add(new JavaPoint2D(1, 0));
        coordinates2.add(new JavaPoint2D(1, 1));
        coordinates2.add(new JavaPoint2D(2, 0));
        coordinates2.add(new JavaPoint2D(0, 2));

        object2.setCoordinates(coordinates2);

        PositionComparisonImpl<Double, Double> comparison = new PositionComparisonImpl<>();

        //WHEN
        double similarity = comparison.compare(object, object2);

        //THEN
        Assert.assertEquals(similarity, 0.5);
    }

    @Test
    public void testFindClosestObject() {
        //GIVEN
        //We have three different objects and want to compare 1 with the other 2.
        //Have to correctly find the image, which is more similar to the 1st one.
        RecognizedObject<Double, Double> object = new RecognizedObject<>();

        List<JavaPoint2D> coordinates = new ArrayList<>();
        coordinates.add(new JavaPoint2D(0, 0));
        coordinates.add(new JavaPoint2D(0, 1));
        coordinates.add(new JavaPoint2D(1, 0));
        coordinates.add(new JavaPoint2D(1, 1));

        object.setCoordinates(coordinates);

        RecognizedObject<Double, Double> object2 = new RecognizedObject<>();

        List<JavaPoint2D> coordinates2 = new ArrayList<>();
        coordinates2.add(new JavaPoint2D(1, 0));
        coordinates2.add(new JavaPoint2D(1, 1));
        coordinates2.add(new JavaPoint2D(2, 0));
        coordinates2.add(new JavaPoint2D(0, 2));

        object2.setCoordinates(coordinates2);

        RecognizedObject<Double, Double> object3 = new RecognizedObject<>();

        List<JavaPoint2D> coordinates3 = new ArrayList<>();
        coordinates3.add(new JavaPoint2D(2, 2));
        coordinates3.add(new JavaPoint2D(1, 1));
        coordinates3.add(new JavaPoint2D(2, 0));
        coordinates3.add(new JavaPoint2D(0, 2));

        object3.setCoordinates(coordinates3);

        List<RecognizedObject<Double, Double>> objects = new ArrayList<>();
        objects.add(object2);
        objects.add(object3);

        PositionComparisonImpl<Double, Double> comparison = new PositionComparisonImpl<>();

        //WHEN
        RecognizedObject<Double, Double> closest = new RecognizedObject<>();
        double similarity = comparison.findClosest(object, objects, closest);

        //THEN
        Assert.assertNotNull(closest);
        Assert.assertEquals(closest, object2);
        Assert.assertEquals(similarity, 0.5);
    }
}