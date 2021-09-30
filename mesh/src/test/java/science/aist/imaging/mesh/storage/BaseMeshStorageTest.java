/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.mesh.storage;

import org.testng.Assert;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.domain.threedimensional.JavaPolygon3D;
import science.aist.seshat.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Base implementation of a mesh reader test</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class BaseMeshStorageTest {
    private static final Logger logger = Logger.getInstance(BaseMeshStorageTest.class);

    protected void compareBufferedReaders(BufferedReader r1, BufferedReader r2) {
        try {
            while (true) {
                String line1 = r1.readLine();
                String line2 = r2.readLine();
                Assert.assertEquals(line1, line2);
                if (line1 == null || line2 == null) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.debug(e);
        }
    }

    /**
     * @return a simple cube model
     */
    protected JavaModel3D getCube() {

        List<JavaPolygon3D> polys = Arrays.asList(
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 0.0),
                        new JavaPoint3D(1.0, 1.0, 0.0),
                        new JavaPoint3D(1.0, 0.0, 0.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 0.0),
                        new JavaPoint3D(0.0, 1.0, 0.0),
                        new JavaPoint3D(1.0, 1.0, 0.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 0.0),
                        new JavaPoint3D(0.0, 1.0, 1.0),
                        new JavaPoint3D(0.0, 1.0, 0.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 0.0),
                        new JavaPoint3D(0.0, 0.0, 1.0),
                        new JavaPoint3D(0.0, 1.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 1.0, 0.0),
                        new JavaPoint3D(1.0, 1.0, 1.0),
                        new JavaPoint3D(1.0, 1.0, 0.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 1.0, 0.0),
                        new JavaPoint3D(0.0, 1.0, 1.0),
                        new JavaPoint3D(1.0, 1.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(1.0, 0.0, 0.0),
                        new JavaPoint3D(1.0, 1.0, 0.0),
                        new JavaPoint3D(1.0, 1.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(1.0, 0.0, 0.0),
                        new JavaPoint3D(1.0, 1.0, 1.0),
                        new JavaPoint3D(1.0, 0.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 0.0),
                        new JavaPoint3D(1.0, 0.0, 0.0),
                        new JavaPoint3D(1.0, 0.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 0.0),
                        new JavaPoint3D(1.0, 0.0, 1.0),
                        new JavaPoint3D(0.0, 0.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 1.0),
                        new JavaPoint3D(1.0, 0.0, 1.0),
                        new JavaPoint3D(1.0, 1.0, 1.0)),
                new JavaPolygon3D(
                        new JavaPoint3D(0.0, 0.0, 1.0),
                        new JavaPoint3D(1.0, 1.0, 1.0),
                        new JavaPoint3D(0.0, 1.0, 1.0)));
        return new JavaModel3D(polys);
    }

    protected void checkCubePoints(JavaModel3D model){
        Set<JavaPoint3D> cubePoints = new HashSet<>(getCube().getPoints());
        Set<JavaPoint3D> modelPoints = new HashSet<>(model.getPoints());
        Assert.assertEquals(modelPoints, cubePoints);
    }

}
