/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.storage;

import lombok.Setter;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;
import science.aist.imaging.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.function.Function;

/**
 * <p>Loads a 2 Byte Image by InputStream using the {@link BufferedImageInputStreamLoader}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class Image2ByteInputStreamLoader implements Function<InputStream, ImageWrapper<short[][][]>> {

    /**
     * Load the image using buffered image loader
     */
    private BufferedImageInputStreamLoader loader = new BufferedImageInputStreamLoader();
    /**
     * Convert a Buffered Image into a 8 Byte image
     */
    private GenericImageWrapperTransformer<double[][][], BufferedImage> transformerBIto8Byte = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(double[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));
    /**
     * Convert the 8 Byte image into a 2 Byte image
     */
    private Image2ByteToImage8ByteTransformer transformer8ByteTo2Byte = new Image2ByteToImage8ByteTransformer();
    /**
     * Convert the image into a greyscale
     */
    @Setter
    private ColoredToGreyscaleFunction<short[][][], short[][][]> greyscaleFunction = new ColoredToGreyscaleFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

    /**
     * If the image should be loaded as greyscale image, default is true
     */
    @Setter
    private boolean greyscale = true;

    public Image2ByteInputStreamLoader() {
        greyscaleFunction.setColorToGreyScale(new GreyscaleAverageConverter());
    }

    @Override
    public ImageWrapper<short[][][]> apply(InputStream inputStream) {
        Function<InputStream, ImageWrapper<short[][][]>> func = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(ImageFunction.closeAfterApply(transformer8ByteTo2Byte::transformTo));

        if (greyscale) {
            func = func.andThen(i -> i.getChannelType() == ChannelType.GREYSCALE ? i : greyscaleFunction.andThenCloseInput().apply(i));
        }

        return func.apply(inputStream);
    }
}
