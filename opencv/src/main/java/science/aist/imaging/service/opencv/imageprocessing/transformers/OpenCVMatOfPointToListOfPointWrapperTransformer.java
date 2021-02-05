/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import science.aist.jack.general.transformer.Transformer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Transforms between a {@link List} of {@link Point2Wrapper} of {@link Point} into a {@link MatOfPoint} and vice versa</p>
 *
 * @author Andreas Pointner
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVMatOfPointToListOfPointWrapperTransformer implements Transformer<List<Point2Wrapper<Point>>, MatOfPoint> {
    @Override
    public List<Point2Wrapper<Point>> transformTo(MatOfPoint matOfPoint) {
        return matOfPoint
                .toList()
                .stream()
                .map(OpenCVPoint2Wrapper::new)
                .collect(Collectors.toList());
    }

    @Override
    public MatOfPoint transformFrom(List<Point2Wrapper<Point>> point2Wrappers) {
        return new MatOfPoint(point2Wrappers.stream().map(Point2Wrapper::getPoint).toArray(Point[]::new));
    }
}
