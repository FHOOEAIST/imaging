/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage.obj;

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
 * <p>MeshReader implementation for OBJ files</p>
 * <p>Based on OBJ definition by <a href="http://paulbourke.net/dataformats/obj/">Pual Bourke</a></p>
 * <p>Note: The reader currently only supports text based OBJ mesh files, not binaries!</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class ObjReader implements MeshReader {
    private static final Logger logger = Logger.getInstance(ObjReader.class);

    @Override
    public Optional<JavaModel3D> read(BufferedReader reader) {
        List<JavaPoint3D> points = new ArrayList<>();
        List<JavaPolygon3D> polygons = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().replace("\t", " ").replaceAll(" +", " ");
                if(line.isEmpty()) continue;

                String[] splits = line.split(" ");
                int len = splits.length;

                if (line.startsWith("v ")) {
                    if (len > 2) {
                        // element at index 0 is line identifier, which we are not interested
                        double x = Double.parseDouble(splits[1]);
                        double y = Double.parseDouble(splits[2]);
                        double z = 0;
                        if (len > 3) {
                            z = Double.parseDouble(splits[3]);
                        }
                        points.add(new JavaPoint3D(x, y, z));

                    } else {
                        throw new IllegalStateException("Found invalid vertex definition: " + line);
                    }


                } else if (line.startsWith("f ")) {
                    if(splits.length < 2){
                        throw new IllegalStateException("Found invalid face definition: " + line);
                    }

                    List<Integer> indices = new ArrayList<>();
                    if(line.contains("/")) {
                        for (int i = 1; i < splits.length; i++) {
                            String split = splits[i];
                            String[] faceElements = split.split("/");
                            // element 0 contains the vertex index everything else is for vectors
                            indices.add(Integer.parseInt(faceElements[0]));
                        }
                    } else {
                        for (int i = 1; i < splits.length; i++) {
                            String split = splits[i];
                            indices.add(Integer.parseInt(split));
                        }

                    }

                    List<JavaPoint3D> polygonPoints = new ArrayList<>();
                    for (Integer index : indices) {
                        if(index > 0){
                            polygonPoints.add(points.get(index - 1)); // OBJ does not use index 0; so index 1 is the first element
                        } else {
                            // a negative index defines reversed point order
                            polygonPoints.add(points.get(points.size() - index));
                        }
                    }
                    polygons.add(new JavaPolygon3D(polygonPoints));
                }
            }

            return Optional.of(new JavaModel3D(polygons));
        } catch (IOException e) {
            logger.debug("Problem when reading obj file: " + e.getMessage());
            return Optional.empty();
        }


    }
}
