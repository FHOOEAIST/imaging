/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.objectprocessing.compare;

import science.aist.imaging.api.domain.RecognizedObject;

import java.util.List;

/**
 * Interface for creating classes, which calculate the differences of objects.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public interface ObjectDifference<T, V, R> {

    List<R> calculateDifference(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2);
}
