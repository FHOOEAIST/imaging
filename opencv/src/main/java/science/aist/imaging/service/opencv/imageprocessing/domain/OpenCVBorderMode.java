/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opencv.core.Core;

/**
 * <p>opencv border mode types</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum OpenCVBorderMode {
    BORDER_CONSTANT(Core.BORDER_CONSTANT),
    BORDER_REPLICATE(Core.BORDER_REPLICATE),
    BORDER_TRANSPARENT(Core.BORDER_TRANSPARENT),
    BORDER_ISOLATED(Core.BORDER_ISOLATED),
    BORDER_DEFAULT(Core.BORDER_DEFAULT),
    BORDER_REFLECT(Core.BORDER_REFLECT),
    BORDER_REFLECT101(Core.BORDER_REFLECT101),
    BORDER_REFLECT_101(Core.BORDER_REFLECT_101),
    BORDER_WRAP(Core.BORDER_WRAP);

    /**
     * OpenCV borderType
     */
    private final int borderType;
}
