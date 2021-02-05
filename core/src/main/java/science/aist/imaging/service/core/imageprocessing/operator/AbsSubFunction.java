/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.operator;

/**
 * <p>Function that subtracts two images and returns the absolute values</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class AbsSubFunction<I> extends SubFunction<I> {
    @Override
    protected double execute(double val1, double val2) {
        return Math.abs(super.execute(val1, val2));
    }
}
