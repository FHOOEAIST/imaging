/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;


import science.aist.imaging.api.compare.JavaPointCoordinateComparator;
import science.aist.imaging.api.domain.AbstractJavaLine;
import lombok.Getter;
import science.aist.jack.data.Pair;
import science.aist.jack.math.MathUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Representation of a Line</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class JavaLine2D extends AbstractJavaLine<JavaPoint2D> implements Serializable {
    @Getter(lazy = true)
    private final List<JavaPoint2D> bresenham = calculateBresenham();
    @Getter(lazy = true)
    private final double rotation = calculateRotation();
    @Getter(lazy = true)
    private final double gradient = calculateGradient();

    /**
     * set line by coordinates of the start and end point
     *
     * @param x1 x-coordinate of start point
     * @param y1 y-coordinate of start point
     * @param x2 x-coordinate of end point
     * @param y2 y-coordinate of end point
     */
    public JavaLine2D(int x1, int y1, int x2, int y2) {
        this(new JavaPoint2D(x1, y1), new JavaPoint2D(x2, y2));
    }

    public JavaLine2D(double x1, double y1, double x2, double y2) {
        this(new JavaPoint2D(x1, y1), new JavaPoint2D(x2, y2));
    }

    public JavaLine2D(JavaPoint2D startPoint, JavaPoint2D endpoint) {
        super(startPoint, endpoint);
    }

    /**
     * http://stackoverflow.com/a/19342455
     *
     * @param line1 line 1
     * @param line2 line 2
     * @return the point where the two lines intersect or null if there is no intersection
     */
    public static JavaPoint2D getIntersectionPoint(JavaLine2D line1, JavaLine2D line2) {
        JavaPoint2D result = null;

        double s1x = line1.getEndPoint().getX() - line1.getStartPoint().getX();
        double s1y = line1.getEndPoint().getY() - line1.getStartPoint().getY();
        double s2x = line2.getEndPoint().getX() - line2.getStartPoint().getX();
        double s2y = line2.getEndPoint().getY() - line2.getStartPoint().getY();

        double helper = -s2x * s1y + s1x * s2y;
        double s = (-s1y * (line1.getStartPoint().getX() - line2.getStartPoint().getX()) + s1x * (line1.getStartPoint().getY() - line2.getStartPoint().getY())) / helper;
        double t = (s2x * (line1.getStartPoint().getY() - line2.getStartPoint().getY()) - s2y * (line1.getStartPoint().getX() - line2.getStartPoint().getX())) / helper;

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // Collision detected
            result = new JavaPoint2D(line1.getStartPoint().getX() + (t * s1x), line1.getStartPoint().getY() + (t * s1y));
        }

        return result;
    }

    /**
     * Create a line out of a point and a specific getRotation with the "length" stepWidth
     *
     * @param point     The center point of the line
     * @param rotation  The getRotation of the line
     * @param stepWidth the step width (line length)
     * @return a new line object
     */
    public static JavaLine2D createLine(JavaPoint2D point, double rotation, int stepWidth) {
        double theta = rotation * Math.PI / 180;

        double stepX = Math.cos(theta) * stepWidth;
        double stepY = Math.sin(theta) * stepWidth;
        JavaPoint2D p1 = new JavaPoint2D(point.getX() + stepX * stepWidth, point.getY() + stepY * stepWidth);
        JavaPoint2D p2 = new JavaPoint2D(point.getX() - stepX * stepWidth, point.getY() - stepY * stepWidth);
        return new JavaLine2D(p1, p2);
    }

    /**
     * Calculates the cut angle between two lines
     * <p>
     * Calculation of cut angle: https://de.wikipedia.org/wiki/Schnittwinkel_(Geometrie)
     *
     * @param line1 the first line
     * @param line2 the second line
     * @return the cut angle between the lines in rad
     */
    public static double cutAngle(JavaLine2D line1, JavaLine2D line2) {
        double gradientLine1 = line1.getGradient();
        double gradientLine2 = line2.getGradient();

        // if gradient1 * gradient2 == -1 then then there would be a divide by 0 error. In this case, the lines cut in
        // 90° angle
        if (Math.abs(gradientLine1 * gradientLine2 + 1) < 0.0001)
            return Math.toRadians(90);

        return Math.atan(Math.abs((gradientLine1 - gradientLine2) / (1 - gradientLine1 * gradientLine2)));
    }

    /**
     * <p>Create a Java Line by using the center of the line + a given getRotation + the length of the line</p>
     *
     * @param center   the center point
     * @param rotation the getRotation in radians
     * @param length   the length of the line
     * @return the resulting java line
     * @see <a href="https://stackoverflow.com/a/14842362">https://stackoverflow.com/a/14842362</a>
     */
    public static JavaLine2D createByCenterRotationAndLength(JavaPoint2D center, double rotation, double length) {
        // To rotate a line first create a ground line. then rotate the point of the ground line, by moving the points
        // to the getRotation center which is the center of the line, and then back.
        return new JavaLine2D(center.sub(new JavaPoint2D(length / 2, 0)).rotate(rotation, center),
                center.add(new JavaPoint2D(length / 2, 0)).rotate(rotation, center));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JavaLine2D that = (JavaLine2D) o;
        return this.getStartPoint().equals(that.startPoint) && this.getEndPoint().equals(that.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bresenham, rotation, gradient);
    }

    /**
     * @return The direction vector of this line
     */
    @Override
    public JavaPoint2D getLineDirection() {
        return endPoint.sub(startPoint);
    }

    /**
     * Method which checks if a point is on a line or not
     *
     * @param point   to be checked
     * @param epsilon needed to check if cross product is greater than some epsilon
     * @return true iff point is on line, else false
     * @see <a href="https://stackoverflow.com/questions/328107/how-can-you-determine-a-point-is-between-two-other-points-on-a-line-segment">Stackoverflow</a>
     */
    public boolean isPointOnLine(JavaPoint2D point, double epsilon) {
        JavaPoint2D a = this.startPoint;
        JavaPoint2D b = this.endPoint;

        // calculate cross product
        double cross = (point.getY() - a.getY()) * (b.getX() - a.getX()) - (point.getX() - a.getX()) * (b.getY() - a.getY());

        // check if cross product is greater than epsilon (in Java = Math.ulp(cross) or just take some random small number)
        if (Math.abs(cross) > epsilon) {
            return false;
        }

        // calculate dot product
        double dot = (point.getX() - a.getX()) * (b.getX() - a.getX()) + (point.getY() - a.getY()) * (b.getY() - a.getY());

        // check dot product
        if (dot < 0) {
            return false;
        }

        // calculate squared length
        double squaredLength = (b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY());

        // check squared length
        return dot <= squaredLength;
    }

    /**
     * Method which checks if a point is on a line or not
     *
     * @param point to be checked
     * @return true iff point is on line, else false
     * @see <a href="https://stackoverflow.com/questions/328107/how-can-you-determine-a-point-is-between-two-other-points-on-a-line-segment">Stackoverflow</a>
     */
    public boolean isPointOnLine(JavaPoint2D point) {
        return isPointOnLine(point, 0.000000001);
    }

    /**
     * Returns the getRotation of the given line.
     * This function returns 0 degree in case start.y = end.y
     * And PI/2 in case of start.x = end.x
     * The result is in the range between 0 and 2 PI. (Be aware of that, some implementation return from -PI to PI)
     * The getRotation of a line usually only makes sense between 0 and PI because its symmetric, but we assume, that a line
     * goes the other direction if start and end point are reversed.
     *
     * @return getRotation of the line in radians.
     */
    protected double calculateRotation() {
        return (Math.atan2(getEndPoint().getY() - getStartPoint().getY(), getEndPoint().getX() - getStartPoint().getX()) + (2 * Math.PI)) % (2 * Math.PI);
    }

    /**
     * Calculate the center point of a given line
     * x = (start.x + end.x) / 2
     * y = (start.y + end.y) / 2
     *
     * @return center point of the line
     */
    @Override
    protected JavaPoint2D calculateCenterPoint() {
        return new JavaPoint2D((getStartPoint().getX() + getEndPoint().getX()) / 2,
                (getStartPoint().getY() + getEndPoint().getY()) / 2);
    }

    /**
     * This function calculates a square around a line with a given distance.
     *
     * @param distance distance
     * @return four corner points of the Square around
     */
    public JavaPolygon2D calculateSquareAroundLine(double distance) {
        double p1x = getStartPoint().getX();
        double p1y = getStartPoint().getY();
        double p2x = getEndPoint().getX();
        double p2y = getEndPoint().getY();
        double angle = Math.toDegrees(getRotation());

        double x1 = distance * MathUtils.cosd(-135 + angle) + p1x;
        double y1 = distance * MathUtils.sind(-135 + angle) + p1y;

        double x2 = distance * MathUtils.cosd(135 + angle) + p1x;
        double y2 = distance * MathUtils.sind(135 + angle) + p1y;

        double x3 = distance * MathUtils.cosd(45 + angle) + p2x;
        double y3 = distance * MathUtils.sind(45 + angle) + p2y;

        double x4 = distance * MathUtils.cosd(315 + angle) + p2x;
        double y4 = distance * MathUtils.sind(315 + angle) + p2y;

        return new JavaPolygon2D(new JavaPoint2D(x1, y1), new JavaPoint2D(x2, y2), new JavaPoint2D(x3, y3), new JavaPoint2D(x4, y4));
    }

    /**
     * Calculates the gradient of the line
     *
     * @return calculates the gradient of the line if StartPoint.x == EndPoint.x --&gt; Double.VALUE is the result.
     */
    protected double calculateGradient() {
        // If the start.x == end.x there is a divide by 0 error. In this case, the gradient would be infinity
        // to enable a calculation with a value near infinity we assume a gradient of DOUBLE.Max
        if (Math.abs(getStartPoint().getX() - getEndPoint().getX()) < 0.0001)
            return Double.MAX_VALUE;

        return (getStartPoint().getY() - getEndPoint().getY()) / (getStartPoint().getX() - getEndPoint().getX());
    }

    /**
     * Moves a line perpendicular
     *
     * @param distance the distance how far the line should be moved
     * @return the moved new line
     */
    public JavaLine2D move(double distance) {
        double diffX = getEndPoint().getX() - getStartPoint().getX();
        double diffY = getEndPoint().getY() - getStartPoint().getY();
        double angle = Math.atan2(diffX, diffY);
        double distX = distance * Math.cos(angle);
        double distY = -distance * Math.sin(angle);
        return new JavaLine2D(getStartPoint().getX() + distX, getStartPoint().getY() + distY, getEndPoint().getX() + distX, getEndPoint().getY() + distY);
    }

    /**
     * Method which splits the single {@link JavaLine2D} into two parts at the given split point
     *
     * @param splitPoint position where to cut the line
     * @return a pair of {@link JavaLine2D} representing the two, split parts of the original line; Optional is empty iff splitPoint is not on the JavaLine
     */
    public Optional<Pair<JavaLine2D, JavaLine2D>> split(JavaPoint2D splitPoint) {
        if (!isPointOnLine(splitPoint)) {
            return Optional.empty();
        }

        return Optional.of(Pair.of(new JavaLine2D(getStartPoint(), splitPoint), new JavaLine2D(splitPoint, getEndPoint())));
    }

    /**
     * Splits the single {@link JavaLine2D} into multiple parts at the given split points
     *
     * @param splitPoints points where to split the {@link JavaLine2D}
     * @return Collection of all splits, which is empty if line could not be split at any point
     */
    public Set<JavaLine2D> split(JavaPoint2D... splitPoints) {
        Set<JavaLine2D> result = new HashSet<>();

        // filter the given points if they are on the line and order them by the x coordinate or if it is a vertical line by the y coordinate.
        List<JavaPoint2D> sortedDistinctSplitPoints = Arrays
                .stream(splitPoints)
                .distinct()
                .filter(this::isPointOnLine)
                .sorted(new JavaPointCoordinateComparator())
                .collect(Collectors.toList());

        JavaPoint2D lastPoint = getStartPoint();

        for (int i = 0; i < sortedDistinctSplitPoints.size(); i++) {
            JavaPoint2D sortedDistinctSplitPoint = sortedDistinctSplitPoints.get(i);
            result.add(new JavaLine2D(lastPoint, sortedDistinctSplitPoint));

            if (i == sortedDistinctSplitPoints.size() - 1) {
                result.add(new JavaLine2D(sortedDistinctSplitPoint, getEndPoint()));
            }
            lastPoint = sortedDistinctSplitPoint;
        }

        return result;
    }

    /**
     * https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#C
     *
     * @return list of points.
     */
    protected List<JavaPoint2D> calculateBresenham() {
        int x0 = getStartPoint().getIntX();
        int y0 = getStartPoint().getIntY();
        int x1 = getEndPoint().getIntX();
        int y1 = getEndPoint().getIntY();

        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int err = (dx > dy ? dx : -dy) / 2;
        int e2;

        List<JavaPoint2D> result = new ArrayList<>();
        while (true) {
            result.add(new JavaPoint2D(x0, y0));
            if (x0 == x1 && y0 == y1) break;
            e2 = err;
            if (e2 > -dx) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dy) {
                err += dx;
                y0 += sy;
            }
        }
        return result;
    }

    /**
     * Method for getting a point along this line with the given distance from the startPoint
     * * (Based on https://math.stackexchange.com/questions/175896/finding-a-point-along-a-line-a-certain-distance-away-from-another-point)
     *
     * @param distance from the startPoint
     * @return the point with the given distance
     */
    @Override
    public JavaPoint2D getPointAlongLine(double distance) {
        return getStartPoint().add(getEndPoint().sub(getStartPoint()).getUnify().mult(distance));
    }

    /**
     * Rotates this line around its mid point
     *
     * @param angle angle (in degrees) to rotate
     * @return new object that represents this line rotated around its midpoint
     */
    public JavaLine2D rotate(double angle) {
        return rotate(angle, getStartPoint().add(getEndPoint()).div(2));
    }

    /**
     * Rotates this line around the given rotationCenter
     *
     * @param angle          angle (in radian) to rotate
     * @param rotationCenter Point to rotate the line around
     * @return new object that represents this line rotated around the given rotationCenter
     */
    public JavaLine2D rotate(double angle, JavaPoint2D rotationCenter) {
        JavaPoint2D rotatedStart = getStartPoint().rotate(angle, rotationCenter);
        JavaPoint2D rotatedEnd = getEndPoint().rotate(angle, rotationCenter);
        return new JavaLine2D(rotatedStart, rotatedEnd);
    }

}
