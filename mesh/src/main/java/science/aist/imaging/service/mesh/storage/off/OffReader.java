/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage.off;

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
 * <p>MeshReader implementation for OFF files</p>
 * <p>Based on OFF definition by <a href="https://segeval.cs.princeton.edu/public/off_format.html">Princeton</a></p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class OffReader implements MeshReader {
    private static final Logger logger = Logger.getInstance(OffReader.class);

    @Override
    public Optional<JavaModel3D> read(BufferedReader reader) {
        try {

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.startsWith("#") && trimmed.toLowerCase().startsWith("off")) {
                    // find the start of the off file defined by the OFF string
                    break;
                }
            }
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.startsWith("#") && !trimmed.isEmpty()) {
                    // ignore comment and blank lines
                    break;
                }
            }

            if (line == null) {
                throw new IllegalStateException("Off file is empty");
            }

            String[] numberOfElements = line.trim().replace("\t", " ").replaceAll(" +", " ").split(" ");
            int numberOfVertices = Integer.parseInt(numberOfElements[0]);
            int numberOfFaces = Integer.parseInt(numberOfElements[1]);

            List<JavaPoint3D> points = new ArrayList<>();
            List<JavaPolygon3D> polygons = new ArrayList<>();

            int currentLine = 0;
            boolean readVertices = true;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim().replace("\t", " ").replaceAll(" +", " ");

                // ignore comment
                if (trimmed.startsWith("#")) {
                    continue;
                }

                String[] splits = trimmed.split(" ");
                int len = splits.length;

                if (readVertices) {
                    if (len > 1) {
                        double x = Double.parseDouble(splits[0]);
                        double y = Double.parseDouble(splits[1]);
                        double z = 0;
                        if (len > 2) {
                            z = Double.parseDouble(splits[2]);
                        }
                        points.add(new JavaPoint3D(x, y, z));
                    } else {
                        throw new IllegalStateException("Found invalid vertex definition: " + line);
                    }

                    if (currentLine == numberOfVertices - 1) {
                        readVertices = false;
                    }
                } else {
                    int numOfVertices = Integer.parseInt(splits[0]);
                    List<JavaPoint3D> polygonPoints = new ArrayList<>();
                    for (int i = 0; i < numOfVertices; i++) {
                        polygonPoints.add(points.get(Integer.parseInt(splits[i + 1])));
                    }
                    polygons.add(new JavaPolygon3D(polygonPoints));
                }

                currentLine++;
            }

            if(polygons.size() != numberOfFaces){
                logger.debug("Something went wrong parsing the file: Number of read polygons does not match off header");
                return Optional.empty();
            }

            return Optional.of(new JavaModel3D(polygons));
        } catch (IOException e) {
            logger.debug("Problem when reading obj file: " + e.getMessage());
            return Optional.empty();
        }
    }
}
