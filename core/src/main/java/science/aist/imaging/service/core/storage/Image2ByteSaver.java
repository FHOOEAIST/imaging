/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.storage;

import lombok.Setter;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.service.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;
import science.aist.jack.general.transformer.Transformer;

import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

/**
 * <p>Class for saving 2 byte images</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class Image2ByteSaver implements BiConsumer<ImageWrapper<short[][][]>, String> {
    private Transformer<ImageWrapper<short[][][]>, ImageWrapper<double[][][]>> transformer8ByteTo2Byte = new Image2ByteToImage8ByteTransformer();
    private GenericImageWrapperTransformer<double[][][], BufferedImage> image8ByteToBufferedImageTransformer = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(double[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));

    @Override
    public void accept(ImageWrapper<short[][][]> image, String fileName) {
        ImageWrapper<double[][][]> imageWrapper = transformer8ByteTo2Byte.transformFrom(image);
        ImageWrapper<BufferedImage> bufferedImageImageWrapper = image8ByteToBufferedImageTransformer.transformFrom(imageWrapper);
        new BufferedImageSaver().accept(bufferedImageImageWrapper.getImage(), fileName);
    }
}
