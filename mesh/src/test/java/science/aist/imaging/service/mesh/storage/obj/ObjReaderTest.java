/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.mesh.storage.obj;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.threedimensional.JavaModel3D;
import science.aist.imaging.service.mesh.storage.BaseMeshStorageTest;

import java.io.InputStream;
import java.util.Optional;

/**
 * <p>Test class for {@link ObjReader}</p>
 *
 * @author Christoph Praschl christoph.praschl@fh-hagenberg.at
 * @since 1.2
 */
public class ObjReaderTest extends BaseMeshStorageTest {

    @Test
    public void testRead() {
        // given
        InputStream inputStream = ObjReaderTest.class.getResourceAsStream("/objtest.obj");
        ObjReader reader = new ObjReader();

        // when
        Optional<JavaModel3D> read = reader.read(inputStream);

        // then
        Assert.assertTrue(read.isPresent());
        Assert.assertEquals(read.get().getPoints().stream().distinct().count(), 8L);
        Assert.assertEquals(read.get().getMesh().size(), 12);
        checkCubePoints(read.get());
    }

}
