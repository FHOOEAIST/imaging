/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.interpolation;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.service.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;
import science.aist.imaging.service.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.service.core.storage.BufferedImageInputStreamLoader;

import java.awt.image.BufferedImage;

/**
 * <p>Test class for {@link BilinearInterpolationFunction}</p>
 *
 * @author Christoph Praschl
 */
public class BilinearInterpolationFunctionTest {
    private BufferedImageInputStreamLoader loader = new BufferedImageInputStreamLoader();
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
        ImageWrapper<BufferedImage> apply1 = ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(loader.apply(getClass().getResourceAsStream("/logo/original.JPG")));
        GenericImageWrapperTransformer<BufferedImage, short[][][]> genericImageWrapperTransformer = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(BufferedImage.class), ImageFactoryFactory.getImageFactory(short[][][].class));

        ImageWrapper<short[][][]> input = coloredToGreyscale.apply(genericImageWrapperTransformer.transformFrom(apply1));

        AbstractInterpolationFunction interpolation = new BilinearInterpolationFunction(0.0);

        // when
        Double apply = interpolation.apply(input, new JavaPoint2D(54.3424, 78.4964));

        // then
        Assert.assertEquals(apply, 67.738, 0.01);
    }
}