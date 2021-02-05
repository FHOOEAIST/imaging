/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.storage;

import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVImageWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Christoph Praschl
 */
public class OpenCVSaverTest extends OpenCVTest {
    /**
     * Tests ImageWrapper.saveImage function
     */
    @Test
    void testSaveImage() {
        // given - load original image
        String imagePath = "/logo/";
        String fileName = "original.tif";
        String newFileName = "savetest.jpg";
        OpenCVImageWrapper wrapper = loadImageFromClassPath(imagePath + fileName);
        OpenCVSaver saver = new OpenCVSaver();


        // when - resave new image
        saver.accept(wrapper, newFileName);

        // then - check if file exists

        String path = OpenCVSaverTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        File f = new File(path.substring(0, path.lastIndexOf("target")) + newFileName);
        Assert.assertTrue(f.isFile());
        Assert.assertTrue(f.delete());
    }
}
