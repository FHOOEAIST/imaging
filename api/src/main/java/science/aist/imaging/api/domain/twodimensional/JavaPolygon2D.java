/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import lombok.Getter;
import science.aist.imaging.api.domain.AbstractJavaPolygon;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>This class represents a Polygon using JavaPoint</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaPolygon2D extends AbstractJavaPolygon<JavaPoint2D, JavaLine2D> implements Serializable {

    @Getter(lazy = true)
    private final double area = calculateArea();
    @Getter(lazy = true)
    private final JavaRectangle2D boundingBox = calculateBoundingBox();
    @Getter(lazy = true)
    private final List<JavaPoint2D> areaPoints = calculateAreaPoints();
    @Getter(lazy = true)
    private final boolean convex = calculateConvex();
    @Getter(lazy = true)
    private final boolean sorted = calculateSorted();
    protected JavaPolygon2D() {

    }
    public JavaPolygon2D(JavaPoint2D... points) {
        super(points);
    }

    public JavaPolygon2D(Collection<JavaPoint2D> points) {
        super(points);
    }

    /**
     * Sorts the given point cloud
     *
     * @param javaPoint2Ds the point cloud
     * @return the java polygon with the point cloud sorted.
     */
    public static JavaPolygon2D getPolygonFromUnsortedPointCloud(JavaPoint2D... javaPoint2Ds) {
        // calculate center point
        JavaPoint2D centerPoint = Arrays
                .stream(javaPoint2Ds)
                .reduce(JavaPoint2D::add)
                .map(jp -> jp.mult(1.0 / (double) javaPoint2Ds.length))
                .orElseThrow(IllegalStateException::new);

        // sort by angle:
        return Arrays.stream(javaPoint2Ds)
                .sorted(Comparator.comparingDouble(jp -> new JavaLine2D(jp, centerPoint).getRotation()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), l -> new JavaPolygon2D(l.toArray(new JavaPoint2D[0]))));
    }

    /**
     * Method for getting the intersection polygon of two polygons
     *
     * @param jp1 polygon 1 (must be sorted!)
     * @param jp2 polygon 2 (must be sorted!)
     * @return The intersection area as new JavaPolygon. Which is empty if given polygons are not sorted, are empty or there is no intersection area.
     * @see <a href="https://www.swtestacademy.com/intersection-convex-polygons-algorithm/">Based on swtestacademy</a>
     */
    public static JavaPolygon2D getIntersection(JavaPolygon2D jp1, JavaPolygon2D jp2) {
        JavaPolygon2D res = new JavaPolygon2D();

        // if polygons are not sorted intersection can not be calculated
        if (jp1.isEmpty() || jp2.isEmpty() || !jp1.isSorted() || !jp2.isSorted()) {
            return res;
        }

        List<JavaPoint2D> jp1Points = jp1.getPoints();
        List<JavaPoint2D> jp2Points = jp2.getPoints();

        Set<JavaPoint2D> intersectionPoints = new HashSet<>();

        // calculate all intersection points of the polygons
        jp1Points.stream().filter(jp2::isInConvexHull).forEach(intersectionPoints::add);
        jp2Points.stream().filter(jp1::isInConvexHull).forEach(intersectionPoints::add);

        for (JavaLine2D outerLine : jp1.getContour()) {
            for (JavaLine2D innerLine : jp2.getContour()) {
                JavaPoint2D intersectionPoint = JavaLine2D.getIntersectionPoint(outerLine, innerLine);
                if (intersectionPoint != null) {
                    intersectionPoints.add(intersectionPoint);
                }
            }
        }

        return intersectionPoints.isEmpty() ? res : getPolygonFromUnsortedPointCloud(intersectionPoints.toArray(new JavaPoint2D[0]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JavaPolygon2D that = (JavaPolygon2D) o;
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), area, boundingBox, areaPoints, convex, sorted);
    }

    @Override
    protected JavaLine2D createLine(JavaPoint2D p1, JavaPoint2D p2) {
        return new JavaLine2D(p1, p2);
    }

    @Override
    protected JavaPoint2D createPoint(double x, double y, double z) {
        return new JavaPoint2D(x, y);
    }

    /**
     * Method which checks if point is within the convex hull
     *
     * @param point Point which should be checked
     * @return True if point is in convex hull else False
     */
    public boolean isInConvexHull(JavaPoint2D point) {
        return isInConvexHull(point, false);
    }

    /**
     * Method which checks if point is  part of the convex hull
     * if considerPointsOnHull flag is false only points within the hull are allowed
     * else points can also be on the hull lines
     *
     * @param point Point which should be checked
     * @param considerPointsOnHull if the function should return true if the point is on the convex hull.
     * @return True if point is in convex hull else False
     */
    @SuppressWarnings("java:S881")
    public boolean isInConvexHull(JavaPoint2D point, boolean considerPointsOnHull) {
        if (considerPointsOnHull) {
            List<JavaLine2D> pathSegments = getContour();
            for (JavaLine2D javaLine2D : pathSegments) {
                if (javaLine2D.isPointOnLine(point)) {
                    return true;
                }
            }
        }

        if (points.size() < 3) return false; // less then 3 points does not represent a convex hull

        int i;
        int j;
        boolean c = false;
        int nvert = points.size();
        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((points.get(i).getY() > point.getY()) != (points.get(j).getY() > point.getY())) &&
                    (point.getX() < (points.get(j).getX() - points.get(i).getX()) * (point.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX()))
                c = !c;
        }
        return c;
    }

    /**
     * Returns the area of the JavaPolygon. This only works if the points are order either clock or counterclockwise.
     *
     * @return the area of the polygon
     * @see <a href="https://www.mathopenref.com/coordpolygonarea.html">https://www.mathopenref.com/coordpolygonarea.html</a>
     */
    private double calculateArea() {
        double sum = 0;
        List<JavaPoint2D> localPoints = getPoints();
        for (int i = 1; i <= points.size(); i++) {
            JavaPoint2D pred = localPoints.get(i - 1);
            JavaPoint2D curr = localPoints.get(i % points.size());
            sum += pred.getX() * curr.getY() - pred.getY() * curr.getX();
        }
        return Math.abs(sum / 2.0);
    }

    /**
     * checks if the points in the JavaPolygon are sorted counter-clockwise
     *
     * @return true if sorted else false
     */
    private boolean calculateSorted() {
        // calculate center point
        JavaPoint2D centerPoint = getPoints()
                .stream()
                .reduce(JavaPoint2D::add)
                .map(jp -> jp.mult(1.0 / (double) getPoints().size()))
                .orElseThrow(IllegalStateException::new);

        List<JavaLine2D> collect = points
                .stream()
                .map(p -> new JavaLine2D(centerPoint, p))
                .collect(Collectors.toList());

        JavaLine2D pred = collect
                .stream()
                .min(Comparator.comparingDouble(JavaLine2D::getRotation))
                .orElseThrow(IllegalStateException::new);

        int smallestAngleIdx = collect.indexOf(pred);

        int size = collect.size();
        for (int i = (smallestAngleIdx + 1) % size, k = 1; k < size; k++, i = (i + 1) % size) {
            JavaLine2D curr = collect.get(i);
            if (pred.getRotation() > curr.getRotation()) return false;
            pred = curr;
        }

        return true;
    }

    /**
     * Sorts the points in the java polygon and returns a new sorted java polygon
     *
     * @return the java polygon with the point cloud sorted.
     */
    public JavaPolygon2D sort() {
        return getPolygonFromUnsortedPointCloud(getPoints().toArray(new JavaPoint2D[0]));
    }

    /**
     * <p>Calculates the bounding box of the polygon</p>
     * <p>Calculates the bounding box of the polygon</p>
     * <p>If there are no points in the bounding box null is going to be returned</p>
     *
     * @return the bounding of as a rectangle
     */
    private JavaRectangle2D calculateBoundingBox() {
        if (getPoints().isEmpty()) return null;
        return JavaRectangle2D.createBoundingBox(getPoints());
    }

    /**
     * Returns all points that are in the polygon
     *
     * @return list of all points in the polygon. If the polygon is empty, the list is aswell
     */
    private List<JavaPoint2D> calculateAreaPoints() {
        JavaRectangle2D bb = getBoundingBox();
        if (bb == null) return Collections.emptyList();

        List<JavaPoint2D> jps = new ArrayList<>();
        for (int y = bb.getTopLeft().getIntY(); y < bb.getBottomRight().getIntY(); y++) {
            for (int x = bb.getTopLeft().getIntX(); x < bb.getBottomRight().getIntX(); x++) {
                JavaPoint2D jp = new JavaPoint2D(x, y);
                if (isInConvexHull(jp, true)) {
                    jps.add(jp);
                }
            }
        }
        return jps;
    }

    /**
     * @return true iff this is a convex polygon or false if it is concave
     */
    private boolean calculateConvex() {
        List<JavaPoint2D> twoDpoints = getPoints();
        if (twoDpoints.size() < 3) return false;

        double res = 0;
        for (int i = 0; i < twoDpoints.size(); i++) {
            JavaPoint2D p = twoDpoints.get(i);
            JavaPoint2D tmp = twoDpoints.get((i + 1) % twoDpoints.size());
            double x = tmp.getX() - p.getX();
            double y = tmp.getY() - p.getY();
            JavaPoint2D u = twoDpoints.get((i + 2) % twoDpoints.size());

            if (i == 0) // in first loop direction is unknown, so save it in res
                res = u.getX() * y - u.getY() * x + x * p.getY() - y * p.getX();
            else {
                double newres = u.getX() * y - u.getY() * x + x * p.getY() - y * p.getX();
                if ((newres > 0 && res < 0) || (newres < 0 && res > 0))
                    return false;
            }
        }
        return true;
    }

    @Override
    protected double calculateInnerDepth() {
        return 0;
    }

    @Override
    protected JavaPoint2D calculateNormalvector() {
        float x = 0;
        float y = 0;
        int cnt = 0;

        for (JavaPoint2D point : getPoints()) {
            x += point.getX();
            y += point.getY();
            cnt++;
        }

        if (cnt > 0) {
            return new JavaPoint2D(x / cnt, y / cnt);
        } else {
            return new JavaPoint2D();
        }
    }
}
