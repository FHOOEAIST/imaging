/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import lombok.NonNull;
import science.aist.imaging.api.domain.AbstractJavaPointCloud;
import science.aist.jack.general.util.CastUtils;
import science.aist.jack.math.MathUtils;

import java.util.List;

/**
 * <p>3D point cloud</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaPointCloud3D extends AbstractJavaPointCloud<JavaPoint3D> {

    public JavaPointCloud3D(@NonNull List<JavaPoint3D> points) {
        super(points);
    }

    /**
     * Subtracts the given JavaPoint from the point cloud.
     *
     * @param vector The JavaPoint to be subtracted from the point cloud
     * @return A new instance holding the result of the subtraction
     */
    public JavaPointCloud3D sub(JavaPoint3D vector) {
        return transformParallel(point -> point.sub(vector));
    }

    /**
     * Adds the given JavaPoint to the point cloud.
     *
     * @param vector The JavaPoint to be added to the point cloud
     * @return A new instance holding the result of the addition
     */
    public JavaPointCloud3D add(JavaPoint3D vector) {
        return transformParallel(point -> point.add(vector));
    }

    /**
     * Multiplies the point cloud with the given scalar.
     *
     * @param vector The multiplied vector
     * @return A new instance holding the result of the multiplication
     */
    public JavaPointCloud3D mult(JavaPoint3D vector) {
        return transformParallel(point -> point.mult(vector));
    }

    /**
     * Divides the point cloud by the given scalar.
     *
     * @param scalar The scalar used for the division of the point cloud
     * @return A new instance holding the result of the division
     */
    public JavaPointCloud3D div(double scalar) {
        return transformParallel(point -> point.div(scalar));
    }

    /**
     * Rotates the point cloud around the center point.
     *
     * @param roll  Rotation around x-axis (in degrees)
     * @param pitch Rotation around y-axis (in degrees)
     * @param yaw   Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaPointCloud3D rotateAroundCenter(double roll, double pitch, double yaw) {
        return rotate(getCenterPoint(), roll, pitch, yaw);
    }

    /**
     * Rotates the point cloud around the given origin
     *
     * @param origin origin point around which will be rotated.
     * @param roll   Rotation around x-axis (in degrees)
     * @param pitch  Rotation around y-axis (in degrees)
     * @param yaw    Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaPointCloud3D rotate(JavaPoint3D origin, double roll, double pitch, double yaw) {
        return transformParallel(point -> point.rotate(origin, roll, pitch, yaw));
    }

    /**
     * Scales the given JavaPointCloud around the center point
     *
     * @param scaleFactors vector containing the scale values
     * @return new scaled instance
     */
    public JavaPointCloud3D scale(JavaPoint3D scaleFactors) {
        return scale(scaleFactors, getCenterPoint());
    }

    /**
     * Scales the given JavaPointCloud around the given scale center point
     *
     * @param scaleFactors vector containing the scale values
     * @param scaleCenter  the scaleCenter
     * @return new scaled instance
     */
    public JavaPointCloud3D scale(JavaPoint3D scaleFactors, JavaPoint3D scaleCenter) {
        if (MathUtils.equals(scaleFactors.getX(), 0.0)) {
            throw new IllegalArgumentException("xScale must not be Zero");
        }

        if (MathUtils.equals(scaleFactors.getY(), 0.0)) {
            throw new IllegalArgumentException("yScale must not be Zero");
        }

        if (MathUtils.equals(scaleFactors.getZ(), 0.0)) {
            throw new IllegalArgumentException("zScale must not be Zero");
        }

        return transformParallel(point -> point.sub(scaleCenter).mult(scaleFactors).add(scaleCenter));
    }

    @Override
    protected JavaPoint3D calculateCenterPoint() {
        JavaPoint3D cPoint = new JavaPoint3D();

        for (JavaPoint3D point : getPoints()) {
            cPoint = cPoint.add(point);
        }

        return cPoint.div(getPoints().size());
    }

    @Override
    protected <L extends AbstractJavaPointCloud<JavaPoint3D>> L createPointCloud(List<JavaPoint3D> point3DS) {
        return CastUtils.cast(new JavaPointCloud3D(point3DS));
    }
}
