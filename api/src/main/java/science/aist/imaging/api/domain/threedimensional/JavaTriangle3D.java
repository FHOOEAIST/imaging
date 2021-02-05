/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import science.aist.imaging.api.domain.Triangle;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;


/**
 * <p>Java representation of a 3D triangle</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
public class JavaTriangle3D extends JavaPolygon3D implements Triangle<JavaPoint3D> {
    protected JavaPoint3D a;
    protected JavaPoint3D b;
    protected JavaPoint3D c;

    public JavaTriangle3D(JavaPoint3D a, JavaPoint3D b, JavaPoint3D c) {
        super(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JavaTriangle3D that = (JavaTriangle3D) o;
        return Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Objects.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), a, b, c);
    }

    @Override
    protected JavaLine3D createLine(JavaPoint3D p1, JavaPoint3D p2) {
        return new JavaLine3D(p1, p2);
    }

    /**
     * Determines the point of intersection between this triangle and a line
     * (source: https://stackoverflow.com/questions/5666222/3d-line-plane-intersection)
     *
     * @param line to intersect with
     * @return Optional containing intersection point or {@link Optional#empty()} if line is parallel to triangle
     */
    public Optional<JavaPoint3D> getIntersection(JavaLine3D line) {
        return getIntersection(this.getA(), line);
    }

    /**
     * @return Calculates the normalvector of this triangle via the vectors AB and AC
     */
    @Override
    protected JavaPoint3D calculateNormalvector() {
        JavaPoint3D v1 = b.sub(a);
        JavaPoint3D v2 = c.sub(a);

        return v1.crossProduct(v2);
    }
}
