/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.pdfbox;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Extracts all Images from the pdf and returns them as a list of buffered images</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class ExtractImagesFromPDF implements Function<InputStream, Collection<BufferedImage>> {
    private static PDXObject getXObjectWrapper(PDResources res, COSName name) {
        try {
            return res.getXObject(name);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static BufferedImage getImageWrapper(PDImageXObject pdImageXObject) {
        try {
            return pdImageXObject.getImage();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Collection<BufferedImage> apply(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            return StreamSupport.stream(document.getPages().spliterator(), false)
                    .map(PDPage::getResources)
                    .flatMap(res -> StreamSupport
                            .stream(res.getXObjectNames().spliterator(), false)
                            .map(name -> getXObjectWrapper(res, name)))
                    .filter(PDImageXObject.class::isInstance)
                    .map(PDImageXObject.class::cast)
                    .map(ExtractImagesFromPDF::getImageWrapper)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
