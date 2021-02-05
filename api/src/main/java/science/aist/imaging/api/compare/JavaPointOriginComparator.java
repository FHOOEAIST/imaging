/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;

import science.aist.imaging.api.domain.AbstractJavaPoint;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;

/**
 * <p>Compares two {@link JavaPoint2D}s based on their distance to the given origin</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaPointOriginComparator implements Comparator<JavaPoint2D>, Serializable {
    @Getter
    @Setter
    private JavaPoint2D origin = new JavaPoint2D(0, 0);

    /**
     * @param o1 point 1
     * @param o2 point 2
     * @return a value &lt; 0 if o1 is closer to origin than o2
     * == 0 if o1 and o2 have the same distance to origin
     * &gt; 0 if o1 is further away from origin than o2
     */
    @Override
    public int compare(JavaPoint2D o1, JavaPoint2D o2) {
        return Double.compare(AbstractJavaPoint.pointDistance(origin, o1), AbstractJavaPoint.pointDistance(origin, o2));
    }
}
