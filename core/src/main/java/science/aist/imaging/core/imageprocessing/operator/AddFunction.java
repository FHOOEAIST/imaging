/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.operator;

import lombok.Setter;

/**
 * <p>Function that adds to images in an optional weighted way</p>
 * <p>res[x,y,c] = image1[x,y,c] * alpha + image2[x,y,c] * beta + gamma</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class AddFunction<I> extends AbstractOperator<I> {

    @Setter
    private double alpha = 1.0;

    @Setter
    private double beta = 1.0;

    @Setter
    private double gamma = 0.0;

    @Override
    protected double execute(double val1, double val2) {
        return (val1 * alpha) + (val2 * beta) + gamma;
    }
}
