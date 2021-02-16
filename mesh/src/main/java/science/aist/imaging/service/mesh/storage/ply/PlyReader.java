/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage.ply;

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
 * <p>MeshReader implementation for PLY files</p>
 * <p>Based on PLY definition by <a href="http://paulbourke.net/dataformats/ply/">Paul Bourke</a></p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class PlyReader implements MeshReader {
    private static final Logger logger = Logger.getInstance(PlyReader.class);
    private static final String COMMENT = "comment ";

    @Override
    public Optional<JavaModel3D> read(BufferedReader reader) {
        try {
            // process ply file header
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.startsWith(COMMENT) && trimmed.toLowerCase().startsWith("ply")) {
                    // find the start of the off file defined by the OFF string
                    break;
                }
            }

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.startsWith(COMMENT)) {
                    // ignore all comments
                    break;
                }
            }

            if (line == null) {
                throw new IllegalStateException("PLY file is empty");
            }

            String[] formatDefinition = line.trim().replaceAll(" +", " ").split(" ");

            if (!"format".equalsIgnoreCase(formatDefinition[0]) &&
                    !"ascii".equalsIgnoreCase(formatDefinition[1])) {
                logger.debug("PlyReader only supports ASCII based ply files");
                return Optional.empty();
            }

            int numOfVertices = 0;
            List<String> vertexDefinition = new ArrayList<>();
            boolean inVertexDefinition = false;
            int numOfFaces = 0;


            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim().replaceAll(" +", " ").toLowerCase();
                if (trimmed.startsWith(COMMENT)) {
                    continue;
                } else if ("end_header".equalsIgnoreCase(trimmed)) {
                    break;
                }

                if (trimmed.startsWith("element  ")) {
                    String[] elementDefinition = trimmed.split(" ");
                    if ("vertex".equalsIgnoreCase(elementDefinition[1])) {
                        numOfVertices = Integer.parseInt(elementDefinition[2]);
                        inVertexDefinition = true;
                    } else {
                        inVertexDefinition = false;
                    }

                    if ("face".equalsIgnoreCase(elementDefinition[1])) {
                        numOfFaces = Integer.parseInt(elementDefinition[2]);
                    }
                }

                if (inVertexDefinition && (trimmed.startsWith("property "))) {
                    vertexDefinition.add(trimmed.split(" ")[2]);
                }
            }

            // start processing ply file body
            int visitedVertices = 0;

            List<JavaPoint3D> points = new ArrayList<>();
            List<JavaPolygon3D> polygons = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim().replaceAll(" +", " ").toLowerCase();
                if (trimmed.startsWith(COMMENT)) {
                    continue;
                }
                String[] splits = trimmed.split(" ");

                if (visitedVertices < numOfVertices) {
                    double x = 0;
                    double y = 0;
                    double z = 0;
                    for (int i = 0; i < vertexDefinition.size(); i++) {
                        String s = vertexDefinition.get(i);
                        if ("x".equalsIgnoreCase(s)) {
                            x = Double.parseDouble(splits[i]);
                        } else if ("y".equalsIgnoreCase(s)) {
                            y = Double.parseDouble(splits[i]);
                        } else if ("z".equalsIgnoreCase(s)) {
                            z = Double.parseDouble(splits[i]);
                        }
                    }
                    points.add(new JavaPoint3D(x, y, z));

                    visitedVertices++;
                } else {
                    List<JavaPoint3D> polygonPoints = new ArrayList<>();
                    for (int i = 1; i < splits.length; i++) {
                        String split = splits[i];
                        polygonPoints.add(points.get(Integer.parseInt(split)));
                    }
                    polygons.add(new JavaPolygon3D(polygonPoints));
                }
            }

            if (numOfFaces != polygons.size()) {
                logger.debug("Something went wrong parsing the file: Number of read polygons does not match ply header");
                return Optional.empty();
            }

            return Optional.of(new JavaModel3D(polygons));
        } catch (IOException e) {
            logger.debug("Problem when reading obj file: " + e.getMessage());
            return Optional.empty();
        }
    }
}
