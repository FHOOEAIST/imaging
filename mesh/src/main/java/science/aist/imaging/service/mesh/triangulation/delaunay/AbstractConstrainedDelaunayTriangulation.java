/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.triangulation.delaunay;

import org.jdelaunay.delaunay.ConstrainedMesh;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.geometries.DEdge;
import org.jdelaunay.delaunay.geometries.DPoint;
import org.jdelaunay.delaunay.geometries.DTriangle;
import science.aist.imaging.api.domain.AbstractJavaLine;
import science.aist.imaging.api.domain.AbstractJavaPoint;
import science.aist.imaging.api.domain.AbstractJavaPolygon;
import science.aist.imaging.service.mesh.triangulation.Triangulation;
import science.aist.seshat.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Abstract implementation of a constrained delaunay triangulation process </p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public abstract class AbstractConstrainedDelaunayTriangulation<P extends AbstractJavaPoint<P>, L extends AbstractJavaLine<P>, G extends AbstractJavaPolygon<P, L>> implements Triangulation<P, L, G> {
    private static final Logger logger = Logger.getInstance(AbstractConstrainedDelaunayTriangulation.class);
    private final Function<P, DPoint> pointConversionFunction = p -> {
        try {
            return new DPoint(p.getX(), p.getY(), p.getZ());
        } catch (DelaunayError delaunayError) {
            logger.debug("Could not convert given point: " + p);
            return null;
        }
    };
    private final Function<L, DEdge> lineConversionFunction = l -> new DEdge(pointConversionFunction.apply(l.getStartPoint()), pointConversionFunction.apply(l.getEndPoint()));

    private Function<List<DPoint>, G> polygonFunction;
    private final Function<DTriangle, G> triangleConversionFunction = t -> polygonFunction.apply(t.getPoints());


    /**
     * Flag that decides if debug level should be used
     */
    private final boolean verboseMode;

    /**
     * <p></p>Force the integrity of the constraints used to compute the delaunay triangulation:</p>
     * <ul>
     * <li>duplicates are removed</li>
     * <li>intersection points are added to the mesh points</li>
     * <li>secant edges are split</li>
     * </ul>
     */
    private final boolean forceMode;

    protected AbstractConstrainedDelaunayTriangulation(Function<List<DPoint>, G> polygonFunction, boolean verboseMode, boolean forceMode) {
        this.polygonFunction = polygonFunction;
        this.verboseMode = verboseMode;
        this.forceMode = forceMode;
    }

    @Override
    public List<G> triangulate(List<P> points) {
        return triangulate(points, Collections.emptyList());
    }

    /**
     * Triangulates the given points using the given constraings
     *
     * @param points to be triangulated
     * @param constraints constraints to be considered
     * @return Triangulation result
     */
    public List<G> triangulate(List<P> points, List<L> constraints) {
        try {
            ConstrainedMesh mesh = new ConstrainedMesh();
            mesh.setVerbose(verboseMode);

            for (L constraint : constraints) {
                mesh.addConstraintEdge(lineConversionFunction.apply(constraint));
            }

            for (P point : points) {
                mesh.addPoint(pointConversionFunction.apply(point));
            }


            if (forceMode) {
                mesh.forceConstraintIntegrity();
            }

            mesh.processDelaunay();

            return mesh.getTriangleList().stream().map(triangleConversionFunction).collect(Collectors.toList());
        } catch (DelaunayError delaunayError) {
            logger.debug("Delaunay triangulation failed: " + delaunayError.getMessage());
            return Collections.emptyList();
        }
    }
}
