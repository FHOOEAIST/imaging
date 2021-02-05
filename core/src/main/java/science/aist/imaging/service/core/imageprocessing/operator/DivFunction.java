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
 * <p>Function that divides two images or a scalar value and an image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class DivFunction<I> extends AbstractOperator<I> {
    @Override
    protected double execute(double val1, double val2) {
        return val1 / val2;
    }
}
