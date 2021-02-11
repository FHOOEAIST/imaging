/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.facedetection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class FaceInformation<T> {
    /**
     * Id of the detected face, this could be null
     */
    private String id;
    /**
     * The bounding box, where the face was detected
     */
    private JavaRectangle2D boundingBox;

    /**
     * The image which contains the face.
     */
    private ImageWrapper<T> image;
}
