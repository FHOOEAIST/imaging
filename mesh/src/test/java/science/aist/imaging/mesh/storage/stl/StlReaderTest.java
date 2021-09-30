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

import java.io.InputStream;
import java.util.Optional;

/**
 * <p>Test class for {@link StlReader}</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class StlReaderTest extends BaseMeshStorageTest {

    @Test
    public void testRead() {
        // given
        InputStream inputStream = StlReaderTest.class.getResourceAsStream("/stltest.stl");
        StlReader reader = new StlReader();

        // when
        Optional<JavaModel3D> read = reader.read(inputStream);

        // then
        Assert.assertTrue(read.isPresent());
        Assert.assertEquals(read.get().getPoints().stream().distinct().count(), 8L);
        Assert.assertEquals(read.get().getMesh().size(), 12);
        checkCubePoints(read.get());
    }

}
