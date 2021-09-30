/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.pdfbox;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Function;

/**
 * <p>Tests {@link ExtractImagesFromPDF}</p>
 *
 * @author Andreas Pointner
 */
public class ExtractImagesFromPDFTest {
    private Function<InputStream, Collection<BufferedImage>> extractImagesFromPDF;

    @BeforeTest
    void setUp() {
        extractImagesFromPDF = new ExtractImagesFromPDF();
    }

    @Test
    void testApply() {
        // given
        InputStream is = getClass().getResourceAsStream("/test.pdf");

        // when
        Collection<BufferedImage> bufferedImages = extractImagesFromPDF.apply(is);

        // then
        Assert.assertNotNull(bufferedImages);
        Assert.assertEquals(bufferedImages.size(), 3);
    }
}
