/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.filter.impl;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.core.imageprocessing.filter.FilterObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Removes recognized objects, which are too small.
 * Will remove the object, if size is below the required threshold
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public class FilterSmallRecognizedObjects<T, V> implements FilterObjects<T, V> {

    @Override
    public List<RecognizedObject<T, V>> filter(List<RecognizedObject<T, V>> recognizedObjects, double threshold) {

        List<RecognizedObject<T, V>> filtered = new ArrayList<>();

        for (RecognizedObject<T, V> object : recognizedObjects) {
            if (object.getCoordinates().size() >= threshold) {
                filtered.add(object);
            }
        }

        return filtered;
    }
}
