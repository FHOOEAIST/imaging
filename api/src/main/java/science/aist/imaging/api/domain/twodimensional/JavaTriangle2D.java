/*
 * Copyright (c) Johannes Diemke and contributors. All rights reserved.
 * Licensed under the MIT license. See LICENSE_MIT_JDIEMKE file in the project root for details.
 */

package science.aist.imaging.api.domain.twodimensional;


import lombok.Getter;
import science.aist.imaging.api.domain.Triangle;

import java.util.Objects;

/**
 * <p>2D triangle class implementation based on https://github.com/jdiemke/delaunay-triangulator/blob/master/library/src/main/java/io/github/jdiemke/triangulation/Triangle2D.java</p>
 *
 * @author <a href="https://github.com/jdiemke">Johannes Diemke</a>
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
public class JavaTriangle2D extends JavaPolygon2D implements Triangle<JavaPoint2D> {
    protected JavaPoint2D a;
    protected JavaPoint2D b;
    protected JavaPoint2D c;

    public JavaTriangle2D(JavaPoint2D a, JavaPoint2D b, JavaPoint2D c) {
        super(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Tests if the two arguments have the same sign.
     *
     * @param a The first floating point argument
     * @param b The second floating point argument
     * @return Returns true iff both arguments have the same sign
     */
    private static boolean hasSameSign(double a, double b) {
        return Math.signum(a) == Math.signum(b);
    }

    /**
     * Tests if a 2D point lies inside this 2D triangle. See Real-Time Collision Detection, chap. 5, p. 206.
     *
     * @param point The point to be tested
     * @return Returns true iff the point lies inside this 2D triangle
     */
    public boolean contains(JavaPoint2D point) {
        double pab = point.sub(a).cross(b.sub(a));
        double pbc = point.sub(b).cross(c.sub(b));

        if (!hasSameSign(pab, pbc)) {
            return false;
        }

        double pca = point.sub(c).cross(a.sub(c));

        return hasSameSign(pab, pca);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JavaTriangle2D that = (JavaTriangle2D) o;
        return Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Objects.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), a, b, c);
    }

    /**
     * Tests if a given point lies in the circumcircle of this triangle. Let the triangle ABC appear in counterclockwise
     * (CCW) order. Then when det &gt; 0, the point lies inside the circumcircle through the three points a, b and c. If
     * instead det &lt; 0, the point lies outside the circumcircle. When det = 0, the four points are cocircular. If the
     * triangle is oriented clockwise (CW) the result is reversed. See Real-Time Collision Detection, chap. 3, p. 34.
     *
     * @param point The point to be tested
     * @return Returns true iff the point lies inside the circumcircle through the three points a, b, and c of the
     * triangle
     */
    public boolean isPointInCircumcircle(JavaPoint2D point) {
        double a11 = a.getX() - point.getX();
        double a21 = b.getX() - point.getX();
        double a31 = c.getX() - point.getX();

        double a12 = a.getY() - point.getY();
        double a22 = b.getY() - point.getY();
        double a32 = c.getY() - point.getY();

        double a13 = (a.getX() - point.getX()) * (a.getX() - point.getX()) + (a.getY() - point.getY()) * (a.getY() - point.getY());
        double a23 = (b.getX() - point.getX()) * (b.getX() - point.getX()) + (b.getY() - point.getY()) * (b.getY() - point.getY());
        double a33 = (c.getX() - point.getX()) * (c.getX() - point.getX()) + (c.getY() - point.getY()) * (c.getY() - point.getY());

        double det = a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31 - a12 * a21 * a33
                - a11 * a23 * a32;

        if (isOrientedCCW()) {
            return det > 0.0d;
        }

        return det < 0.0d;
    }

    /**
     * Test if this triangle is oriented counterclockwise (CCW). Let A, B and C be three 2D points. If det &gt; 0, C
     * lies to the left of the directed line AB. Equivalently the triangle ABC is oriented counterclockwise. When det
     * &lt; 0, C lies to the right of the directed line AB, and the triangle ABC is oriented clockwise. When det = 0,
     * the three points are colinear. See Real-Time Collision Detection, chap. 3, p. 32
     *
     * @return Returns true iff the triangle ABC is oriented counterclockwise (CCW)
     */
    public boolean isOrientedCCW() {
        double a11 = a.getX() - c.getX();
        double a21 = b.getX() - c.getX();

        double a12 = a.getY() - c.getY();
        double a22 = b.getY() - c.getY();

        double det = a11 * a22 - a12 * a21;

        return det > 0.0d;
    }

    /**
     * Returns true if this triangle contains the given edge.
     *
     * @param edge The edge to be tested
     * @return Returns true if this triangle contains the edge
     */
    public boolean isNeighbour(JavaLine2D edge) {
        return (a.equals(edge.getStartPoint()) || b.equals(edge.getStartPoint()) || c.equals(edge.getStartPoint())) &&
                (a.equals(edge.getEndPoint()) || b.equals(edge.getEndPoint()) || c.equals(edge.getEndPoint()));
    }

    /**
     * Returns the vertex of this triangle that is not part of the given edge.
     *
     * @param edge The edge
     * @return The vertex of this triangle that is not part of the edge
     */
    public JavaPoint2D getNoneEdgeVertex(JavaLine2D edge) {
        if (!a.equals(edge.getStartPoint()) && !a.equals(edge.getEndPoint())) {
            return a;
        } else if (!b.equals(edge.getStartPoint()) && !b.equals(edge.getEndPoint())) {
            return b;
        } else if (!c.equals(edge.getStartPoint()) && !c.equals(edge.getEndPoint())) {
            return c;
        }

        return null;
    }

    /**
     * Returns true if the given vertex is one of the vertices describing this triangle.
     *
     * @param vertex The vertex to be tested
     * @return Returns true if the Vertex is one of the vertices describing this triangle
     */
    @SuppressWarnings("java:S1698")
    public boolean hasVertex(JavaPoint2D vertex) {
        return a == vertex || b == vertex || c == vertex;

    }

    @Override
    public String toString() {
        return "JavaTriangle[" + a + ", " + b + ", " + c + "]";
    }

    @Override
    protected JavaLine2D createLine(JavaPoint2D p1, JavaPoint2D p2) {
        return new JavaLine2D(p1, p2);
    }
}