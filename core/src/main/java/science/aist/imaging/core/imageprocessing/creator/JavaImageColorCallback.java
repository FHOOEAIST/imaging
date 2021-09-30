/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.creator;

import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.color.RGBColor;

/**
 * <p>Returns a java image color representation</p>
 *
 * @param <Input> Input Source
 * @author Andreas Pointner
 * @since 1.0
 */
@FunctionalInterface
public interface JavaImageColorCallback<Input> {
    /**
     * Returns a color representation for a given input
     *
     * @param input the input
     * @return the color representation. comp: {@link RGBColor}
     */
    Color getColorRepresentation(Input input);
}
