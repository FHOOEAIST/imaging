/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;


/**
 * <p>Interface of a Triangle</p>
 * <pre>
 * A
 * | \
 * B--C
 * </pre>
 *
 * @param <T> The type of the point
 * @author Christoph Praschl
 * @since 1.0
 */
public interface Triangle<T extends AbstractJavaPoint<T>> {
    /**
     * @return triangle point A
     */
    T getA();

    /**
     * @return triangle point B
     */

    T getB();

    /**
     * @return triangle point C
     */
    T getC();
}
