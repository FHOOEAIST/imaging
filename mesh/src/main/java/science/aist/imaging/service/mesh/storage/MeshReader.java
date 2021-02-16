/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage;

import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.seshat.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>Interface for reading a mesh from a file</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public interface MeshReader {
    /**
     * Reads a given mesh from a given file defined by its path
     * @param path path to the file
     * @return the read mesh or empty if read operation was not successful
     */
    default Optional<JavaModel3D> read(String path) {
        return read(new File(path));
    }

    /**
     * Reads a given mesh from a given file
     * @param file containing the mesh
     * @return the read mesh or empty if read operation was not successful
     */
    default Optional<JavaModel3D> read(File file) {
        try(InputStream stream = new FileInputStream(file)){
            return read(stream);
        } catch (IOException e) {
            Logger.getInstance(this.getClass()).debug("Could not read the file: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Reads a given mesh from a given inputstream
     * @param inputStream containing the mesh
     * @return the read mesh or empty if read operation was not successful
     */
    default Optional<JavaModel3D> read(InputStream inputStream) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            return read(reader);
        } catch (IOException e) {
            Logger.getInstance(this.getClass()).debug("Could not read the input stream: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Reads a given mesh from a given reader
     * @param reader accesing the mesh data
     * @return the read mesh or empty if read operation was not successful
     */
    Optional<JavaModel3D> read(BufferedReader  reader);
}