/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import science.aist.imaging.api.domain.AbstractJavaLine;

/**
 * <p>Threedimensional line between two 3D points</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaLine3D extends AbstractJavaLine<JavaPoint3D> {

    public JavaLine3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(new JavaPoint3D(x1, y1, z1), new JavaPoint3D(x2, y2, z2));
    }

    /**
     * set line by start and end point
     *
     * @param startPoint the start point of the line
     * @param endPoint   the end point of the line
     */
    @SuppressWarnings("WeakerAccess") // this framework is also used in other projects
    public JavaLine3D(JavaPoint3D startPoint, JavaPoint3D endPoint) {
        super(startPoint, endPoint);
    }

    /**
     * <p>
     * Calculate the center point of a given line <br>
     * x = (start.x + end.x) / 2 <br>
     * y = (start.y + end.y) / 2 <br>
     * z = (start.z + end.z) / 2
     * </p>
     *
     * @return center point of the line
     */
    @Override
    protected JavaPoint3D calculateCenterPoint() {
        return new JavaPoint3D((getStartPoint().getX() + getEndPoint().getX()) / 2,
                (getStartPoint().getY() + getEndPoint().getY()) / 2, (getStartPoint().getZ() + getEndPoint().getZ()) / 2);
    }

    /**
     * Method for getting a point along this line with the given distance from the startPoint
     * (Based on https://math.stackexchange.com/questions/175896/finding-a-point-along-a-line-a-certain-distance-away-from-another-point)
     *
     * @param distance from the startPoint
     * @return the point with the given distance
     */
    @Override
    public JavaPoint3D getPointAlongLine(double distance) {
        return getStartPoint().add(getEndPoint().sub(getStartPoint()).getUnify().mult(distance));
    }
}
