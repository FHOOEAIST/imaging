/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */


package science.aist.imaging.service.mesh.storage.ply;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.service.mesh.storage.BaseMeshStorageTest;
import science.aist.jack.persistence.filesystem.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * <p>Test to ensure the read/write integrity of the polygon order of the ply reader and writer</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class PlyTest extends BaseMeshStorageTest {

    @Test
    public void testWrite() throws IOException {
        // given
        Path path = FileUtils.createTempFile("plytest", ".ply");
        JavaModel3D cube = getCube();
        PlyWriter writer = new PlyWriter();
        PlyReader reader = new PlyReader();

        // when 1
        boolean write = writer.write(cube, path.toFile());
        Assert.assertTrue(write);

        // when 2
        Optional<JavaModel3D> read = reader.read(path.toFile());
        Assert.assertTrue(read.isPresent());

        // then
        Assert.assertEquals(cube.getMesh(), read.get().getMesh());
        Files.deleteIfExists(path);
    }
}
