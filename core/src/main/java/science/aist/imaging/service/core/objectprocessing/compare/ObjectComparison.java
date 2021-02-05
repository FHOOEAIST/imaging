/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.objectprocessing.compare;

import science.aist.imaging.api.domain.RecognizedObject;

import java.util.List;

/**
 * Compares form of two different objects and returns information how close these objects are.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public interface ObjectComparison<T, V> {

    /**
     * Compares form of the two objects.
     * Returns how close said objects are using a percent value (e.g. 1.0 = objects are exactly the same).
     *
     * @param object1 object to compare
     * @param object2 object to compare
     * @return percent value how close the objects are to each other
     */
    double compare(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2);

    /**
     * Compares one object with a list of other objects.
     * Finds the object, which is closest to the first one and puts it into the "closest" variable.
     * Additionally, returns the percent value of how close the closest found is with the given object.
     *
     * @param compareMe object to compare with the other objects
     * @param objects   list of objects that will be compared to the first object
     * @param closest   closest found object will be put into this variable
     * @return percent value how close the closest object is with the given object
     */
    double findClosest(RecognizedObject<T, V> compareMe, List<RecognizedObject<T, V>> objects, RecognizedObject<T, V> closest);

}
