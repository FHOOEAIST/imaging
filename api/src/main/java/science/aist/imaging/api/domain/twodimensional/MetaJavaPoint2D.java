/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import lombok.NoArgsConstructor;
import science.aist.jack.general.util.CastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>JavaPoint which is extended by Meta Information</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@NoArgsConstructor
public class MetaJavaPoint2D extends JavaPoint2D {
    private final Map<String, Object> metaInformation = new HashMap<>();

    /**
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public MetaJavaPoint2D(double x, double y) {
        super(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MetaJavaPoint2D that = (MetaJavaPoint2D) o;
        final double EPSILON = 0.00000001f;
        return Math.abs(getX() - that.getX()) < EPSILON &&
                Math.abs(getY() - that.getY()) < EPSILON &&
                Objects.equals(metaInformation, that.metaInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), metaInformation);
    }

    /**
     * Returns the meta information for a specific key and casts it to &lt;T&gt;
     *
     * @param key the key of the meta information
     * @param <T> the type of the value
     * @return the value if the key exists, else null
     */
    public <T> T getMetaInfo(String key) {
        Object val = metaInformation.get(key);
        if (val == null) return null;
        return CastUtils.cast(val);
    }

    /**
     * Sets a meta information element
     *
     * @param key the key of the meta information
     * @param val the value of the meta information
     * @param <T> the type of the value
     */
    public <T> void setMetaInfo(String key, T val) {
        metaInformation.put(key, val);
    }
}
