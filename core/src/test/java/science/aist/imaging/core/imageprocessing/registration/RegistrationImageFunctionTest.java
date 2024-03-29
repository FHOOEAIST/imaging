/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.registration;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.imageprocessing.fitnessfunction.SSEFitnessFunction;
import science.aist.imaging.core.imageprocessing.interpolation.BilinearInterpolationFunction;
import science.aist.imaging.core.imageprocessing.transformation.TransformFunction;
import science.aist.imaging.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;
import science.aist.imaging.core.storage.BufferedImageInputStreamLoader;
import science.aist.imaging.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;

import java.awt.image.BufferedImage;

/**
 * <p>Test class for {@link RegistrationImageFunction}</p>
 *
 * @author Christoph Praschl
 */
public class RegistrationImageFunctionTest {

    private final BufferedImageInputStreamLoader loader = new BufferedImageInputStreamLoader();
    private final GenericImageWrapperTransformer<double[][][], BufferedImage> transformerBIto8Byte = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(double[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));
    private final Image2ByteToImage8ByteTransformer transformer8ByteTo2Byte = new Image2ByteToImage8ByteTransformer();
    private final ColoredToGreyscaleFunction<short[][][], short[][][]> coloredToGreyscale = new ColoredToGreyscaleFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

    @BeforeTest
    void setUp() {
        coloredToGreyscale.setColorToGreyScale(new GreyscaleAverageConverter());
    }

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }


    @Test
    public void testApply() {
        // given
        ImageWrapper<short[][][]> input = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .andThen(coloredToGreyscale)
                .apply(getClass().getResourceAsStream("/logo/original.JPG"));

        ImageWrapper<short[][][]> input2 = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .andThen(coloredToGreyscale)
                .apply(getClass().getResourceAsStream("/logo/original.JPG"));

        RegistrationImageFunction<short[][][], short[][][]> registration2ByteImage = new RegistrationImageFunction<>(10, 10, 10, new SSEFitnessFunction(), new TransformFunction<>(new BilinearInterpolationFunction(0.0), ImageFactoryFactory.getImageFactory(short[][][].class)));

        // when
        RotationOffset apply = registration2ByteImage.apply(input, input2);

        // then
        Assert.assertEquals(apply.getRotationalOffset(), 0.0);
        Assert.assertEquals(apply.getXOffset(), 0.0);
        Assert.assertEquals(apply.getYOffset(), 0.0);
        Assert.assertEquals(apply.getFailure(), 0.0);
    }

    @Test
    public void testApply2() {
        // given
        ImageWrapper<short[][][]> input = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .andThen(coloredToGreyscale)
                .apply(getClass().getResourceAsStream("/logo/original.JPG"));

        ImageWrapper<short[][][]> input2 = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .andThen(coloredToGreyscale)
                .apply(getClass().getResourceAsStream("/logo/translated2.bmp"));

        RegistrationImageFunction<short[][][], short[][][]> registration2ByteImage = new RegistrationImageFunction<>(10, 10, 10, new SSEFitnessFunction(), new TransformFunction<>(new BilinearInterpolationFunction(0.0), ImageFactoryFactory.getImageFactory(short[][][].class)));

        // when
        RotationOffset apply = registration2ByteImage.apply(input, input2);

        // then
        Assert.assertEquals(apply.getRotationalOffset(), 0.0);
        Assert.assertEquals(apply.getXOffset(), -20.0);
        Assert.assertEquals(apply.getYOffset(), 10.0);
        Assert.assertEquals(apply.getFailure(), 299437860.0);
    }
}