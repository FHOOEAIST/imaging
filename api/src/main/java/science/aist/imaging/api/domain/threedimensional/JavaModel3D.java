/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import science.aist.imaging.api.domain.AbstractJavaPointCloud;
import science.aist.imaging.api.util.PointMinMaxFunction;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.jack.data.Pair;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * <p>Class which represents any 3D model given by a mesh of 3D polygons</p>
 * <p>The model only contains an internal state for the properties (width, length, height, roll, pitch, yaw).
 * For the actual state use e.g. VolumeBox3D#getBox from service-core using {@link JavaModel3D#points}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public class JavaModel3D {
    @Getter(lazy = true)
    private final JavaPoint3D centerPoint = calculateCenterPoint();
    private double width = -1;
    private double length = -1;
    private double height = -1;
    @Getter
    private double roll = 0.0;
    @Getter
    private double pitch = 0.0;
    @Getter
    private double yaw = 0.0;
    @Getter
    @NonNull
    private List<JavaPolygon3D> mesh;
    @Getter(lazy = true)
    private final List<JavaPoint3D> points = calculatePoints();

    /**
     * method which updates the internal state
     */
    private void calculateSizes() {
        if (width < 0 || length < 0 || height < 0) {
            PointMinMaxFunction<JavaPoint3D> function = new PointMinMaxFunction<>();
            Pair<JavaPoint3D, JavaPoint3D> apply = function.apply(getPoints());
            JavaPoint3D min = apply.getFirst();
            JavaPoint3D max = apply.getSecond();
            width = max.getX() - min.getX();
            length = max.getY() - min.getY();
            height = max.getZ() - min.getZ();
        }
    }

    /**
     * gets value of field {@link JavaModel3D#width} which represents the internal state. For the actual state use VolumeBox3D#getBox from service-core using {@link JavaModel3D#points}
     *
     * @return value of field width
     * @see JavaModel3D#width
     */
    public double getWidth() {
        calculateSizes();
        return width;
    }

    /**
     * gets value of field {@link JavaModel3D#length} which represents the internal state. For the actual state use VolumeBox3D#getBox from service-core using {@link JavaModel3D#points}
     *
     * @return value of field length
     * @see JavaModel3D#length
     */
    public double getLength() {
        calculateSizes();
        return length;
    }

    /**
     * gets value of field {@link JavaModel3D#height} which represents the internal state. For the actual state use VolumeBox3D#getBox from service-core using {@link JavaModel3D#points}
     *
     * @return value of field height
     * @see JavaModel3D#height
     */
    public double getHeight() {
        calculateSizes();
        return height;
    }

    private List<JavaPoint3D> calculatePoints() {
        return mesh.stream().flatMap(javaPolygon3D -> javaPolygon3D.getPoints().stream()).collect(Collectors.toList());
    }

    private JavaPoint3D calculateCenterPoint() {
        return new JavaPointCloud3D(getPoints()).getCenterPoint();
    }

    /**
     * Help method for applying a unary operator to all points of the mesh (polygon-wise) using the {@link AbstractJavaPointCloud} class
     *
     * @param function unary operator which is applied to every polygon in the mesh
     * @return a new instance holding the transformed model 3D
     */
    private JavaModel3D transformParallelPointcloudBased(UnaryOperator<JavaPointCloud3D> function) {
        return transformParallel(javaPolygon3D -> {
            JavaPointCloud3D javaPointCloud = function.apply(new JavaPointCloud3D(javaPolygon3D.getPoints()));
            return new JavaPolygon3D(javaPointCloud.getPoints());
        });
    }

    /**
     * Method for applying a unary operator to polygons of the mesh
     *
     * @param function unary operator which is applied to every polygon in the mesh
     * @return a new instance holding the transformed model 3D (Attention: internal status is lost)
     */
    public JavaModel3D transformParallel(UnaryOperator<JavaPolygon3D> function) {
        return new JavaModel3D(mesh.stream().parallel().map(function).collect(Collectors.toList()));
    }

    /**
     * Moves the model3D by the given vector
     *
     * @param vector the vector used for moving the model
     * @return A new instance holding the moved result
     */
    public JavaModel3D move(JavaPoint3D vector) {
        return transformParallelPointcloudBased(javaPoint3D -> javaPoint3D.add(vector));
    }

    /**
     * Rotates the model 3D around the origin (0,0,0)
     *
     * @param roll  Rotation around x-axis (in degrees)
     * @param pitch Rotation around y-axis (in degrees)
     * @param yaw   Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaModel3D rotate(double roll, double pitch, double yaw) {
        return rotate(new JavaPoint3D(0, 0, 0), roll, pitch, yaw);
    }

    /**
     * Rotates the Model3D around it's center {@link #centerPoint}
     *
     * @param roll  Rotation around x-axis (in degrees)
     * @param pitch Rotation around y-axis (in degrees)
     * @param yaw   Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaModel3D rotateAroundCenter(double roll, double pitch, double yaw) {
        return rotate(getCenterPoint(), roll, pitch, yaw);
    }

    /**
     * Rotates the model 3D around the given origin
     *
     * @param origin origin point around which will be rotated.
     * @param roll   Rotation around x-axis (in degrees)
     * @param pitch  Rotation around y-axis (in degrees)
     * @param yaw    Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaModel3D rotate(JavaPoint3D origin, double roll, double pitch, double yaw) {
        JavaModel3D javaModel3D = transformParallelPointcloudBased(javaPoint3D -> javaPoint3D.rotate(origin, roll, pitch, yaw));
        javaModel3D.roll += roll;
        javaModel3D.pitch += pitch;
        javaModel3D.yaw += yaw;
        if (width > 0) {
            javaModel3D.width = width;
            javaModel3D.height = height;
            javaModel3D.length = length;
        }
        return javaModel3D;
    }

    /**
     * Scales the given Model3D
     *
     * @param scaleFactor unified scale factor used for scaling the cloud
     * @return new scaled instance
     */
    public JavaModel3D scale(double scaleFactor) {
        return scale(new JavaPoint3D(scaleFactor, scaleFactor, scaleFactor));
    }

    /**
     * Scales the given Model3D
     *
     * @param scaleFactors vector containing the scale values
     * @return new scaled instance
     */
    public JavaModel3D scale(JavaPoint3D scaleFactors) {
        JavaPoint3D cPoint = getCenterPoint();
        JavaModel3D javaModel3D = transformParallelPointcloudBased(javaPointCloud -> javaPointCloud.scale(scaleFactors, cPoint));
        javaModel3D.roll = roll;
        javaModel3D.yaw = yaw;
        javaModel3D.pitch = pitch;

        if (width > 0) {
            javaModel3D.width = width * scaleFactors.getX();
            javaModel3D.length = length * scaleFactors.getY();
            javaModel3D.height = height * scaleFactors.getZ();
        }
        return javaModel3D;
    }
}
