/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformers;

import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.storage.Image2ByteInputStreamLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>Test class for {@link GenericImageWrapperTransformer}</p>
 *
 * @author Christoph Praschl
 */
public class GenericImageWrapperTransformerTest {

    @Test
    public void testTransformTo() {
        // given
        double[][][] data = {
                {
                        {1, 5, 10}
                },
                {
                        {5, 10, 5}
                },
                {
                        {10, 5, 15}
                },
        };

        ImageWrapper<double[][][]> multiChannel8ByteImage = ImageFactoryFactory.getImageFactory(double[][][].class).getImage(3, 1, ChannelType.BGR, data);
        GenericImageWrapperTransformer<short[][][], double[][][]> genericImageWrapperTransformer = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(double[][][].class));

        // when
        ImageWrapper<short[][][]> imageWrapper = genericImageWrapperTransformer.transformTo(multiChannel8ByteImage);

        // then
        GenericImageCompareFunction compareFunction = new GenericImageCompareFunction();
        Assert.assertTrue(compareFunction.applyAsBoolean(imageWrapper, multiChannel8ByteImage));
    }

    @Test
    public void testTransformFrom() {
        // given
        short[][][] data = {
                {
                        {1, 5, 10}
                },
                {
                        {5, 10, 5}
                },
                {
                        {10, 5, 15}
                },
        };

        ImageWrapper<short[][][]> multiChannel8ByteImage = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(3, 1, ChannelType.BGR, data);
        GenericImageWrapperTransformer<short[][][], double[][][]> genericImageWrapperTransformer = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(double[][][].class));

        // when
        ImageWrapper<double[][][]> imageWrapper = genericImageWrapperTransformer.transformFrom(multiChannel8ByteImage);

        // then
        GenericImageCompareFunction compareFunction = new GenericImageCompareFunction();
        Assert.assertTrue(compareFunction.applyAsBoolean(imageWrapper, multiChannel8ByteImage));
    }


    @Test
    public void testTransformTo2() throws IOException {
        // given
        ClassPathResource resource = new ClassPathResource("./imageWithMetaData.jpg");
        ImageWrapper<BufferedImage> bufferedImage = ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(ImageIO.read(resource.getInputStream()));
        Image2ByteInputStreamLoader inputStreamLoader = new Image2ByteInputStreamLoader();
        inputStreamLoader.setGreyscale(false);
        ImageWrapper<short[][][]> compare = inputStreamLoader.apply(resource.getInputStream());

        GenericImageWrapperTransformer<short[][][], BufferedImage> transformer = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));

        // when
        ImageWrapper<short[][][]> imageWrapper = transformer.transformTo(bufferedImage);

        // then
        GenericImageCompareFunction compareFunction = new GenericImageCompareFunction();
        Assert.assertTrue(compareFunction.applyAsBoolean(imageWrapper, compare));
    }

    @Test
    public void testTransformFrom2() throws IOException {
        // given
        ClassPathResource resource = new ClassPathResource("./imageWithMetaData.jpg");
        ImageWrapper<BufferedImage> compare = ImageFactoryFactory.getImageFactory(BufferedImage.class).getImage(ImageIO.read(resource.getInputStream()));
        Image2ByteInputStreamLoader inputStreamLoader = new Image2ByteInputStreamLoader();
        inputStreamLoader.setGreyscale(false);
        ImageWrapper<short[][][]> input = inputStreamLoader.apply(resource.getInputStream());
        GenericImageWrapperTransformer<short[][][], BufferedImage> transformer = new GenericImageWrapperTransformer<>(ImageFactoryFactory.getImageFactory(short[][][].class), ImageFactoryFactory.getImageFactory(BufferedImage.class));

        // when
        ImageWrapper<BufferedImage> bufferedImageImageWrapper = transformer.transformFrom(input);

        // then
        GenericImageCompareFunction compareFunction = new GenericImageCompareFunction();
        Assert.assertTrue(compareFunction.applyAsBoolean(bufferedImageImageWrapper, compare));
    }
}