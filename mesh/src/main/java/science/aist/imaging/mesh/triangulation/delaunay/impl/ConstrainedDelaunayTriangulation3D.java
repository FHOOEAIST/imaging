/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.mesh.triangulation.delaunay.impl;

import science.aist.imaging.api.domain.threedimensional.JavaLine3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.domain.threedimensional.JavaPolygon3D;
import science.aist.imaging.mesh.triangulation.delaunay.AbstractConstrainedDelaunayTriangulation;

import java.util.stream.Collectors;

/**
 * <p>Implementation of a constrained delaunay triangulation process for 3D points</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class ConstrainedDelaunayTriangulation3D extends AbstractConstrainedDelaunayTriangulation<JavaPoint3D, JavaLine3D, JavaPolygon3D> {
    public ConstrainedDelaunayTriangulation3D() {
        this(false, false);
    }


    public ConstrainedDelaunayTriangulation3D(boolean verboseMode, boolean forceMode) {
        super(dPoints -> new JavaPolygon3D(dPoints.stream().map(p -> new JavaPoint3D(p.getX(), p.getY(), p.getZ())).collect(Collectors.toList())), verboseMode, forceMode);
    }
}
