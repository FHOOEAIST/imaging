/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.storage;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.CustomLog;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.function.BiConsumer;

/**
 * <p>Class for saving open cv images</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
public class OpenCVSaver implements BiConsumer<ImageWrapper<Mat>, String> {
    @Override
    public void accept(ImageWrapper<Mat> matImageWrapper, String path) {
        Mat image = matImageWrapper.getImage();
        if (image == null)
            throw new IllegalArgumentException("Wrapper does not contain an image, which could be saved.");
        boolean b = Imgcodecs.imwrite(path, image);
        // check if imwrite was successful -> if not delete file when necessary and throw exception
        if (!b) {
            // check if file has been created -> if so delete it
            File f = new File(path);
            if (f.exists()) {
                @SuppressWarnings("squid:S4042") boolean deleted = f.delete();
                log.debug("File has been deleted because of an error: " + deleted);
            }
            throw new IllegalStateException("Image could not be saved at path " + path);
        }
    }
}
