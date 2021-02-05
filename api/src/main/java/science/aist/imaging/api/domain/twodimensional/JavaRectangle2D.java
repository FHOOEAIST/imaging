/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import science.aist.imaging.api.domain.Side;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.util.PointMinMaxFunction;
import science.aist.jack.data.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * <p>This class represents a Rectangle using JavaPoint</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class JavaRectangle2D extends JavaRectangleRotated2D {
    /**
     * @param topLeft     top left point of the rectangle
     * @param bottomRight bottom right point of the rectangle
     */
    public JavaRectangle2D(JavaPoint2D topLeft, JavaPoint2D bottomRight) {
        // As this must be the first statement, it is not possible to save data in between
        this(topLeft, bottomRight.getX() - topLeft.getX(), bottomRight.getY() - topLeft.getY());
    }

    /**
     * this(topLeft, new JavaPoint(topLeft.getX() + width, topLeft.getY() + height))
     *
     * @param topLeft top left point of the rectangle
     * @param width   width of the rectangle
     * @param height  height of the rectangle
     */
    public JavaRectangle2D(JavaPoint2D topLeft, double width, double height) {
        super(topLeft.add(new JavaPoint2D(width / 2.0, height / 2.0)), width, height, 0);
    }

    /**
     * @param x1 x-coordinate top left point of the rectangle
     * @param y1 y-coordinate top left point of the rectangle
     * @param x2 x-coordinate bottom right point of the rectangle
     * @param y2 y-coordinate bottom right point of the rectangle
     */
    public JavaRectangle2D(int x1, int y1, int x2, int y2) {
        this(new JavaPoint2D(x1, y1), new JavaPoint2D(x2, y2));
    }

    /**
     * @param x1 x-coordinate top left point of the rectangle
     * @param y1 y-coordinate top left point of the rectangle
     * @param x2 x-coordinate bottom right point of the rectangle
     * @param y2 y-coordinate bottom right point of the rectangle
     */
    public JavaRectangle2D(double x1, double y1, double x2, double y2) {
        this(new JavaPoint2D(x1, y1), new JavaPoint2D(x2, y2));
    }

    /**
     * Empty constructor for deriving classes
     */
    protected JavaRectangle2D() {

    }

    /**
     * Method that calculates the intersection rectangle of two rectangles.
     *
     * @param rect1 the first rectangle
     * @param rect2 the second rectangle
     * @return the intersection rectangle if there is an intersection else Optional.empty
     */
    @SuppressWarnings("java:S3242")
    public static Optional<JavaRectangle2D> getIntersection(JavaRectangle2D rect1, JavaRectangle2D rect2) {
        double xTL = Math.max(rect1.getTopLeft().getX(), rect2.getTopLeft().getX());
        double yTL = Math.max(rect1.getTopLeft().getY(), rect2.getTopLeft().getY());
        double xBR = Math.min(rect1.getBottomRight().getX(), rect2.getBottomRight().getX());
        double yBR = Math.min(rect1.getBottomRight().getY(), rect2.getBottomRight().getY());

        if (xBR < xTL || yBR < yTL) return Optional.empty();
        return Optional.of(new JavaRectangle2D(xTL, yTL, xBR, yBR));
    }

    /**
     * Method which checks if two rectangles which are not equal or overlapping are touching each other
     *
     * @param rect1 rect 1
     * @param rect2 rect 2
     * @return The side of rect 1 where rect2 is touching or an empty optional iff not touching/equal/overlapping
     */
    public static Optional<Side> touchingSide(JavaRectangle2D rect1, JavaRectangle2D rect2) {
        Optional<JavaRectangle2D> intersection = getIntersection(rect1, rect2);
        if (rect1.equals(rect2) || (intersection.isPresent() && intersection.get().getArea() > 0.0001)) {
            return Optional.empty();
        }

        List<JavaLine2D> rect2Lines = Arrays.asList(rect2.getTopLine(), rect2.getRightLine(), rect2.getBottomLine(), rect2.getLeftLine());

        // All different four cases. This helps to prevent using an if-/else cascade
        List<Pair<List<JavaPoint2D>, Side>> cases = Arrays.asList(
                Pair.of(rect1.getTopLine().getBresenham(), Side.TOP),
                Pair.of(rect1.getRightLine().getBresenham(), Side.RIGHT),
                Pair.of(rect1.getBottomLine().getBresenham(), Side.BOTTOM),
                Pair.of(rect1.getLeftLine().getBresenham(), Side.LEFT)
        );

        for (JavaLine2D rect2Line : rect2Lines) {
            List<JavaPoint2D> outerBresenham = rect2Line.getBresenham();
            for (Pair<List<JavaPoint2D>, Side> aCase : cases) {
                if (aCase.getFirst().stream().filter(outerBresenham::contains).count() >= 2) {
                    return Optional.of(aCase.getSecond());
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Comfort method for creating an rectangle from any x and y points.
     * The correct coordinates are determined for {@link JavaRectangle2D#getTopLeft()} and {@link JavaRectangle2D#getBottomRight()}
     *
     * @param x1 any first x coordinate
     * @param y1 any first y coordinate
     * @param x2 any second x coordinate
     * @param y2 any second y coordinate
     * @return a JavaRectangle
     */
    public static JavaRectangle2D createRectangle(double x1, double y1, double x2, double y2) {
        double maxX = Math.max(x1, x2);
        double minX = Math.min(x1, x2);
        double maxY = Math.max(y1, y2);
        double minY = Math.min(y1, y2);

        return new JavaRectangle2D(minX, minY, maxX, maxY);
    }

    /**
     * Creates a bounding box for a given set of java points
     *
     * @param jps the points
     * @return the bounding box of the java points
     * @throws IllegalArgumentException if the collection is empty
     * @see JavaRectangle2D#createBoundingBox(Collection)
     */
    public static JavaRectangle2D createBoundingBox(JavaPoint2D... jps) {
        return createBoundingBox(Arrays.asList(jps));
    }

    /**
     * Creates a bounding box for a given set of java points
     *
     * @param jps the list of points
     * @return the bounding box of the java points
     * @throws IllegalArgumentException if the collection is empty
     */
    public static JavaRectangle2D createBoundingBox(Collection<JavaPoint2D> jps) {
        if (jps.isEmpty())
            throw new IllegalArgumentException("At least a single point is required to construct a bounding box.");


        PointMinMaxFunction<JavaPoint2D> point2DPointMinMaxFunction = new PointMinMaxFunction<>();
        Pair<JavaPoint3D, JavaPoint3D> apply = point2DPointMinMaxFunction.apply(jps);

        double minX = apply.getFirst().getX();
        double minY = apply.getFirst().getY();
        double maxX = apply.getSecond().getX();
        double maxY = apply.getSecond().getY();

        return new JavaRectangle2D(minX, minY, maxX, maxY);
    }

    /**
     * Sets the top left point of the rectangle. This mutates the current rectangle!
     *
     * @param topLeft the new top left point of the rectangle
     */
    public void setTopLeft(JavaPoint2D topLeft) {
        // Just create a new rectangle, and set all its internal properties not the one with the new values
        JavaRectangle2D jr = new JavaRectangle2D(topLeft, getBottomRight());
        setHeight(jr.getHeight());
        setWidth(jr.getWidth());
        setRotation(jr.getRotation());
        setCenterPoint(jr.getCenterPoint());
    }

    /**
     * Sets the bottom right point of the rectangle. This mutates the current rectangle!
     *
     * @param bottomRight the new bottom right point of the rectangle
     */
    public void setBottomRight(JavaPoint2D bottomRight) {
        // Just create a new rectangle, and set all its internal properties not the one with the new values
        JavaRectangle2D jr = new JavaRectangle2D(getTopLeft(), bottomRight);
        setHeight(jr.getHeight());
        setWidth(jr.getWidth());
        setRotation(jr.getRotation());
        setCenterPoint(jr.getCenterPoint());
    }

    /**
     * Checks if a point in contained in the rectangle. so if it is inside or on the boundaries.
     *
     * @param javaPoint2D the point to be checked
     * @return true if contained, else false
     */
    @Override
    public boolean containsJavaPoint(JavaPoint2D javaPoint2D) {
        double x = javaPoint2D.getX();
        double y = javaPoint2D.getY();
        return x >= getTopLeft().getX()
                && x <= getBottomRight().getX()
                && y >= getTopLeft().getY()
                && y <= getBottomRight().getY();
    }

    /**
     * @return toString for debugging
     */
    @Override
    public String toString() {
        return "[" + getTopLeft() + "," + getBottomRight() + "]";
    }

    @Override
    public JavaPoint2D getNormalvector() {
        return new JavaTriangle2D(getTopLeft(), getTopRight(), getBottomLeft()).getNormalvector();
    }
}
