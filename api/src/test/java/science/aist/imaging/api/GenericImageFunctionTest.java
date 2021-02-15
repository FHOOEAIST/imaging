/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */


package science.aist.imaging.api;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Random;
import java.util.function.Function;

/**
 * <p>Test class for {@link GenericImageFunction}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class GenericImageFunctionTest {

    @Test
    public void testApply() {
        // given
        Function<ImageWrapper<double[][][]>, ImageWrapper<double[][][]>> function = (wrapper) -> wrapper.createCopy(ImageFactoryFactory.getImageFactory(double[][][].class));

        GenericImageFunction<short[][][], short[][][], double[][][], double[][][]> genericImageFunction = new GenericImageFunction<>(function, double[][][].class, short[][][].class);

        Random rand = new Random(768457);
        ImageWrapper<short[][][]> randomImage = ImageFactoryFactory.getImageFactory(short[][][].class).getRandomImage(10, 10, ChannelType.RGB, rand, 0, 255, true);

        // when
        ImageWrapper<short[][][]> apply = genericImageFunction.apply(randomImage);

        // then
        for (int x = 0; x < apply.getWidth(); x++) {
            for (int y = 0; y < apply.getHeight(); y++) {
                for (int c = 0; c < apply.getChannels(); c++) {
                    Assert.assertEquals(randomImage.getValue(x,y,c), apply.getValue(x,y,c));
                }
            }
        }
    }

}
