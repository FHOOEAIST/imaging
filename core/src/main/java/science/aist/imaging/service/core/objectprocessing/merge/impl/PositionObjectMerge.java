/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.objectprocessing.merge.impl;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.service.core.objectprocessing.merge.ObjectMerge;
import lombok.CustomLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Merges two objects depending on if a certain number of coordinates overlap.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
public class PositionObjectMerge<T, V> implements ObjectMerge<T, V> {

    @Override
    public RecognizedObject<T, V> merge(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2, double threshold) {
        Set<JavaPoint2D> coordinates = new HashSet<>();
        coordinates.addAll(object1.getCoordinates());
        coordinates.addAll(object2.getCoordinates());

        //we require at least "threshold"-X overlapping coordinates
        //check by calculating difference between all coordinates and unique coordinates
        if ((object1.getCoordinates().size() + object2.getCoordinates().size()) - coordinates.size() < threshold) {
            log.warn("Can't merge the images, because the required number of overlapping coordinates couldn't be reached!");
            return null;
        }

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
