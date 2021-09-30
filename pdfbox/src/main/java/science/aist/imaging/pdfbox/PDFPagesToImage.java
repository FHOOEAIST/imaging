/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.pdfbox;

import lombok.Getter;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>Converts each page of a pdf into a buffered image</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Getter
@Setter
public class PDFPagesToImage implements Function<InputStream, Collection<BufferedImage>> {
    /**
     * DPI to convert
     */
    private float dpi = 600;

    /**
     * Image type to convert to
     */
    private ImageType imageType = ImageType.RGB;

    @Override
    public Collection<BufferedImage> apply(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            return IntStream
                    .range(0, document.getNumberOfPages())
                    .mapToObj(page -> wrapRendererImageWithDPI(pdfRenderer, page))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private BufferedImage wrapRendererImageWithDPI(PDFRenderer renderer, int page) {
        try {
            return renderer.renderImageWithDPI(page, dpi, imageType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
