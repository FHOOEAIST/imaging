/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.mesh.storage.off;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.mesh.storage.BaseMeshStorageTest;
import science.aist.jack.persistence.filesystem.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Test class for {@link OffWriter}</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class OffWriterTest extends BaseMeshStorageTest {

    @Test
    public void testWrite() throws IOException {
        // given
        Path path = FileUtils.createTempFile("offtest", ".off");
        JavaModel3D cube = getCube();
        OffWriter writer = new OffWriter();

        InputStream inputStream = OffWriterTest.class.getResourceAsStream("/offtest.off");

        // when
        boolean write = writer.write(cube, path.toFile());

        // then
        Assert.assertTrue(write);

        try(BufferedReader r1 = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8));
            BufferedReader r2 = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            compareBufferedReaders(r1, r2);
        }
        Files.deleteIfExists(path);
    }


}
