/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.mesh.storage;

import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.seshat.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>Interface for writing a mesh to a file</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public interface MeshWriter {
    /**
     * Write a given mesh to a file defined by its path
     * @param mesh to be written
     * @param path path to the file
     * @return true iff write operation was successful
     */
    default boolean write(JavaModel3D mesh, String path) {
        return write(mesh, new File(path));
    }

    /**
     * Write a given mesh to a file
     * @param mesh to be written
     * @param file to which mesh should be written
     * @return true iff write operation was successful
     */
    default boolean write(JavaModel3D mesh, File file){
        try(OutputStream stream = new FileOutputStream(file)) {
            return write(mesh, stream);
        } catch (IOException e) {
            Logger.getInstance(this.getClass()).debug("Could not write to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Write a given mesh to a given outputstream
     * @param mesh to be written
     * @param outputStream to be written to
     * @return true iff write operation was successful
     */
    default boolean write(JavaModel3D mesh, OutputStream outputStream) {
        try (OutputStreamWriter fileWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            return write(mesh, fileWriter);
        } catch (IOException e) {
            Logger.getInstance(this.getClass()).debug("Could not write to outputStream: " + e.getMessage());
            return false;
        }
    }

    /**
     * Write a given mesh using the given writer
     * @param mesh to be written
     * @param writer writer used to store mesh
     * @return true iff write operation was successful
     */
    boolean write(JavaModel3D mesh, Writer writer);

}