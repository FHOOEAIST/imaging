/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.triangulation.delaunay.impl;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.service.mesh.triangulation.delaunay.AbstractConstrainedDelaunayTriangulation;

import java.util.stream.Collectors;

/**
 * <p>Implementation of a constrained delaunay triangulation process for 2D points</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class ConstrainedDelaunayTriangulation2D extends AbstractConstrainedDelaunayTriangulation<JavaPoint2D, JavaLine2D, JavaPolygon2D> {
    public ConstrainedDelaunayTriangulation2D() {
        this(false, false);
    }

    public ConstrainedDelaunayTriangulation2D(boolean verboseMode, boolean forceMode) {
        super(dPoints -> new JavaPolygon2D(dPoints.stream().map(p -> new JavaPoint2D(p.getX(), p.getY())).collect(Collectors.toList())), verboseMode, forceMode);
    }
}
