/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.objectdetection;

import lombok.*;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Abstract class for detecting object based on a features</p>
 *
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <R> Type of Rectangle wrapped by RectangleWrapper
 * @author Christoph Praschl
 * @since 1.0
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractFeaturebasedObjectDetector<I, P, R> extends AbstractObjectDetector<I, P, R> {
    @NonNull
    protected ImageWrapper<I> objectReferenceImage;
    protected int minNumberMatchingFeatures = 10;
    protected double threshold = 2.0;
}
