/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */


package science.aist.imaging.mesh.storage.stl;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.mesh.storage.BaseMeshStorageTest;
import science.aist.jack.persistence.filesystem.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * <p>Test to ensure the read/write integrity of the polygon order of the stl reader and writer</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class StlTest extends BaseMeshStorageTest {

    @Test
    public void testWrite() throws IOException {
        // given
        Path path = FileUtils.createTempFile("stltest", ".stl");
        JavaModel3D cube = getCube();
        StlWriter writer = new StlWriter();
        StlReader reader = new StlReader();

        // when 1
        boolean write = writer.write(cube, path.toFile());

        // when 2
        Optional<JavaModel3D> read = reader.read(path.toFile());

        // then
        Assert.assertTrue(write);
        Assert.assertTrue(read.isPresent());
        Assert.assertEquals(cube.getMesh(), read.get().getMesh());
        Files.deleteIfExists(path);
    }
}
