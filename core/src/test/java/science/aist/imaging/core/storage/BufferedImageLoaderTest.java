/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.storage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.function.Function;

/**
 * <p>Tests {@link BufferedImageInputStreamLoader}. {@link BufferedImageFileLoader} is not really testable, as loading
 * from a direct path fails on jenkins, because the images from the resources are capsuled then </p>
 *
 * @author Andreas Pointner
 */
public class BufferedImageLoaderTest {

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    void testLoad() {
        // given
        Function<InputStream, BufferedImage> loader = new BufferedImageInputStreamLoader();

        // when
        BufferedImage img = loader.apply(getClass().getResourceAsStream("/logo/original.JPG"));

        // then
        Assert.assertNotNull(img);
        Assert.assertEquals(img.getWidth(), 196);
        Assert.assertEquals(img.getHeight(), 145);
        Assert.assertEquals(img.getType(), BufferedImage.TYPE_3BYTE_BGR);
    }
}
