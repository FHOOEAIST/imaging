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
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.testng.Assert.*;

/**
 * <p>Test class for {@link GenericImageConsumer}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class GenericImageConsumerTest {

    @Test
    public void testAccept() {
        // given
        BiConsumer<ImageWrapper<double[][][]>, JavaPoint2D> consumer = (i, p) -> {i.setValues(p.getIntX(), p.getIntY(), new double[]{10.0, 10.0, 10.0});};

        GenericImageConsumer<short[][][], double[][][], JavaPoint2D> genericImageConsumer = new GenericImageConsumer<>(consumer, double[][][].class);


        ImageWrapper<short[][][]> randomImage = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);

        // when
        genericImageConsumer.accept(randomImage, new JavaPoint2D(2,2));

        // then
        for (int x = 0; x < randomImage.getWidth(); x++) {
            for (int y = 0; y < randomImage.getHeight(); y++) {
                for (int c = 0; c < randomImage.getChannels(); c++) {
                    if(x == 2 && y == 2){
                        Assert.assertEquals(randomImage.getValue(x,y,c), 10.0);
                    } else {
                        Assert.assertEquals(randomImage.getValue(x,y,c), 0.0);
                    }
                }
            }
        }
    }

}
