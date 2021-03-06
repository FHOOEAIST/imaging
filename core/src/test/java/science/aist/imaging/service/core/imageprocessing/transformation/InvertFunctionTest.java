/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.service.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;
import science.aist.imaging.service.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.service.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;
import science.aist.imaging.service.core.storage.BufferedImageInputStreamLoader;

import java.awt.image.BufferedImage;

/**
 * <p>Tests {@link InvertFunction}</p>
 *
 * @author Andreas Pointner
 */
public class InvertFunctionTest {

    private final BufferedImageInputStreamLoader loader = new BufferedImageInputStreamLoader();
    private final Image2ByteToImage8ByteTransformer transformer8ByteTo2Byte = new Image2ByteToImage8ByteTransformer();
    private final ColoredToGreyscaleFunction<short[][][], short[][][]> coloredToGreyscale = new ColoredToGreyscaleFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
    private GenericImageWrapperTransformer<double[][][], BufferedImage> transformerBIto8Byte = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(double[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));

    @BeforeTest
    void setUp() {
        coloredToGreyscale.setColorToGreyScale(new GreyscaleAverageConverter());
    }

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    public void testInvertWithGreyscaleImage() {
        // given
        ImageWrapper<short[][][]> ji = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .andThen(coloredToGreyscale)
                .apply(getClass().getResourceAsStream("/logo/original.JPG"));
        ImageWrapper<short[][][]> ref = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .andThen(coloredToGreyscale)
                .apply(getClass().getResourceAsStream("/logo/invertedGreyScale.bmp"));
        InvertFunction<short[][][], short[][][]> invert = new InvertFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

        // when
        ImageWrapper<short[][][]> inv = invert.apply(ji);

        // then
        Assert.assertEquals(inv, ref);
    }

    @Test
    public void testInvertWithColoredImage() {
        // given
        ImageWrapper<short[][][]> ji = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .apply(getClass().getResourceAsStream("/logo/original.JPG"));
        ImageWrapper<short[][][]> ref = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .apply(getClass().getResourceAsStream("/logo/inverted.bmp"));
        InvertFunction<short[][][], short[][][]> invert = new InvertFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

        // when
        ImageWrapper<short[][][]> inv = invert.apply(ji);

        // then
        Assert.assertEquals(inv, ref);
    }
}
