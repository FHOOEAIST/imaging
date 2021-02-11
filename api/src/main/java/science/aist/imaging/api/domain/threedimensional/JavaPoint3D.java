/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import science.aist.imaging.api.domain.AbstractJavaPoint;

/**
 * <p>Java Representation of a 3D - point.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class JavaPoint3D extends AbstractJavaPoint<JavaPoint3D> {
    public JavaPoint3D(double x, double y) {
        this(x, y, 0);
    }

    public JavaPoint3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Subtracts the given JavaPoint from this.
     *
     * @param vector The JavaPoint to be subtracted from this
     * @return A new instance holding the result of the vector subtraction
     */
    public JavaPoint3D sub(JavaPoint3D vector) {
        return new JavaPoint3D(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    /**
     * Adds the given JavaPoint to this.
     *
     * @param vector The JavaPoint to be added to this
     * @return A new instance holding the result of the vector addition
     */
    public JavaPoint3D add(JavaPoint3D vector) {
        return new JavaPoint3D(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    /**
     * Multiplies this by the given scalar.
     *
     * @param scalar The scalar to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public JavaPoint3D mult(double scalar) {
        return mult(new JavaPoint3D(scalar, scalar, scalar));
    }

    /**
     * Multiplies this by the given vector.
     *
     * @param vector The vector to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public JavaPoint3D mult(JavaPoint3D vector) {
        return new JavaPoint3D(this.x * vector.getX(), this.y * vector.getY(), this.z * vector.getZ());
    }

    /**
     * Divides this by the given scalar.
     *
     * @param scalar The scalar to by which this should be divided
     * @return A new instance holding the result of the division
     */
    public JavaPoint3D div(double scalar) {
        if (!(scalar < 0 || scalar > 0)) {
            throw new IllegalArgumentException("Scalar must not be 0. Division by 0 not defined");
        }
        return new JavaPoint3D(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    /**
     * @return Returns a new instance holding the unit vector of this
     */
    @Override
    protected JavaPoint3D calculateUnify() {
        double norm = 1.0 / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return new JavaPoint3D(this.x * norm, this.y * norm, this.z * norm);
    }


    /**
     * Computes the dot product of this and the given JavaPoint.
     *
     * @param vector The JavaPoint to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public double dot(JavaPoint3D vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }


    /**
     * Rotates the point around the origin (0,0,0)
     *
     * @param roll  Rotation around x-axis (in degrees)
     * @param pitch Rotation around y-axis (in degrees)
     * @param yaw   Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaPoint3D rotate(double roll, double pitch, double yaw) {
        return rotate(new JavaPoint3D(0, 0, 0), roll, pitch, yaw);
    }

    /**
     * @param b the second vector
     * @return the cross product c = a x b, where a == this
     */
    public JavaPoint3D crossProduct(JavaPoint3D b) {
        double cx = this.y * b.z - this.z * b.y;
        double cy = this.z * b.x - this.x * b.z;
        double cz = this.x * b.y - this.y * b.x;

        return new JavaPoint3D(cx, cy, cz);
    }

    /**
     * Rotates the point around the given origin
     *
     * @param origin origin point around which will be rotated.
     * @param roll   Rotation around x-axis (in degrees)
     * @param pitch  Rotation around y-axis (in degrees)
     * @param yaw    Rotation around z-axis (in degrees)
     * @return Returns a new, rotated instance
     */
    public JavaPoint3D rotate(JavaPoint3D origin, double roll, double pitch, double yaw) {
        double currX = this.getX() - origin.getX();
        double currY = this.getY() - origin.getY();
        double currZ = this.getZ() - origin.getZ();

        double rRoll = Math.toRadians(roll);
        double rPitch = Math.toRadians(pitch);
        double rYaw = Math.toRadians(yaw);

        double cosa = Math.cos(rYaw);
        double sina = Math.sin(rYaw);

        double cosb = Math.cos(rPitch);
        double sinb = Math.sin(rPitch);

        double cosc = Math.cos(rRoll);
        double sinc = Math.sin(rRoll);

        double axx = cosa * cosb;
        double axy = cosa * sinb * sinc - sina * cosc;
        double axz = cosa * sinb * cosc + sina * sinc;

        double ayx = sina * cosb;
        double ayy = sina * sinb * sinc + cosa * cosc;
        double ayz = sina * sinb * cosc - cosa * sinc;

        double azx = -sinb;
        double azy = cosb * sinc;
        double azz = cosb * cosc;

        double xR = (axx * currX + axy * currY + axz * currZ) + origin.getX();
        double yR = (ayx * currX + ayy * currY + ayz * currZ) + origin.getY();
        double zR = (azx * currX + azy * currY + azz * currZ) + origin.getZ();

        return new JavaPoint3D(xR, yR, zR);
    }
}
