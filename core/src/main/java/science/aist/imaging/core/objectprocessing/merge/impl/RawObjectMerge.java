/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.objectprocessing.merge.impl;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.core.objectprocessing.merge.ObjectMerge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Flat out merges the two objects, without using any threshold or other safety checks.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public class RawObjectMerge<T, V> implements ObjectMerge<T, V> {

    @Override
    public RecognizedObject<T, V> merge(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2, double threshold) {

        RecognizedObject<T, V> mergedObject = new RecognizedObject<>();

        //filter out overlapping coordinates using a set
        Set<JavaPoint2D> coordinates = new HashSet<>();
        coordinates.addAll(object1.getCoordinates());
        coordinates.addAll(object2.getCoordinates());

        mergedObject.setFromImage(object1.getFromImage());
        mergedObject.setCoordinates(new ArrayList<>(coordinates));
        mergedObject.setHuman(object1.isHuman() && object2.isHuman());
        //we don't set id, value and threshold
        //this should be done by other methods

        return mergedObject;
    }
}
