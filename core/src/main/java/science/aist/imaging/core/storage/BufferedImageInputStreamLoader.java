/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.storage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.Function;

/**
 * <p>Loads a Buffered Image from a given Input Stream</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class BufferedImageInputStreamLoader implements Function<InputStream, BufferedImage> {
    @Override
    public BufferedImage apply(InputStream inputStream) {
        try {
            return javax.imageio.ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
