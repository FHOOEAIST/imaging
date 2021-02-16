/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage.stl;

import lombok.Setter;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;
import science.aist.imaging.api.domain.threedimensional.JavaPolygon3D;
import science.aist.imaging.service.mesh.storage.MeshWriter;
import science.aist.seshat.Logger;

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * <p>MeshWriter implementation for Stl files</p>
 * Based on STL file definition by <a href="http://paulbourke.net/dataformats/stl/">Paul Bourke</a>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class StlWriter implements MeshWriter {
    private static final Logger logger = Logger.getInstance(StlWriter.class);

    /**
     * Formatter definition for formating the point coordinates
     */
    @Setter
    private DecimalFormat formatter = new DecimalFormat("0.######E0####", new DecimalFormatSymbols(Locale.ENGLISH));


    @Override
    public boolean write(JavaModel3D mesh, Writer writer) {
        try {
            String lineSeperator = System.getProperty("line.separator");

            // write ply header
            writer.append("solid ")
                  .append(lineSeperator);

            for (JavaPolygon3D poly : mesh.getMesh()) {
                writer.append("  facet normal 0.0 0.0 0.0")
                        .append(lineSeperator)
                        .append("  outer loop ")
                        .append(lineSeperator);
                for (JavaPoint3D point : poly.getPoints()) {
                    writer.append("    vertex")
                            .append(" ")
                            .append(formatter.format(point.getX()))
                            .append(" ")
                            .append(formatter.format(point.getY()))
                            .append(" ")
                            .append(formatter.format(point.getZ()))
                            .append(lineSeperator);
                }
                writer.append("  endloop")
                        .append(lineSeperator)
                        .append("  endfacet")
                        .append(lineSeperator);
            }
            writer.append("endsolid ");

            return true;
        } catch (IOException e) {
            logger.debug("Could not create mesh file: " + e.getMessage());
            return false;
        }
    }
}
