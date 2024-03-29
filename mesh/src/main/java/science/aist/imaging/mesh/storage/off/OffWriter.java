/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.mesh.storage.off;

import lombok.Setter;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.domain.threedimensional.JavaPolygon3D;
import science.aist.imaging.mesh.storage.MeshWriter;
import science.aist.seshat.Logger;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * <p>MeshWriter implementation for OFF files</p>
 * <p>Based on OFF definition by <a href="https://segeval.cs.princeton.edu/public/off_format.html">Princeton</a></p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class OffWriter implements MeshWriter {
    private static final Logger logger = Logger.getInstance(OffWriter.class);

    /**
     * Delta definition to define if a point is equal to another one
     */
    @Setter
    private double epsilon = 0.0001;

    @Override
    public boolean write(JavaModel3D mesh, Writer writer) {
        try {
            String lineSeperator = System.getProperty("line.separator");

            List<JavaPoint3D> points = new ArrayList<>();
            for (JavaPoint3D point : mesh.getPoints()) {
                if (points.stream().parallel().noneMatch(javaPoint3D -> javaPoint3D.positionalEqual(point, epsilon))) {
                    points.add(point);
                }
            }

            List<List<Integer>> polygonIndices = new ArrayList<>();
            List<JavaPolygon3D> meshMesh = mesh.getMesh();
            int numOfEdges = 0;
            for (int i = 0; i < meshMesh.size(); i++) {
                JavaPolygon3D polygon = meshMesh.get(i);
                List<Integer> indices = new ArrayList<>();
                for (JavaPoint3D point : polygon.getPoints()) {
                    OptionalInt first = IntStream.range(0, points.size()).parallel().filter(value -> points.get(value).positionalEqual(point, epsilon)).findFirst();
                    if (first.isPresent()) {
                        indices.add(first.getAsInt());
                    } else {
                        throw new IllegalStateException("Could not write polygon: " + i);
                    }
                    numOfEdges++;
                }
                polygonIndices.add(indices);
            }

            writer.append("OFF")
                    .append(lineSeperator)
                    .append(Integer.toString(points.size()))
                    .append(" ")
                    .append(Integer.toString(meshMesh.size()))
                    .append(" ")
                    .append(Integer.toString(numOfEdges))
                    .append(lineSeperator);

            for (JavaPoint3D point : points) {
                writer.append(Double.toString(point.getX()))
                        .append(" ")
                        .append(Double.toString(point.getY()))
                        .append(" ")
                        .append(Double.toString(point.getZ()))
                        .append(lineSeperator);
            }

            for (List<Integer> indices : polygonIndices) {
                writer.append(Integer.toString(indices.size()))
                        .append(" ");
                for (Integer index : indices) {
                    writer.append(Integer.toString(index))
                            .append(" ");
                }
                writer.append(lineSeperator);
            }

            return true;
        } catch (IOException e) {
            logger.debug("Could not create mesh file: " + e.getMessage());
            return false;
        }
    }
}
