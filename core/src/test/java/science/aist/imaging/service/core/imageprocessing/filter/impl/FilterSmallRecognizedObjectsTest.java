/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.impl;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Test class for {@link FilterSmallRecognizedObjects}</p>
 *
 * @author Christoph Praschl
 */
public class FilterSmallRecognizedObjectsTest {
    @Test
    public void testFilterSmallObjects() {
        //GIVEN
        //We have 3 objects.
        //1 with size 1, 1 with size 2, with size 3.
        //we want to filter all objects size < 2.
        List<RecognizedObject<Double, Double>> objects = new ArrayList<>();

        RecognizedObject<Double, Double> object1 = new RecognizedObject<>();
        RecognizedObject<Double, Double> object2 = new RecognizedObject<>();
        RecognizedObject<Double, Double> object3 = new RecognizedObject<>();

        List<JavaPoint2D> coordinates1 = new ArrayList<>();
        List<JavaPoint2D> coordinates2 = new ArrayList<>();
        List<JavaPoint2D> coordinates3 = new ArrayList<>();

        coordinates1.add(new JavaPoint2D());

        coordinates2.add(new JavaPoint2D());
        coordinates2.add(new JavaPoint2D());

        coordinates3.add(new JavaPoint2D());
        coordinates3.add(new JavaPoint2D());
        coordinates3.add(new JavaPoint2D());

        object1.setCoordinates(coordinates1);
        object2.setCoordinates(coordinates2);
        object3.setCoordinates(coordinates3);

        objects.add(object1);
        objects.add(object2);
        objects.add(object3);

        FilterSmallRecognizedObjects<Double, Double> filter = new FilterSmallRecognizedObjects<>();

        //WHEN
        List<RecognizedObject<Double, Double>> filtered = filter.filter(objects, 2.0);

        //THEN
        Assert.assertNotNull(filtered);
        Assert.assertEquals(filtered.size(), 2);
        Assert.assertTrue(filtered.contains(object2));
        Assert.assertTrue(filtered.contains(object3));
    }
}