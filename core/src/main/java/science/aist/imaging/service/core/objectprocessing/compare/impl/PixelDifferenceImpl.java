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
import science.aist.imaging.service.core.objectprocessing.compare.ObjectDifference;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates difference of two objects using their pixel coordinate information.
 * Will find pixels, which don't overlap, and return them.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public class PixelDifferenceImpl<T, V> implements ObjectDifference<T, V, JavaPoint2D> {

    @Override
    public List<JavaPoint2D> calculateDifference(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2) {
        List<JavaPoint2D> nonOverlapping = new ArrayList<>();

        List<JavaPoint2D> coordinates1 = new ArrayList<>(object1.getCoordinates());
        List<JavaPoint2D> coordinates2 = new ArrayList<>(object2.getCoordinates());

        coordinates1.removeAll(object2.getCoordinates());
        coordinates2.removeAll(object1.getCoordinates());

        nonOverlapping.addAll(coordinates1);
        nonOverlapping.addAll(coordinates2);

        return nonOverlapping;
    }
}
