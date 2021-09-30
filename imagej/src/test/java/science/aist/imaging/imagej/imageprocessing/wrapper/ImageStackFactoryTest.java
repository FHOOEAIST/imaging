/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.imagej.imageprocessing.wrapper;

import ij.ImageStack;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Random;


/**
 * <p>Test class for {@link ImageStackFactory}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ImageStackFactoryTest {

    @Test
    public void testGetImage() {
        // given
        ImageFactory<ImageStack> instance = ImageFactoryFactory.getImageFactory(ImageStack.class);

        int width = 10;
        int height = 15;

        Random rand = new Random(768457);
        ImageStack stack = new ImageStack(width, height, 3);
        stack.setProcessor(getChannelImage(rand, width, height), 1);
        stack.setProcessor(getChannelImage(rand, width, height), 2);
        stack.setProcessor(getChannelImage(rand, width, height), 3);

        int[][][] reference = new int[][][]{
                new int[][]{new int[]{164, 28, 212}, new int[]{214, 252, 228}, new int[]{202, 79, 245}, new int[]{223, 220, 57}, new int[]{210, 245, 61}, new int[]{103, 178, 44}, new int[]{52, 158, 213}, new int[]{93, 160, 184}, new int[]{166, 202, 213}, new int[]{87, 97, 152}, new int[]{146, 239, 195}, new int[]{68, 206, 219}, new int[]{71, 220, 86}, new int[]{194, 251, 87}, new int[]{136, 133, 92}},
                new int[][]{new int[]{99, 80, 169}, new int[]{237, 194, 249}, new int[]{243, 213, 111}, new int[]{36, 70, 9}, new int[]{194, 53, 147}, new int[]{185, 254, 58}, new int[]{240, 149, 96}, new int[]{158, 91, 79}, new int[]{142, 232, 69}, new int[]{73, 139, 185}, new int[]{49, 99, 82}, new int[]{130, 42, 202}, new int[]{210, 114, 172}, new int[]{239, 69, 2}, new int[]{245, 123, 160}},
                new int[][]{new int[]{79, 235, 242}, new int[]{198, 54, 54}, new int[]{108, 15, 80}, new int[]{93, 50, 228}, new int[]{251, 66, 218}, new int[]{49, 34, 253}, new int[]{108, 200, 79}, new int[]{220, 64, 52}, new int[]{78, 123, 225}, new int[]{60, 249, 127}, new int[]{157, 124, 224}, new int[]{50, 7, 159}, new int[]{45, 182, 23}, new int[]{134, 17, 47}, new int[]{44, 228, 183}},
                new int[][]{new int[]{154, 3, 224}, new int[]{12, 36, 222}, new int[]{139, 151, 58}, new int[]{226, 182, 153}, new int[]{78, 202, 15}, new int[]{107, 144, 109}, new int[]{250, 245, 13}, new int[]{133, 99, 206}, new int[]{87, 100, 189}, new int[]{135, 63, 172}, new int[]{78, 141, 6}, new int[]{3, 87, 34}, new int[]{197, 27, 57}, new int[]{122, 127, 172}, new int[]{31, 102, 50}},
                new int[][]{new int[]{108, 105, 163}, new int[]{231, 240, 253}, new int[]{14, 91, 86}, new int[]{118, 251, 243}, new int[]{26, 185, 0}, new int[]{123, 119, 163}, new int[]{116, 55, 64}, new int[]{57, 213, 211}, new int[]{62, 80, 12}, new int[]{181, 221, 106}, new int[]{102, 83, 139}, new int[]{167, 126, 192}, new int[]{225, 197, 99}, new int[]{172, 99, 64}, new int[]{127, 107, 225}},
                new int[][]{new int[]{242, 72, 48}, new int[]{54, 252, 194}, new int[]{136, 91, 37}, new int[]{221, 225, 16}, new int[]{184, 213, 221}, new int[]{89, 188, 104}, new int[]{97, 71, 171}, new int[]{50, 95, 73}, new int[]{200, 48, 243}, new int[]{78, 38, 248}, new int[]{44, 23, 124}, new int[]{150, 200, 84}, new int[]{74, 252, 53}, new int[]{97, 212, 69}, new int[]{61, 74, 66}},
                new int[][]{new int[]{81, 18, 183}, new int[]{212, 71, 230}, new int[]{223, 69, 95}, new int[]{20, 179, 174}, new int[]{222, 163, 60}, new int[]{104, 238, 220}, new int[]{161, 98, 144}, new int[]{145, 57, 133}, new int[]{37, 39, 43}, new int[]{200, 140, 60}, new int[]{160, 69, 99}, new int[]{60, 120, 185}, new int[]{53, 227, 96}, new int[]{220, 17, 219}, new int[]{187, 217, 121}},
                new int[][]{new int[]{56, 197, 180}, new int[]{82, 92, 189}, new int[]{219, 131, 67}, new int[]{119, 54, 103}, new int[]{184, 25, 98}, new int[]{57, 251, 27}, new int[]{145, 125, 9}, new int[]{148, 171, 145}, new int[]{220, 215, 5}, new int[]{162, 178, 131}, new int[]{121, 160, 230}, new int[]{223, 233, 43}, new int[]{12, 33, 246}, new int[]{213, 228, 193}, new int[]{238, 233, 64}},
                new int[][]{new int[]{210, 148, 39}, new int[]{111, 116, 131}, new int[]{220, 170, 220}, new int[]{43, 56, 54}, new int[]{179, 153, 59}, new int[]{116, 124, 88}, new int[]{197, 234, 235}, new int[]{153, 240, 8}, new int[]{55, 122, 152}, new int[]{153, 198, 106}, new int[]{243, 130, 248}, new int[]{166, 85, 20}, new int[]{169, 68, 95}, new int[]{62, 17, 61}, new int[]{137, 88, 24}},
                new int[][]{new int[]{188, 0, 241}, new int[]{242, 4, 17}, new int[]{191, 99, 150}, new int[]{171, 221, 69}, new int[]{65, 147, 178}, new int[]{118, 113, 192}, new int[]{73, 245, 180}, new int[]{66, 136, 106}, new int[]{103, 136, 115}, new int[]{30, 248, 191}, new int[]{45, 214, 152}, new int[]{217, 176, 156}, new int[]{191, 205, 94}, new int[]{57, 27, 88}, new int[]{151, 67, 218}},
        };

        // when
        ImageWrapper<ImageStack> image = instance.getImage(height, width, ChannelType.RGB, stack);

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int c = 0; c < image.getChannels(); c++) {
                    int i = (int) image.getValue(x, y, c);
                    Assert.assertEquals(i, reference[x][y][c]);
                }
            }
        }
    }

    private ImageProcessor getChannelImage(Random rand, int width, int height) {
        ImageProcessor ip = new ColorProcessor(width, height);
        int[] channelArr = new int[3];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                channelArr[0] = 0;
                channelArr[1] = 0;
                channelArr[2] = rand.nextInt(255);
                ip.putPixel(x, y, channelArr);
            }
        }
        return ip;
    }

}
