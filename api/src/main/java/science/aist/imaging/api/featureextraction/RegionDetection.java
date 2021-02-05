/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.featureextraction;

import science.aist.imaging.api.domain.RecognizedObject;

import java.util.List;

/**
 * Detections regions in images using information, depending on the image.
 * Usually tries to find regions with similar information (color values, depth), then collects coordinates of this region.
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
public interface RegionDetection<T, V> {

    /**
     * Recognize regions using a specific threshold and information of an image-object.
     *
     * @param image     image to find regions in for
     * @param threshold threshold to use during the region search process
     * @return list containing the found regions
     */
    List<RecognizedObject<T, V>> recognizeRegion(T image, double threshold);
}
