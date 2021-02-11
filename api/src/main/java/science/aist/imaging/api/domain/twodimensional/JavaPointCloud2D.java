/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import lombok.NonNull;
import science.aist.imaging.api.domain.AbstractJavaPointCloud;
import science.aist.jack.general.util.CastUtils;
import science.aist.jack.math.MathUtils;

import java.util.List;

/**
 * <p>2D Point cloud</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaPointCloud2D extends AbstractJavaPointCloud<JavaPoint2D> {

    public JavaPointCloud2D(@NonNull List<JavaPoint2D> points) {
        super(points);
    }

    /**
     * Subtracts the given JavaPoint from the point cloud.
     *
     * @param vector The JavaPoint to be subtracted from the point cloud
     * @return A new instance holding the result of the subtraction
     */
    public JavaPointCloud2D sub(JavaPoint2D vector) {
        return transformParallel(point -> point.sub(vector));
    }

    /**
     * Adds the given JavaPoint to the point cloud.
     *
     * @param vector The JavaPoint to be added to the point cloud
     * @return A new instance holding the result of the addition
     */
    public JavaPointCloud2D add(JavaPoint2D vector) {
        return transformParallel(point -> point.add(vector));
    }

    /**
     * Multiplies the point cloud with the given scalar.
     *
     * @param vector The multiplied vector
     * @return A new instance holding the result of the multiplication
     */
    public JavaPointCloud2D mult(JavaPoint2D vector) {
        return transformParallel(point -> point.mult(vector));
    }

    /**
     * Divides the point cloud by the given scalar.
     *
     * @param scalar The scalar used for the division of the point cloud
     * @return A new instance holding the result of the division
     */
    public JavaPointCloud2D div(double scalar) {
        return transformParallel(point -> point.div(scalar));
    }

    /**
     * Rotates the point cloud around the center point.
     *
     * @param rotation radians to rotate the points
     * @return Returns a new, rotated instance
     */
    public JavaPointCloud2D rotateAroundCenter(double rotation) {
        return rotate(rotation, getCenterPoint());
    }

    /**
     * Rotates the point cloud around the given origin
     *
     * @param origin   origin point around which will be rotated.
     * @param rotation the radians to rotate the points
     * @return Returns a new, rotated instance
     */
    public JavaPointCloud2D rotate(double rotation, JavaPoint2D origin) {
        return transformParallel(point -> point.rotate(rotation, origin));
    }

    /**
     * Scales the given JavaPointCloud around the center point
     *
     * @param scaleFactors vector containing the scale values
     * @return new scaled instance
     */
    public JavaPointCloud2D scale(JavaPoint2D scaleFactors) {
        return scale(scaleFactors, getCenterPoint());
    }

    /**
     * Scales the given JavaPointCloud around the given scale center point
     *
     * @param scaleFactors vector containing the scale values
     * @param scaleCenter  the scaleCenter
     * @return new scaled instance
     */
    public JavaPointCloud2D scale(JavaPoint2D scaleFactors, JavaPoint2D scaleCenter) {
        if (MathUtils.equals(scaleFactors.getX(), 0.0)) {
            throw new IllegalArgumentException("xScale must not be Zero");
        }

        if (MathUtils.equals(scaleFactors.getY(), 0.0)) {
            throw new IllegalArgumentException("yScale must not be Zero");
        }

        return transformParallel(point -> point.sub(scaleCenter).mult(scaleFactors).add(scaleCenter));
    }

    @Override
    protected JavaPoint2D calculateCenterPoint() {
        JavaPoint2D cPoint = new JavaPoint2D();

        for (JavaPoint2D point : getPoints()) {
            cPoint = cPoint.add(point);
        }

        return cPoint.div(getPoints().size());
    }

    @Override
    protected <L extends AbstractJavaPointCloud<JavaPoint2D>> L createPointCloud(List<JavaPoint2D> point3DS) {
        return CastUtils.cast(new JavaPointCloud2D(point3DS));
    }
}
