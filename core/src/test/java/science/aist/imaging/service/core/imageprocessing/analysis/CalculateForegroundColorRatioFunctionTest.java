/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.analysis;

import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.service.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;
import science.aist.imaging.service.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.service.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;
import science.aist.imaging.api.domain.wrapper.implementation.BufferedImageFactory;
import science.aist.imaging.service.core.storage.BufferedImageInputStreamLoader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;

/**
 * <p>Tests {@link CalculateForegroundColorRatioFunction}</p>
 *
 * @author Andreas Pointner
 */
public class CalculateForegroundColorRatioFunctionTest {

    private final GenericImageWrapperTransformer<double[][][], BufferedImage> transformerBIto8Byte = new GenericImageWrapperTransformer<>(TypeBasedImageFactoryFactory.getImageFactory(double[][][].class), TypeBasedImageFactoryFactory.getImageFactory(BufferedImage.class));
    private BufferedImageInputStreamLoader loader = new BufferedImageInputStreamLoader();
    private Image2ByteToImage8ByteTransformer transformer8ByteTo2Byte = new Image2ByteToImage8ByteTransformer();
    private ColoredToGreyscaleFunction<short[][][], short[][][]> coloredToGreyscale = new ColoredToGreyscaleFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @BeforeTest
    void setUp() {
        coloredToGreyscale.setColorToGreyScale(new GreyscaleAverageConverter());
    }

    @Test
    void testCalculateForegroundColorRatio() {
        // given
        ImageWrapper<short[][][]> template = loader
                .andThen(bufferedImage -> TypeBasedImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .apply(getClass().getResourceAsStream("/logo/logoBinary.bmp"));
        template = coloredToGreyscale.apply(template);
        CalculateForegroundColorRatioFunction<short[][][]> calculateForegroundColorRatio = new CalculateForegroundColorRatioFunction<>();
        calculateForegroundColorRatio.setForegroundColor((short) 255);

        // when
        double ration = calculateForegroundColorRatio.applyAsDouble(template);

        // then
        Assert.assertEquals(ration, 0.059, 0.01);
    }
}
