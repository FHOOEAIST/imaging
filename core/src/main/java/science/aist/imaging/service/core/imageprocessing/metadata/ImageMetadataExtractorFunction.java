/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.metadata;

import lombok.CustomLog;
import science.aist.imaging.api.domain.imagemetadata.ImageMetaData;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.Iterator;
import java.util.function.Function;

/**
 * <p>Class that extract the meta data from a given {@link ImageInputStream}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
public class ImageMetadataExtractorFunction implements Function<ImageInputStream, ImageMetaData> {
    @Override
    public ImageMetaData apply(ImageInputStream imageInputStream) {
        try {
            // https://stackoverflow.com/a/21027398
            Iterator<ImageReader> it = ImageIO.getImageReaders(imageInputStream);
            if (!it.hasNext()) {
                throw new IllegalStateException("No Image reader found");
            }
            ImageReader reader = it.next();
            reader.setInput(imageInputStream);

            IIOMetadata meta = reader.getImageMetadata(0);
            IIOMetadataNode root = (IIOMetadataNode) meta.getAsTree("javax_imageio_1.0");

            // https://stackoverflow.com/a/9996266
            JAXBContext context = JAXBContext.newInstance(ImageMetaData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ImageMetaData) unmarshaller.unmarshal(root);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return null;
        }
    }
}
