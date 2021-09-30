/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opencv.ximgproc.Ximgproc;
import science.aist.imaging.opencv.imageprocessing.morphology.ThinningFunction;

/**
 * <p>Thinning type used in {@link ThinningFunction}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum ThinningType {
    GUOHALL(Ximgproc.THINNING_GUOHALL), ZHANGSUEN(Ximgproc.THINNING_ZHANGSUEN);

    private int id;
}
