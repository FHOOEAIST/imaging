/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.segmentation;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.imageprocessing.transformers.GenericImageWrapperTransformer;
import science.aist.imaging.core.imageprocessing.transformers.Image2ByteToImage8ByteTransformer;
import science.aist.imaging.core.storage.BufferedImageInputStreamLoader;
import science.aist.imaging.core.storage.BufferedImageSaver;
import science.aist.imaging.core.imageprocessing.conversion.ColoredToGreyscaleFunction;
import science.aist.imaging.core.imageprocessing.conversion.greyscale.GreyscaleAverageConverter;

import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * <p>Tests {@link ColorPartsSegmentationFunction}</p>
 *
 * @author Andreas Pointner
 */
public class ColorPartsSegmentationFunctionTest {

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
    void testSegmentColorParts() {
        // given
        ImageWrapper<short[][][]> input = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .apply(getClass().getResourceAsStream("/passport/base_p_03_cropped.png"));
        ImageWrapper<short[][][]> comp = loader
                .andThen(bufferedImage -> ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(bufferedImage))
                .andThen(transformerBIto8Byte::transformTo)
                .andThen(transformer8ByteTo2Byte::transformTo)
                .apply(getClass().getResourceAsStream("/passport/base_p_03_cropped_segmented.bmp"));
        ColorPartsSegmentationFunction<short[][][], short[][][]> cps = new ColorPartsSegmentationFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
        cps.setBackground((short) 255);
        cps.setForeground((short) 0);
        cps.setMinDiff((short) 40);

        // when
        ImageWrapper<short[][][]> seg = cps.apply(input);

        new BufferedImageSaver().accept(
                ((Function<ImageWrapper<short[][][]>, ImageWrapper<double[][][]>>) transformer8ByteTo2Byte::transformFrom)
                        .andThen(transformerBIto8Byte::transformFrom)
                        .andThen(ImageWrapper::getImage)
                        .apply(seg), "tmp.png"
        );

        // then
        Assert.assertEquals(seg, comp);
    }
}
