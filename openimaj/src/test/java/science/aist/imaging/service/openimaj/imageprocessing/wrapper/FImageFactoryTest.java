/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.openimaj.imageprocessing.wrapper;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.io.IOException;
import java.util.Random;

/**
 * <p>Test class for {@link FImageFactory}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class FImageFactoryTest {

    @Test
    public void testGetImage() {
        // given
        ImageFactory<FImage> imageProcessorFactory = ImageFactoryFactory.getImageFactory(FImage.class);
        int width = 10;
        int height = 15;
        FImage img = new FImage(width, height);

        Random rand = new Random(768457);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setPixel(x, y, rand.nextFloat());
            }
        }

        int[][] reference = new int[][]{
                new int[]{72, 67, 172, 12, 206, 189, 164, 231, 84, 228, 45, 70, 16, 9, 76},
                new int[]{45, 241, 185, 179, 193, 167, 61, 85, 117, 69, 45, 74, 75, 37, 169},
                new int[]{212, 164, 98, 153, 91, 2, 74, 36, 14, 134, 202, 209, 123, 47, 88},
                new int[]{207, 194, 93, 23, 170, 212, 13, 168, 72, 212, 223, 48, 251, 189, 59},
                new int[]{14, 144, 242, 63, 239, 44, 87, 251, 223, 184, 25, 101, 134, 82, 150},
                new int[]{127, 154, 105, 8, 48, 161, 145, 57, 145, 133, 52, 213, 144, 91, 105},
                new int[]{120, 190, 80, 201, 210, 146, 188, 110, 61, 117, 89, 234, 196, 21, 11},
                new int[]{211, 185, 54, 31, 99, 129, 159, 48, 39, 11, 192, 132, 254, 99, 19},
                new int[]{58, 147, 142, 63, 145, 231, 251, 41, 104, 10, 238, 13, 230, 191, 195},
                new int[]{160, 50, 170, 244, 31, 87, 44, 252, 167, 113, 238, 92, 106, 32, 132},
        };

        // when
        ImageWrapper<FImage> image = imageProcessorFactory.getImage(img);

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double value = image.getValue(x, y, 0);
                Assert.assertEquals((int) value, reference[x][y]);
            }
        }
    }

    @Test
    public void testGetImage2() throws IOException {
        // given
        ImageFactory<FImage> imageProcessorFactory = ImageFactoryFactory.getImageFactory(FImage.class);
        FImage fImage = ImageUtilities.readF(FImageFactoryTest.class.getResourceAsStream("/original.tif"));

        // when
        ImageWrapper<FImage> image = imageProcessorFactory.getImage(fImage);

        // then
        Assert.assertEquals(image.getWidth(), 196);
        Assert.assertEquals(image.getHeight(), 145);
        Assert.assertEquals(image.getChannelType(), ChannelType.GREYSCALE);
        Assert.assertEquals(image.getChannels(), 1);
    }

}
