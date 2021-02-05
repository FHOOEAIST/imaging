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
import lombok.ToString;

import java.util.Objects;

/**
 * <p>Extension of the JavaPoint3D class containing normal vector information</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
@ToString(callSuper = true)
public class NormalJavaPoint3D extends JavaPoint3D {
    protected double nx;
    protected double ny;
    protected double nz;

    public NormalJavaPoint3D(JavaPoint3D p, double nx, double ny, double nz) {
        this(p.getX(), p.getY(), p.getZ(), nx, ny, nz);
    }

    public NormalJavaPoint3D(double x, double y, double z, double nx, double ny, double nz) {
        super(x, y, z);
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalJavaPoint3D that = (NormalJavaPoint3D) o;

        final double EPSILON = 0.00000001f;
        return Math.abs(getX() - that.getX()) < EPSILON &&
                Math.abs(getY() - that.getY()) < EPSILON &&
                Math.abs(getZ() - that.getZ()) < EPSILON &&
                Math.abs(getNx() - that.getNx()) < EPSILON &&
                Math.abs(getNy() - that.getNy()) < EPSILON &&
                Math.abs(getNz() - that.getNz()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nx, ny, nz);
    }
}
