/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.triangulation;

import science.aist.imaging.api.domain.AbstractJavaLine;
import science.aist.imaging.api.domain.AbstractJavaPoint;
import science.aist.imaging.api.domain.AbstractJavaPolygon;

import java.util.List;

/**
 * <p>Interface for writing a mesh to a file</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
@FunctionalInterface
public interface Triangulation<P extends AbstractJavaPoint<P>, L extends AbstractJavaLine<P>, G extends AbstractJavaPolygon<P, L>> {
    /**
     * Triangulates a given list of points
     * @param points to be triangulated
     * @return triangulation result
     */
    List<G> triangulate(List<P> points);
}