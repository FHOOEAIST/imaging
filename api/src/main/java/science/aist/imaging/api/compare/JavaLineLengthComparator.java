/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.compare;


import science.aist.imaging.api.domain.twodimensional.JavaLine2D;

import java.io.Serializable;
import java.util.Comparator;

/**
 * <p>Comparator for {@link JavaLine2D} based on the lines´ length</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaLineLengthComparator implements Comparator<JavaLine2D>, Serializable {
    @Override
    public int compare(JavaLine2D o1, JavaLine2D o2) {
        return Double.compare(o1.length(), o2.length());
    }
}

