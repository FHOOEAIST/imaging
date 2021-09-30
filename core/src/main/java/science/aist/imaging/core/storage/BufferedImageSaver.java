/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.storage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * <p>Saves a {@link BufferedImage} using {@link ImageIO}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class BufferedImageSaver implements BiConsumer<BufferedImage, String> {
    /**
     * Saves a buffered image
     *
     * @param image    the image to be saved
     * @param savePath the path where the image should be saved to.
     */
    @Override
    public void accept(BufferedImage image, String savePath) {
        String fileEnding = savePath.substring(savePath.lastIndexOf('.') + 1);
        if (!Arrays.asList(ImageIO.getReaderFormatNames()).contains(fileEnding))
            throw new RuntimeException("Type not supported");

        try {
            ImageIO.write(image, fileEnding, new File(savePath));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
