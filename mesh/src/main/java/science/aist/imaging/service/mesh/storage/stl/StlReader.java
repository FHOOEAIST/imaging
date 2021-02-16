/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage.stl;

import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.domain.threedimensional.JavaPolygon3D;
import science.aist.imaging.service.mesh.storage.MeshReader;
import science.aist.seshat.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>MeshReader implementation for Stl files</p>
 * Based on STL file definition by <a href="http://paulbourke.net/dataformats/stl/">Paul Bourke</a>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class StlReader implements MeshReader {
    private static final Logger logger = Logger.getInstance(StlReader.class);

    @Override
    public Optional<JavaModel3D> read(BufferedReader reader) {
        try {
            String line;
            boolean inFaceDefinition = false;
            boolean inLoop = false;

            List<JavaPolygon3D> polygons = new ArrayList<>();
            List<JavaPoint3D> currentPoints = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim().replaceAll(" +", " ").toLowerCase();
                if (!inFaceDefinition) {
                    if (trimmed.startsWith("solid ")) {
                        // find the start of the stl file defined by the OFF string
                        continue;
                    } else if (trimmed.startsWith("facet ")) {
                        inFaceDefinition = true;
                    } else if (trimmed.startsWith("endsolid ")) {
                        // find the end of the stl file defined by the OFF string
                        break;
                    }
                } else {
                    if (inLoop && trimmed.startsWith("vertex")) {
                        String[] splits = trimmed.split(" ");
                        int len = splits.length;
                        if (len < 3) {
                            throw new IllegalStateException("Found illegal vertex defintion: " + trimmed);
                        }
                        double x = Double.parseDouble(splits[1]);
                        double y = Double.parseDouble(splits[2]);
                        double z = 0;

                        if (len > 3) {
                            z = Double.parseDouble(splits[3]);
                        }
                        currentPoints.add(new JavaPoint3D(x,y,z));
                    } else if (trimmed.startsWith("outer loop")) {
                        inLoop = true;
                    } else if (trimmed.startsWith("endloop")) {
                        polygons.add(new JavaPolygon3D(currentPoints));
                        currentPoints = new ArrayList<>();
                        inLoop = false;
                    }
                }
            }
            return Optional.of(new JavaModel3D(polygons));
        } catch (IOException e) {
            logger.debug("Problem when reading obj file: " + e.getMessage());
            return Optional.empty();
        }
    }

}
