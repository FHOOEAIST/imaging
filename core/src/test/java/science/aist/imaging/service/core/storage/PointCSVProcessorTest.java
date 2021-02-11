/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.storage;

import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.threedimensional.JavaPoint3D;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Test class for {@link PointCSVProcessor}</p>
 *
 * @author Christoph Praschl
 */
public class PointCSVProcessorTest {
    private PointCSVProcessor processor = new PointCSVProcessor(';');

    @Test
    public void testRead() throws IOException {
        // given
        ClassPathResource cpr = new ClassPathResource("points.csv");

        // when
        List<JavaPoint3D> read = processor.read(cpr.getFile(), true);

        // then
        Assert.assertTrue(read.contains(new JavaPoint3D(10.5, 5, 8.9)));
        Assert.assertTrue(read.contains(new JavaPoint3D(44, 66, 88)));
        Assert.assertTrue(read.contains(new JavaPoint3D(33, 35, 38)));
    }

    @Test
    public void testRead2() throws IOException {
        // given
        ClassPathResource cpr = new ClassPathResource("points.csv");

        // when
        List<JavaPoint3D> read = processor.read(cpr.getFile(), true, true);

        // then
        Assert.assertTrue(read.contains(new JavaPoint3D(10.5, 8.9, 5)));
        Assert.assertTrue(read.contains(new JavaPoint3D(44, 88, 66)));
        Assert.assertTrue(read.contains(new JavaPoint3D(33, 38, 35)));
    }

    @Test
    public void testWrite() throws IOException {
        // given
        List<JavaPoint3D> points = Arrays.asList(new JavaPoint3D(10.5, 5, 8.9),
                new JavaPoint3D(44, 66, 88),
                new JavaPoint3D(33, 35, 38));

        Path pointtest = Files.createTempFile("pointtest", ".csv");

        // when
        boolean write = processor.write(pointtest.toFile(), points);

        // then
        Assert.assertTrue(write);
        List<JavaPoint3D> read = processor.read(pointtest.toFile());

        for (JavaPoint3D javaPoint3D : read) {
            Assert.assertTrue(points.contains(javaPoint3D));
        }


        Files.deleteIfExists(pointtest);
    }
}