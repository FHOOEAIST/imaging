/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.objectprocessing.merge;

import science.aist.imaging.api.domain.RecognizedObject;

/**
 * Merges two different objects according to some threshold.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public interface ObjectMerge<T, V> {

    /**
     * Merges two different objects.
     * Considers the threshold during the merge.
     * If the objects do not fit according to the threshold, will not merge and return null.
     *
     * @param object1   object 1 to merge
     * @param object2   object 2 to merge
     * @param threshold threshold to consider during the merge process
     * @return merged object or null, if objects don't fit according to threshold
     */
    RecognizedObject<T, V> merge(RecognizedObject<T, V> object1, RecognizedObject<T, V> object2, double threshold);
}
