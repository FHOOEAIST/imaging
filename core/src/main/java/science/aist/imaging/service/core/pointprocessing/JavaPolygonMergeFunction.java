/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing;

import lombok.Setter;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.seshat.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Merges two Java Polygons</p>
 *
 * @author Andreas Pointner
 */
@Setter
public class JavaPolygonMergeFunction implements BiFunction<JavaPolygon2D, JavaPolygon2D, Optional<JavaPolygon2D>> {
    protected static final Logger logger = Logger.getInstance();

    /**
     * Ratio that two java polygon intersect to be merged.
     */
    private double intersectionRatio = 0.25;

    @Override
    public Optional<JavaPolygon2D> apply(JavaPolygon2D javaPolygon2D, JavaPolygon2D javaPolygon2D2) {
        JavaPolygon2D intersection = JavaPolygon2D.getIntersection(javaPolygon2D, javaPolygon2D2);
        double areaJP1 = javaPolygon2D.getArea();
        double areaJP2 = javaPolygon2D2.getArea();
        double areaInt = intersection.getArea();
        double ratioJp1 = areaInt / areaJP1;
        double ratioJp2 = areaInt / areaJP2;
        if (ratioJp1 > intersectionRatio || ratioJp2 > intersectionRatio) {
            List<JavaPoint2D> firstPoints = javaPolygon2D.getPoints()
                    .stream()
                    .filter(((Predicate<? super JavaPoint2D>) intersection.getPoints()::contains).negate())
                    .collect(Collectors.toList());

            List<JavaPoint2D> secondPoints = javaPolygon2D2.getPoints()
                    .stream()
                    .filter(((Predicate<? super JavaPoint2D>) intersection.getPoints()::contains).negate())
                    .collect(Collectors.toList());

            List<JavaPoint2D> bresenham1 = javaPolygon2D
                    .getContour()
                    .stream()
                    .map(JavaLine2D::getBresenham)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            List<JavaPoint2D> bresenham2 = javaPolygon2D2
                    .getContour()
                    .stream()
                    .map(JavaLine2D::getBresenham)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            List<JavaPoint2D> intersectionPoints = intersection
                    .getPoints()
                    .stream()
                    .filter(bresenham1::contains)
                    .filter(bresenham2::contains)
                    .collect(Collectors.toList());

            JavaPoint2D[] javaPoint2Ds = Stream
                    .of(firstPoints, secondPoints, intersectionPoints)
                    .flatMap(Collection::stream)
                    .toArray(JavaPoint2D[]::new);
            return Optional.of(JavaPolygon2D.getPolygonFromUnsortedPointCloud(javaPoint2Ds));
        } else {
            logger.debug("Intersection ratio too low to merge polygons Ratio Jp1: {}, Ratio Jp2: {}; Required Ratio: {}", ratioJp1, ratioJp2, intersectionRatio);
            return Optional.empty();
        }
    }
}
