/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.objectprocessing.compare.impl;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.service.core.objectprocessing.compare.ObjectComparison;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple comparison class, which allows to compare objects by their current coordinate data.
 * Ignores information regarding other image data.
 * <p>
 * If the objects are not the same size, will never reach a similarity of 100%.
 * Uses the size of the bigger object to calculate overlap percentage.
 * <p>
 * IMPORTANT: When comparing multiple images, the "closest"-image parameter has to be set with a new object.
 * If it stays null, then returning the closest found object is not possible.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public class PositionComparisonImpl<T, V> implements ObjectComparison<T, V> {

    @Override
    public double compare(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2) {

        RecognizedObject<T, V> bigger = null;
        RecognizedObject<T, V> smaller = null;

        //find the bigger object, makes algorithm a bit faster
        if (object1.getCoordinates().size() > object2.getCoordinates().size()) {
            bigger = object1;
            smaller = object2;
        } else {
            bigger = object2;
            smaller = object1;
        }

        //add all coordinates of the bigger object to the set
        Set<JavaPoint2D> coordinates = new HashSet<>(bigger.getCoordinates());

        double identical = 0.0;
        //start adding the coordinates of the other object
        //if the set already contains the coordinate, add returns false
        //which means the coordinate exists in both objects
        for (JavaPoint2D coordinate : smaller.getCoordinates()) {
            if (!coordinates.add(coordinate)) {
                identical++;
            }
        }

        return identical / (double) bigger.getCoordinates().size();
    }

    @Override
    public double findClosest(RecognizedObject<T, V> compareMe, List<RecognizedObject<T, V>> recognizedObjects, RecognizedObject<T, V> closest) {
        double similarity = 0.0;
        //compare all the objects in the list with the first object
        for (RecognizedObject<T, V> object : recognizedObjects) {
            double currentSimilarity = compare(compareMe, object);
            //if we found better similarity, set it and set the closest found object to the current object
            if (currentSimilarity > similarity) {
                similarity = currentSimilarity;

                if (closest != null) {
                    closest.setCoordinates(object.getCoordinates());
                    closest.setId(object.getId());
                    closest.setThresholdUsed(object.getThresholdUsed());
                    closest.setHuman(object.isHuman());
                    closest.setValue(object.getValue());
                    closest.setFromImage(object.getFromImage());
                }
            }
        }
        return similarity;
    }
}
