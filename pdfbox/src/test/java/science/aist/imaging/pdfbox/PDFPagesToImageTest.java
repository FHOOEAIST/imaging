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
import java.util.Iterator;
import java.util.function.Function;

/**
 * <p>Test {@link PDFPagesToImage}</p>
 *
 * @author Andreas Pointner
 */
public class PDFPagesToImageTest {
    private Function<InputStream, Collection<BufferedImage>> pdfPagesToImage;

    @BeforeTest
    void setUp() {
        pdfPagesToImage = new PDFPagesToImage();
    }

    @Test
    void testApply1() {
        // given
        InputStream is = getClass().getResourceAsStream("/test.pdf");

        // when
        Collection<BufferedImage> result = pdfPagesToImage.apply(is);

        // then
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2);
        Iterator<BufferedImage> iterator = result.iterator();
        BufferedImage bi1 = iterator.next();
        Assert.assertEquals(bi1.getHeight(), 7014);
        Assert.assertEquals(bi1.getWidth(), 4961);
        BufferedImage bi2 = iterator.next();
        Assert.assertEquals(bi2.getHeight(), 7014);
        Assert.assertEquals(bi2.getWidth(), 4961);
    }

}
