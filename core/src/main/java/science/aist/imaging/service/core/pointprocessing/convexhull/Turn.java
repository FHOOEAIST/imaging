/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing.convexhull;

/**
 * <p> An enum denoting a directional-turn between 3 points (vectors)./</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public enum Turn {
    CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR
}
