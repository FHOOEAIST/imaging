/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import science.aist.imaging.api.domain.AbstractJavaPolygon;

import java.util.Collection;

/**
 * <p>3D polygon</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class JavaPolygon3D extends AbstractJavaPolygon<JavaPoint3D, JavaLine3D> {
    public JavaPolygon3D(JavaPoint3D... points) {
        super(points);
    }

    public JavaPolygon3D(Collection<JavaPoint3D> points) {
        super(points);
    }

    @Override
    protected JavaLine3D createLine(JavaPoint3D p1, JavaPoint3D p2) {
        return new JavaLine3D(p1, p2);
    }

    @Override
    protected JavaPoint3D createPoint(double x, double y, double z) {
        return new JavaPoint3D(x, y, z);
    }

    @Override
    protected JavaPoint3D calculateNormalvector() {
        float x = 0;
        float y = 0;
        float z = 0;
        int cnt = 0;

        for (JavaPoint3D point : getPoints()) {
            if (point instanceof NormalJavaPoint3D) {
                x += ((NormalJavaPoint3D) point).getNx();
                y += ((NormalJavaPoint3D) point).getNy();
                z += ((NormalJavaPoint3D) point).getNz();
            } else {
                x += point.getX();
                y += point.getY();
                z += point.getZ();
            }
            cnt++;
        }

        if (cnt > 0) {
            return new JavaPoint3D(x / cnt, y / cnt, z / cnt);
        } else {
            return new JavaPoint3D();
        }
    }
}
