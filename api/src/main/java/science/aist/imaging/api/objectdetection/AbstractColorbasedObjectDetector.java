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
import science.aist.imaging.api.domain.color.ThreeChannelColor;

/**
 * <p>Abstract class for detecting object based on a given color range</p>
 *
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <C> Type of Color for detection
 * @param <R> Type of Rectangle wrapped by RectangleWrapper
 * @author Christoph Praschl
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractColorbasedObjectDetector<I, P, R, C extends ThreeChannelColor> extends AbstractObjectDetector<I, P, R> {
    /**
     * Field containing Low color value of the object which should be detected
     */
    protected C lowerBound;
    /**
     * Field containing High color value of the object which should be detected
     */
    protected C upperBound;
}
