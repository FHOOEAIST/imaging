/*
 * Copyright (c) Bart Kiers and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE_MIT_KIERS file in the project root for details.
 */

package science.aist.imaging.service.core.pointprocessing.convexhull;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * <p>Implementation of the GrahamScan Algorithms for finding a ConvexHull</p>
 * Based on: https://github.com/bkiers/GrahamScan
 *
 * @author Christoph Praschl
 * @author <a href="https://github.com/bkiers">Bart Kiers</a>
 * @since 1.0
 */
public class GrahamConvexHull implements UnaryOperator<List<JavaPoint2D>> {
    /**
     * Returns the convex hull of the points created from <code>xs</code>
     * and <code>ys</code>. Note that the first and last point in the returned
     * <code>List&lt;JavaPoint&gt;</code> are the same point.
     *
     * @param xs the x coordinates.
     * @param ys the y coordinates.
     * @return the convex hull of the points created from <code>xs</code>
     * and <code>ys</code>.
     */
    public List<JavaPoint2D> apply(int[] xs, int[] ys) {

        if (xs.length != ys.length) {
            throw new IllegalArgumentException("xs and ys don't have the same size");
        }

        List<JavaPoint2D> points = new ArrayList<>();

        for (int i = 0; i < xs.length; i++) {
            points.add(new JavaPoint2D(xs[i], ys[i]));
        }

        return apply(points);
    }

    /**
     * Returns the convex hull of the points created from the list
     * <code>points</code>. Note that the first and last point in the
     * returned <code>List&lt;JavaPoint&gt;</code> are the same
     * point.
     *
     * @param points the list of points.
     * @return the convex hull of the points created from the list
     * <code>points</code>.
     */
    @SuppressWarnings({"java:S127", "java:S1698"})
    public List<JavaPoint2D> apply(List<JavaPoint2D> points) {

        List<JavaPoint2D> sorted = new ArrayList<>(getSortedPointSet(points));

        if (sorted.size() < 3) {
            throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
        }

        if (areAllCollinear(sorted)) {
            throw new IllegalArgumentException("cannot create a convex hull from collinear points");
        }

        Deque<JavaPoint2D> stack = new ArrayDeque<>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {

            JavaPoint2D head = sorted.get(i);
            JavaPoint2D middle = stack.pop();
            JavaPoint2D tail = stack.peek();

            Turn turn = getTurn(tail, middle, head);

            switch (turn) {
                case COUNTER_CLOCKWISE:
                    stack.push(middle);
                    stack.push(head);
                    break;
                case CLOCKWISE:
                    i--;
                    break;
                case COLLINEAR:
                    stack.push(head);
                    break;
            }
        }

        // close the hull
        stack.push(sorted.get(0));

        return new ArrayList<>(stack);
    }


    /**
     * Returns true iff all points in <code>points</code> are collinear.
     *
     * @param points the list of points.
     * @return true iff all points in <code>points</code> are collinear.
     */
    public boolean areAllCollinear(List<JavaPoint2D> points) {

        if (points.size() < 2) {
            return true;
        }

        final JavaPoint2D a = points.get(0);
        final JavaPoint2D b = points.get(1);

        for (int i = 2; i < points.size(); i++) {

            JavaPoint2D c = points.get(i);

            if (getTurn(a, b, c) != Turn.COLLINEAR) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the points with the lowest y coordinate. In case more than 1 such
     * point exists, the one with the lowest x coordinate is returned.
     *
     * @param points the list of points to return the lowest point from.
     * @return the points with the lowest y coordinate. In case more than
     * 1 such point exists, the one with the lowest x coordinate
     * is returned.
     */
    public JavaPoint2D getLowestPoint(List<JavaPoint2D> points) {

        JavaPoint2D lowest = points.get(0);

        for (int i = 1; i < points.size(); i++) {

            JavaPoint2D temp = points.get(i);

            if (temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
                lowest = temp;
            }
        }

        return lowest;
    }

    /**
     * Returns a sorted set of points from the list <code>points</code>. The
     * set of points are sorted in increasing order of the angle they and the
     * lowest point <i>P</i> make with the x-axis. If tow (or more) points
     * form the same angle towards <i>P</i>, the one closest to <i>P</i>
     * comes first.
     *
     * @param points the list of points to sort.
     * @return a sorted set of points from the list <code>points</code>.
     * @see GrahamConvexHull#getLowestPoint(java.util.List)
     */
    @SuppressWarnings({"java:S127", "java:S1698"})
    public Set<JavaPoint2D> getSortedPointSet(List<JavaPoint2D> points) {

        final JavaPoint2D lowest = getLowestPoint(points);

        TreeSet<JavaPoint2D> set = new TreeSet<>((a, b) -> {
            if (a == b || a.equals(b)) {
                return 0;
            }

            // use longs to guard against int-underflow
            double thetaA = Math.atan2((long) a.getY() - lowest.getY(), (long) a.getX() - lowest.getX());
            double thetaB = Math.atan2((long) b.getY() - lowest.getY(), (long) b.getX() - lowest.getX());

            if (thetaA < thetaB) {
                return -1;
            } else if (thetaA > thetaB) {
                return 1;
            } else {
                // collinear with the 'lowest' point, let the point closest to it come first

                // use longs to guard against int-over/underflow
                double distanceA = Math.sqrt((((long) lowest.getX() - a.getX()) * ((long) lowest.getX() - a.getX())) +
                        (((long) lowest.getY() - a.getY()) * ((long) lowest.getY() - a.getY())));
                double distanceB = Math.sqrt((((long) lowest.getX() - b.getX()) * ((long) lowest.getX() - b.getX())) +
                        (((long) lowest.getY() - b.getY()) * ((long) lowest.getY() - b.getY())));

                if (distanceA < distanceB) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        set.addAll(points);

        return set;
    }

    /**
     * Returns the GrahamScan#Turn formed by traversing through the
     * ordered points <code>a</code>, <code>b</code> and <code>c</code>.
     * More specifically, the cross product <i>C</i> between the
     * 3 points (vectors) is calculated:
     *
     * <i>(b.getX()-a.getX() * c.getY()-a.getY()) - (b.getY()-a.getY() * c.getX()-a.getX())</i>
     * <p>
     * and if <i>C</i> is less than 0, the turn is CLOCKWISE, if
     * <i>C</i> is more than 0, the turn is COUNTER_CLOCKWISE, else
     * the three points are COLLINEAR.
     *
     * @param a the starting point.
     * @param b the second point.
     * @param c the end point.
     * @return the GrahamScan#Turn formed by traversing through the
     * ordered points <code>a</code>, <code>b</code> and
     * <code>c</code>.
     */
    @SuppressWarnings("java:S3242")
    public Turn getTurn(JavaPoint2D a, JavaPoint2D b, JavaPoint2D c) {
        // use longs to guard against int-over/underflow
        double crossProduct = (((long) b.getX() - a.getX()) * ((long) c.getY() - a.getY())) -
                (((long) b.getY() - a.getY()) * ((long) c.getX() - a.getX()));

        if (crossProduct > 0) {
            return Turn.COUNTER_CLOCKWISE;
        } else if (crossProduct < 0) {
            return Turn.CLOCKWISE;
        } else {
            return Turn.COLLINEAR;
        }
    }
}
