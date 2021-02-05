/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.tesseract.imageprocessing.opticalcharacterrecognition.tesseract;

import science.aist.imaging.api.domain.ocr.OCRResult;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andreas Pointner
 */
@ContextConfiguration("classpath:tesseract-config.xml")
public class TesseractOCRTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TesseractOCR ocr;

    @Test
    void testDoOCR() {
        // given

        Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
        ImageWrapper<short[][][]> ji = loader.apply(getClass().getResourceAsStream("/example1.png"));

        // when
        OCRResult ocrResult = ocr.apply(ji);

        // then
        Assert.assertEquals(ocrResult.getResultString(), "Ich bin ein Testtext");
        Assert.assertEquals(ocrResult.getCharacterInformation().stream().findFirst().orElseThrow(() -> new IllegalStateException("No element")).getConfidence(), 99.62, 0.01);
    }
}
