/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>Java representation for a Rotated Rectangle.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class JavaRectangleRotated2D extends JavaPolygon2D {
    /**
     * Center Point
     */
    private JavaPoint2D centerPoint;
    /**
     * The getRotation of the rectangle in radians
     */
    private double rotation;
    /**
     * width of the rectangle
     */
    private double width;
    /**
     * height of the rectangle
     */
    private double height;

    /**
     * Empty constructor for deriving classes
     */
    protected JavaRectangleRotated2D() {

    }

    /**
     * Constructs a rotated rectangle by it's centerPoint, width, height and getRotation.
     *
     * @param centerPoint the center point of the rectangle
     * @param width       the width of the rectangle
     * @param height      the height of the rectangle
     * @param rotation    the getRotation in radians of the rectangle
     */
    public JavaRectangleRotated2D(JavaPoint2D centerPoint, double width, double height, double rotation) {
        super(calculateCornerPoints(centerPoint, width, height, rotation));
        this.centerPoint = centerPoint;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
    }

    private static boolean aGreaterThanB(JavaPoint2D a, JavaPoint2D b) {
        return a.getX() > b.getX() && a.getY() > b.getY();
    }

    private static boolean aSmallerThanB(JavaPoint2D a, JavaPoint2D b) {
        return a.getX() < b.getX() && a.getY() < b.getY();
    }

    private static List<JavaPoint2D> calculateCornerPoints(JavaPoint2D centerPoint, double width, double height, double rotation) {
        return Arrays.asList(
                calculateCornerPoint(centerPoint, width, height, rotation, -1, -1),
                calculateCornerPoint(centerPoint, width, height, rotation, 1, -1),
                calculateCornerPoint(centerPoint, width, height, rotation, -1, 1),
                calculateCornerPoint(centerPoint, width, height, rotation, 1, 1)
        );
    }

    private static JavaPoint2D calculateCornerPoint(JavaPoint2D centerPoint, double width, double height, double rotation, int widthFactor, int heightFactor) {
        return centerPoint.add(new JavaPoint2D(widthFactor * width / 2.0, heightFactor * height / 2.0)).rotate(rotation, centerPoint);
    }

    /**
     * Calculate the top right point of the rectangle
     *
     * @return the top right point
     */
    public JavaPoint2D getTopRight() {
        return centerPoint.add(new JavaPoint2D(getWidth() / 2.0, -getHeight() / 2.0)).rotate(rotation, centerPoint);
    }

    /**
     * Calculate the bottom left point of the rectangle
     *
     * @return the bottom left point
     */
    public JavaPoint2D getBottomLeft() {
        return centerPoint.add(new JavaPoint2D(-getWidth() / 2.0, getHeight() / 2.0)).rotate(rotation, centerPoint);
    }

    /**
     * Calculate the top left  point of the rectangle
     *
     * @return the top left point
     */
    public JavaPoint2D getTopLeft() {
        return centerPoint.add(new JavaPoint2D(-getWidth() / 2.0, -getHeight() / 2.0)).rotate(rotation, centerPoint);
    }

    /**
     * Calculate the bottom right point of the rectangle
     *
     * @return the bottom right point
     */
    public JavaPoint2D getBottomRight() {
        return centerPoint.add(new JavaPoint2D(getWidth() / 2.0, getHeight() / 2.0)).rotate(rotation, centerPoint);
    }

    /**
     * @return the center point
     */
    public JavaPoint2D getCenterPoint() {
        return centerPoint;
    }

    /**
     * sets value of field {@link JavaRectangleRotated2D#centerPoint}
     *
     * @param centerPoint value of field centerPoint
     * @see JavaRectangleRotated2D#centerPoint
     */
    void setCenterPoint(JavaPoint2D centerPoint) {
        this.centerPoint = centerPoint;
    }

    /**
     * @return the width of the rectangle
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * sets value of field {@link JavaRectangleRotated2D#width}
     *
     * @param width value of field width
     * @see JavaRectangleRotated2D#width
     */
    void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return return the height of the rectangle
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * sets value of field {@link JavaRectangleRotated2D#height}
     *
     * @param height value of field height
     * @see JavaRectangleRotated2D#height
     */
    void setHeight(double height) {
        this.height = height;
    }

    /**
     * gets value of field {@link JavaRectangleRotated2D#rotation}
     *
     * @return value of field getRotation
     * @see JavaRectangleRotated2D#rotation
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * sets value of field {@link JavaRectangleRotated2D#rotation}
     *
     * @param rotation value of field getRotation
     * @see JavaRectangleRotated2D#rotation
     */
    void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public boolean containsJavaPoint(JavaPoint2D javaPoint2D) {
        // approach: see the rectangle as "unrotated" rectangle, and just rotate the point that should
        // be check into the "unrotated" plane, and then use the normal code JavaRectangle#containsJavaPoint function
        JavaPoint2D rotatedPointInPlane = javaPoint2D.rotate(getRotation(), getCenterPoint());
        JavaPoint2D tmp = new JavaPoint2D(getWidth() / 2.0, getHeight() / 2.0);
        return aGreaterThanB(rotatedPointInPlane, centerPoint.sub(tmp))
                && aSmallerThanB(rotatedPointInPlane, centerPoint.add(tmp));
    }

    /**
     * @return top line (new object)
     */
    public JavaLine2D getTopLine() {
        return new JavaLine2D(getTopLeft(), getTopRight());
    }

    /**
     * @return right line (new object)
     */
    public JavaLine2D getRightLine() {
        return new JavaLine2D(getTopRight(), getBottomRight());
    }

    /**
     * @return bottom line (new object)
     */
    public JavaLine2D getBottomLine() {
        return new JavaLine2D(getBottomLeft(), getBottomRight());
    }

    /**
     * @return left line (new object)
     */
    public JavaLine2D getLeftLine() {
        return new JavaLine2D(getTopLeft(), getBottomLeft());
    }

    /**
     * @return List of (top line, right line, bottom line and left line)
     */
    public List<JavaLine2D> getBorderLines() {
        return Arrays.asList(getTopLine(), getRightLine(), getBottomLine(), getLeftLine());
    }

    /**
     * @return returns the size of the rectangle
     */
    @Override
    public double getArea() {
        return getWidth() * getHeight();
    }

    /**
     * adds the given getRotation to the current rectangle and returns
     *
     * @param rotation the getRotation in radians
     * @return the new java rectangle with the applied getRotation
     */
    public JavaRectangleRotated2D rotate(double rotation) {
        return new JavaRectangleRotated2D(getCenterPoint(), getWidth(), getHeight(), getRotation() + rotation);
    }

    /**
     * Normalizes the rotated rectangles
     *
     * @return a normalized java rectangle
     */
    public JavaRectangle2D normalize() {
        return new JavaRectangle2D(getCenterPoint().sub(new JavaPoint2D(getWidth() / 2.0, getHeight() / 2.0)), getWidth(), getHeight());
    }

    /**
     * Moves the rectangle to a given position
     *
     * @param newCenterPoint the new center point
     * @return the moved rectangle
     */
    public JavaRectangleRotated2D moveTo(JavaPoint2D newCenterPoint) {
        return new JavaRectangleRotated2D(newCenterPoint, getWidth(), getHeight(), getRotation());
    }

    /**
     * Moves the rectangle by a given distance
     *
     * @param distance the given distance
     * @return the moved rectangle
     */
    public JavaRectangleRotated2D move(JavaPoint2D distance) {
        return new JavaRectangleRotated2D(getCenterPoint().add(distance), getWidth(), getHeight(), getRotation());
    }

    /**
     * Stretches the rectangle by given factors
     *
     * @param widthFactor  the factor for the width
     * @param heightFactor the factor for the height
     * @return the stretched rectangle
     */
    public JavaRectangleRotated2D stretch(double widthFactor, double heightFactor) {
        return new JavaRectangleRotated2D(getCenterPoint(), getWidth() * widthFactor, getHeight() * heightFactor, getRotation());
    }

    /**
     * Stretches the rectangle to given width and heights
     *
     * @param width  new width
     * @param height new height
     * @return the stretched rectangle
     */
    public JavaRectangleRotated2D stretchTo(double width, double height) {
        return new JavaRectangleRotated2D(getCenterPoint(), width, height, getRotation());
    }

    /**
     * Generated Code
     *
     * @param o value to compare
     * @return true if values are equals else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaRectangleRotated2D that = (JavaRectangleRotated2D) o;
        return Double.compare(that.rotation, rotation) == 0 &&
                Double.compare(that.width, width) == 0 &&
                Double.compare(that.height, height) == 0 &&
                Objects.equals(centerPoint, that.centerPoint);
    }

    /**
     * Generated Code
     *
     * @return hashCode for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(centerPoint, rotation, width, height);
    }

    @Override
    public JavaPoint2D calculateNormalvector() {
        return new JavaTriangle2D(getTopLeft(), getTopRight(), getBottomLeft()).getNormalvector();
    }

    @Override
    public List<JavaLine2D> calculateContour() {
        List<JavaLine2D> result = new ArrayList<>();
        result.add(new JavaLine2D(getTopLeft(), getTopRight()));
        result.add(new JavaLine2D(getTopRight(), getBottomRight()));
        result.add(new JavaLine2D(getBottomRight(), getBottomLeft()));
        result.add(new JavaLine2D(getBottomLeft(), getTopLeft()));
        return result;
    }

    /**
     * Generated Code
     *
     * @return String representation of the object
     */
    @Override
    public String toString() {
        return "JavaRectangleRotated{" +
                "centerPoint=" + centerPoint +
                ", getRotation=" + rotation +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
