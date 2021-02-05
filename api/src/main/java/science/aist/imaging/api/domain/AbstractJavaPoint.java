/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import lombok.Getter;
import lombok.ToString;
import science.aist.jack.general.util.CastUtils;
import science.aist.jack.math.MathUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Base class of JavaPoint</p>
 *
 * @param <T> The type of the point
 * @author Christoph Praschl
 * @since 1.0
 */
@ToString(of = {"x", "y", "z"})
@Getter
public abstract class AbstractJavaPoint<T extends AbstractJavaPoint<T>> implements Serializable {
    private static final double EPSILON = 0.00000001;
    @Getter(lazy = true)
    private final T unify = calculateUnify();
    /**
     * x-coordinate
     */
    protected double x;
    /**
     * y-coordinate
     */
    protected double y;
    /**
     * z-coordinate
     */
    protected double z;
    @Getter(lazy = true)
    private final double mag = calculateMag();

    /**
     * returns the 3D distance between two given points
     * sqrt((point1.x - point2.x)^2 + (point1.y - point2.y)^2 + (point1.z - point2.z)^2)
     *
     * @param p1 point1
     * @param p2 point2
     * @return the distance between two given points
     */
    public static double pointDistance(AbstractJavaPoint<?> p1, AbstractJavaPoint<?> p2) {
        double xDist = xDistance(p1, p2);
        double yDist = yDistance(p1, p2);
        double zDist = zDistance(p1, p2);
        return Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist);
    }

    /**
     * returns the absolute distance in x direction between two points.
     * abs(point1.x - point2.x)
     *
     * @param p1 point1
     * @param p2 point2
     * @return distance in x direction
     */
    public static double xDistance(AbstractJavaPoint<?> p1, AbstractJavaPoint<?> p2) {
        return Math.abs(p1.getX() - p2.getX());
    }

    /**
     * returns the absolute distance in y direction between two points.
     * abs(point1.y - point2.y)
     *
     * @param p1 point1
     * @param p2 point2
     * @return distance in y direction
     */
    public static double yDistance(AbstractJavaPoint<?> p1, AbstractJavaPoint<?> p2) {
        return Math.abs(p1.getY() - p2.getY());
    }

    /**
     * returns the absolute distance in x direction between two points.
     * abs(point1.x - point2.x)
     *
     * @param p1 point1
     * @param p2 point2
     * @return distance in x direction
     */
    public static double zDistance(AbstractJavaPoint<?> p1, AbstractJavaPoint<?> p2) {
        return Math.abs(p1.getZ() - p2.getZ());
    }

    /**
     * Returns the rounded int value
     *
     * @return rounded x-coordinate
     */
    public int getIntX() {
        double localX = getX();
        if (localX < 0) {
            return (int) (localX - 0.5);
        }
        return (int) (localX + 0.5);
    }

    /**
     * Returns the rounded int value
     *
     * @return rounded y-coordinate
     */
    public int getIntY() {
        double localY = getY();
        if (localY < 0) {
            return (int) (localY - 0.5);
        }
        return (int) (localY + 0.5);
    }

    /**
     * Returns the rounded int value
     *
     * @return rounded z-coordinate
     */
    public int getIntZ() {
        double localZ = getZ();
        if (localZ < 0) {
            return (int) (localZ - 0.5);
        }
        return (int) (localZ + 0.5);
    }

    /**
     * Computes the magnitude or length of this.
     *
     * @return The magnitude of this
     */
    private double calculateMag() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Subtracts the given JavaPoint from this.
     *
     * @param vector The JavaPoint to be subtracted from this
     * @return A new instance holding the result of the vector subtraction
     */
    public abstract T sub(T vector);

    /**
     * Adds the given JavaPoint to this.
     *
     * @param vector The JavaPoint to be added to this
     * @return A new instance holding the result of the vector addition
     */
    public abstract T add(T vector);

    /**
     * Multiplies this by the given scalar.
     *
     * @param scalar The scalar to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public abstract T mult(double scalar);

    /**
     * Multiplies this by the given vector.
     *
     * @param vector The vector to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public abstract T mult(T vector);

    /**
     * Divides this by the given scalar.
     *
     * @param scalar The scalar to by which this should be divided
     * @return A new instance holding the result of the division
     */
    public abstract T div(double scalar);

    /**
     * Computes the dot product of this and the given JavaPoint.
     *
     * @param vector The JavaPoint to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public abstract double dot(T vector);

    /**
     * @return Returns a new instance holding the unit vector of this
     */
    protected abstract T calculateUnify();

    @Override
    public boolean equals(Object o) {
        return equals(o, EPSILON);
    }

    /**
     * Equality check with epsilon for coordinates
     *
     * @param o       other object
     * @param epsilon epsilon used for coordinate comparison
     * @return true iff this and o are equal with the given epsilon; else false
     */
    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T that = CastUtils.cast(o);

        return positionalEqual(that, epsilon);
    }

    /**
     * Checks if this and that are positional equal (not type equal)
     *
     * @param that other point
     * @return true iff this and that are not null and positional equal; else false
     */
    public boolean positionalEqual(T that) {
        return positionalEqual(that, EPSILON);
    }

    /**
     * Checks if this and that are positional equal (not type equal)
     *
     * @param that    other point
     * @param epsilon epsilon used for coordinate comparison
     * @return true iff this and that are not null and positional equal; else false
     */
    public boolean positionalEqual(T that, double epsilon) {
        if (that == null) return false;

        return MathUtils.equals(this.getX(), that.getX(), epsilon) &&
                MathUtils.equals(this.getY(), that.getY(), epsilon) &&
                MathUtils.equals(this.getZ(), that.getZ(), epsilon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


}
