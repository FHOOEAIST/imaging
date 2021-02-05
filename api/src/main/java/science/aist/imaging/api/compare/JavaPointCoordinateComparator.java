/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;


import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;

/**
 * <p>Compares two {@link JavaPoint2D}s based on their x-coordinate respectively the y-coordinate if the x-coordinate is equal</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaPointCoordinateComparator implements Comparator<JavaPoint2D>, Serializable {

    @Getter
    @Setter
    private double epsilon = 0.00000001f;

    /**
     * @param o1 point 1
     * @param o2 point 2
     * @return a value &lt; 0 if o1 is on coordinate system more left or lower than o2
     * == 0 if o1 and o2 are equal
     * &gt; 0 if o1 is on coordinate system more right or upper than o2
     */
    @Override
    public int compare(JavaPoint2D o1, JavaPoint2D o2) {
        double x1 = o1.getX();
        double x2 = o2.getX();
        if (Math.abs(x1 - x2) < epsilon) {
            double y1 = o1.getY();
            double y2 = o2.getY();

            return Double.compare(y1, y2);
        } else {
            return Double.compare(x1, x2);
        }
    }
}