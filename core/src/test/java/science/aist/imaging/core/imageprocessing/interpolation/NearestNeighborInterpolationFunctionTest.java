/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.interpolation;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.storage.BufferedImageInputStreamLoader;
import science.aist.imaging.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;
import science.aist.imaging.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;

import java.awt.image.BufferedImage;

/**
 * <p>Test class for {@link NearestNeighborInterpolationFunction}</p>
 *
 * @author Christoph Praschl
 */
public class NearestNeighborInterpolationFunctionTest {

    private BufferedImageInputStreamLoader loader = new BufferedImageInputStreamLoader();
    private GenericImageWrapperTransformer<double[][][], BufferedImage> transformerBIto8Byte = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(double[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));
    private Image2ByteToImage8ByteTransformer transformer8ByteTo2Byte = new Image2ByteToImage8ByteTransformer();
    private ColoredToGreyscaleFunction<short[][][], short[][][]> coloredToGreyscale = new ColoredToGreyscaleFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));

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

        AbstractInterpolationFunction interpolation = new NearestNeighborInterpolationFunction(0.0);

        // when
        Double apply = interpolation.apply(input, new JavaPoint2D(54.3424, 78.4964));

        // then
        Assert.assertEquals(apply, 77.0, 0.0);
    }
}