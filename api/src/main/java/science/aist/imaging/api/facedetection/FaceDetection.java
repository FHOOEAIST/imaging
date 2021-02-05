/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.facedetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Collection;

/**
 * @author Andreas Pointner
 * @since 1.0
 */
public interface FaceDetection<T> {
    /**
     * Detects a face in the image, and returns a FaceInformation object to describe it.
     *
     * @param ji the image where the face should be detected
     * @return the information about the detected faces
     */
    Collection<FaceInformation<T>> detectFaces(ImageWrapper<T> ji);

    /**
     * Calculates a confidence value, if there is the same person on img1 and on img2
     *
     * @param face1 first face
     * @param face2 second face
     * @return confidence value if the two faces on the images are the same
     */
    double verifyFace(FaceInformation<T> face1, FaceInformation<T> face2);
}
