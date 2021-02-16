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
import science.aist.imaging.service.mesh.storage.MeshReader;

import java.io.BufferedReader;
import java.util.Optional;

/**
 * <p>MeshReader implementation for PLY files</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class PlyReader implements MeshReader {
    @Override
    public Optional<JavaModel3D> read(BufferedReader reader) {
        return Optional.empty();
    }
}
