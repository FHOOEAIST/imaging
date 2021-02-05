/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

/**
 * <p>
 * Abstract generic implementation of the Point3Wrapper interface.
 * Getter and Setter for coordinate-values (not for the wrapped point) have to be overwritten in inherited class!
 * </p>
 *
 * @author Christoph Praschl
 * @since 1.0
 * @param <P> The type of the point
 */
public abstract class AbstractPoint3Wrapper<P> extends AbstractPoint2Wrapper<P> implements Point3Wrapper<P> {
    public AbstractPoint3Wrapper(P point) {
        super(point);
    }
}
