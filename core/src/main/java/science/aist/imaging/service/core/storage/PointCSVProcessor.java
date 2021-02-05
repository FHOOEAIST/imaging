/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.storage;

import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.jack.persistence.filesystem.implementation.ReflectionCSVProcessor;

import java.util.Arrays;

/**
 * <p>Simple implementation of CSVProcessor for {@link JavaPoint3D} with a csv files with the columns (1) x,  (2) y and (z)</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class PointCSVProcessor extends ReflectionCSVProcessor<JavaPoint3D> {
    public PointCSVProcessor(char separator) {
        super(separator, Arrays.asList("x", "y", "z"), JavaPoint3D.class);
    }
}

