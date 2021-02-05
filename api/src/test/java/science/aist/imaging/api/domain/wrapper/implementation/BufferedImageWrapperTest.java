/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>Test class for {@link BufferedImageWrapper}</p>
 *
 * @author Christoph Praschl
 */
public class BufferedImageWrapperTest {

    @Test
    public void testGetValue() throws IOException {
        // given
        BufferedImage read = ImageIO.read(new ClassPathResource("./imageWithMetaData.jpg").getInputStream());
        ImageWrapper<BufferedImage> bufferedImage = BufferedImageFactory.getInstance().getImage(read);

        for (int c = 0; c < 3; c++) {
            // when
            double pixel = bufferedImage.getValue(288, 399, c);

            // then
            Assert.assertEquals(pixel, 255.0);
        }
    }

    @Test
    public void testGetValue2() throws IOException {
        // given
        BufferedImage read = ImageIO.read(new ClassPathResource("./imageWithMetaData.jpg").getInputStream());
        ImageWrapper<BufferedImage> bufferedImage = BufferedImageFactory.getInstance().getImage(read);

        // when
        double b = bufferedImage.getValue(177, 452, 0);
        double g = bufferedImage.getValue(177, 452, 1);
        double r = bufferedImage.getValue(177, 452, 2);

        // then
        Assert.assertEquals(r, 255.0);
        Assert.assertEquals(g, 1.0);
        Assert.assertEquals(b, 1.0);
    }


    @Test
    public void testSetValue() {
        // given
        ImageFactory<BufferedImage> bufferedImageProvider = BufferedImageFactory.getInstance();
        ImageWrapper<BufferedImage> bufferedImage = bufferedImageProvider.getImage(100, 100, ChannelType.GREYSCALE);
        double value = 127;

        // when
        bufferedImage.setValue(50, 50, 0, value);

        // then
        Assert.assertEquals(bufferedImage.getValue(50, 50, 0), value);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetValue2() {
        // given
        ImageWrapper<BufferedImage> bufferedImage = BufferedImageFactory.getInstance().getImage(100, 100, ChannelType.GREYSCALE);
        double value = 127;

        // when
        bufferedImage.setValue(50, 50, 5, value);

        // then -- exception
    }
}