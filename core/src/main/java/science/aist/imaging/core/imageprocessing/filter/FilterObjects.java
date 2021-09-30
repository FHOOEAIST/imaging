/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.filter;

import science.aist.imaging.api.domain.RecognizedObject;

import java.util.List;

/**
 * Filters list of recognized objects, depending on the implementation.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public interface FilterObjects<T, V> {

    /**
     * Remove objects from the given list using a threshold.
     *
     * @param objects   objects to filter
     * @param threshold threshold to use during the filtering process
     * @return new list containing filtered objects
     */
    List<RecognizedObject<T, V>> filter(List<RecognizedObject<T, V>> objects, double threshold);
}
