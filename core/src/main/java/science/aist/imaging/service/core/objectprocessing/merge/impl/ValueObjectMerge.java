/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.objectprocessing.merge.impl;

import lombok.CustomLog;
import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.service.core.objectprocessing.merge.ObjectMerge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Merges objects, if their value is close enough (depends on threshold as well).
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
public class ValueObjectMerge<T, V extends Number> implements ObjectMerge<T, V> {
    @Override
    public RecognizedObject<T, V> merge(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2, double threshold) {

        if (object1.getValue() == null ||
                object2.getValue() == null ||
                Math.abs(object1.getValue().doubleValue() - object2.getValue().doubleValue()) > threshold) {
            log.warn("Could not merge objects, as their value difference was above the required threshold!");
            return null;
        }

        Set<JavaPoint2D> coordinates = new HashSet<>();
        coordinates.addAll(object1.getCoordinates());
        coordinates.addAll(object2.getCoordinates());

        //lets merge the object
        RecognizedObject<T, V> mergedObject = new RecognizedObject<>();
        mergedObject.setFromImage(object1.getFromImage());
        mergedObject.setCoordinates(new ArrayList<>(coordinates));
        mergedObject.setHuman(object1.isHuman() && object2.isHuman());
        //we don't set id, value and threshold
        //this should be done by other methods

        return mergedObject;
    }
}
