/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.objectdetection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Abstract class for detecting objects based on differences in image.
 * Detects Object based on differences between the reference image and the input image</p>
 *
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <R> Type of Rectangle wrapped by RectangleWrapper
 * @author Christoph Praschl
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractDifferencebasedObjectDetector<I, P, R> extends AbstractObjectDetector<I, P, R> {
    protected ImageWrapper<I> referenceImage;
}
