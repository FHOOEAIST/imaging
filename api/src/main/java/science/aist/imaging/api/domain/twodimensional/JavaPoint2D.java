/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;


import lombok.NoArgsConstructor;
import science.aist.imaging.api.domain.AbstractJavaPoint;

/**
 * <p>Java Representation of a point. There is a Point in java.awt.Point available, but this is a int representation
 * internal. There are some use-cases, where a double representation is needed.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@NoArgsConstructor
public class JavaPoint2D extends AbstractJavaPoint<JavaPoint2D> {
    /**
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public JavaPoint2D(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    /**
     * Subtracts the given JavaPoint from this.
     *
     * @param vector The JavaPoint to be subtracted from this
     * @return A new instance holding the result of the vector subtraction
     */
    @Override
    public JavaPoint2D sub(JavaPoint2D vector) {
        return new JavaPoint2D(this.getX() - vector.getX(), this.getY() - vector.getY());
    }

    /**
     * Adds the given JavaPoint to this.
     *
     * @param vector The JavaPoint to be added to this
     * @return A new instance holding the result of the vector addition
     */
    @Override
    public JavaPoint2D add(JavaPoint2D vector) {
        return new JavaPoint2D(this.getX() + vector.getX(), this.getY() + vector.getY());
    }

    /**
     * Multiplies this by the given scalar.
     *
     * @param scalar The scalar to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    @Override
    public JavaPoint2D mult(double scalar) {
        return mult(new JavaPoint2D(scalar, scalar));
    }

    /**
     * Multiplies this by the given vector.
     *
     * @param vector The vector to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    @Override
    public JavaPoint2D mult(JavaPoint2D vector) {
        return new JavaPoint2D(this.getX() * vector.getX(), this.getY() * vector.getY());
    }

    /**
     * Divides this by the given scalar.
     *
     * @param scalar The scalar to by which this should be divided
     * @return A new instance holding the result of the division
     */
    @Override
    public JavaPoint2D div(double scalar) {
        if (!(scalar < 0 || scalar > 0)) {
            throw new IllegalArgumentException("Scalar must not be 0. Division by 0 not defined");
        }
        return new JavaPoint2D(this.getX() / scalar, this.getY() / scalar);
    }

    /**
     * Computes the dot product of this and the given JavaPoint.
     *
     * @param vector The JavaPoint to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    @Override
    public double dot(JavaPoint2D vector) {
        return this.getX() * vector.getX() + this.getY() * vector.getY();
    }

    /**
     * Computes the 2D pseudo cross product Dot(Perp(this), JavaPoint) of this and
     * the given JavaPoint.
     *
     * @param vector The JavaPoint to be multiplied to the perpendicular vector of  this
     * @return A new instance holding the result of the pseudo cross product
     */
    public double cross(JavaPoint2D vector) {
        return this.getY() * vector.getX() - this.getX() * vector.getY();
    }

    /**
     * Rotates a Java Point around the origin (0, 0)
     *
     * @param radians the radians to rotate the point
     * @return the resulting rotated point
     * @see <a href="https://en.wikipedia.org/wiki/Rotation_matrix#In_two_dimensions">https://en.wikipedia.org/wiki/Rotation_matrix#In_two_dimensions</a>
     */
    public JavaPoint2D rotate(double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new JavaPoint2D(getX() * cos - getY() * sin, getX() * sin + getY() * cos);
    }

    /**
     * Rotates a Java Point around a given origin
     *
     * @param radians the radians to rotate the point
     * @param origin  the origin where the point is rotated around
     * @return the resulting rotated point
     */
    public JavaPoint2D rotate(double radians, JavaPoint2D origin) {
        return sub(origin).rotate(radians).add(origin);
    }

    /**
     * @return Returns a new instance holding the unit vector of this
     */
    @Override
    protected JavaPoint2D calculateUnify() {
        double norm = 1.0 / Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
        return new JavaPoint2D(this.getX() * norm, this.getY() * norm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaPoint2D that = (JavaPoint2D) o;

        final double EPSILON = 0.00000001f;
        return Math.abs(getX() - that.getX()) < EPSILON &&
                Math.abs(getY() - that.getY()) < EPSILON;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + getIntX();
        hash = 71 * hash + getIntY();
        return hash;
    }
}
