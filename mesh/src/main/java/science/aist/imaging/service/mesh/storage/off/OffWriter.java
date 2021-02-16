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
import science.aist.imaging.service.mesh.storage.MeshWriter;

import java.io.Writer;

/**
 * <p>MeshWriter implementation for OFF files</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class OffWriter implements MeshWriter {
    @Override
    public boolean write(JavaModel3D mesh, Writer writer) {
        return false;
    }
}
