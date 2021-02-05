/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * <p>Test class for {@link ImageFactory}</p>
 *
 * @author Christoph Praschl
 */
public class ImageWrapperProviderTest {

    @Test
    public void testProvide() {
        // given
        ImageFactory<short[][][]> provider = Image2ByteFactory.getInstance();

        double value = 255;

        // when
        ImageWrapper<short[][][]> provide = provider.getImage(10, 10, ChannelType.BGR, value);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(provide.getValue(x, y, c), value);
                }
            }
        }
    }

    @Test
    public void testProvideRandom() {
        // given
        ImageFactory<short[][][]> provider = Image2ByteFactory.getInstance();

        Random rand = new Random(768457);

        // when
        ImageWrapper<short[][][]> provide = provider.getRandomImage(10, 10, ChannelType.BGR, rand, 0, 255, true);

        // then
        for (int y = 0; y < provide.getHeight(); y++) {
            for (int x = 0; x < provide.getWidth(); x++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertTrue(provide.getValue(x, y, c) >= 0.0);
                    Assert.assertTrue(provide.getValue(x, y, c) <= 255.0);
                }
            }
        }
    }

    @Test
    public void testProvideRandom2() {
        // given
        ImageFactory<short[][][]> provider = Image2ByteFactory.getInstance();

        Random rand = new Random(768457);

        double[][][] reference = new double[][][]{
                new double[][]{new double[]{72.0, 172.0, 206.0}, new double[]{164.0, 84.0, 45.0}, new double[]{16.0, 76.0, 241.0}, new double[]{179.0, 167.0, 85.0}, new double[]{69.0, 74.0, 37.0}, new double[]{212.0, 98.0, 91.0}, new double[]{74.0, 14.0, 202.0}, new double[]{123.0, 88.0, 194.0}, new double[]{23.0, 212.0, 168.0}, new double[]{212.0, 48.0, 189.0}},
                new double[][]{new double[]{14.0, 242.0, 239.0}, new double[]{87.0, 223.0, 25.0}, new double[]{134.0, 150.0, 154.0}, new double[]{8.0, 161.0, 57.0}, new double[]{133.0, 213.0, 91.0}, new double[]{120.0, 80.0, 210.0}, new double[]{188.0, 61.0, 89.0}, new double[]{196.0, 11.0, 185.0}, new double[]{31.0, 129.0, 48.0}, new double[]{11.0, 132.0, 99.0}},
                new double[][]{new double[]{58.0, 142.0, 145.0}, new double[]{251.0, 104.0, 238.0}, new double[]{230.0, 195.0, 50.0}, new double[]{244.0, 87.0, 252.0}, new double[]{113.0, 92.0, 32.0}, new double[]{112.0, 50.0, 123.0}, new double[]{140.0, 210.0, 189.0}, new double[]{225.0, 79.0, 208.0}, new double[]{236.0, 76.0, 120.0}, new double[]{223.0, 129.0, 103.0}},
                new double[][]{new double[]{180.0, 211.0, 192.0}, new double[]{252.0, 119.0, 48.0}, new double[]{185.0, 254.0, 183.0}, new double[]{226.0, 104.0, 238.0}, new double[]{68.0, 210.0, 74.0}, new double[]{159.0, 249.0, 75.0}, new double[]{53.0, 84.0, 213.0}, new double[]{184.0, 219.0, 126.0}, new double[]{113.0, 172.0, 6.0}, new double[]{31.0, 130.0, 91.0}},
                new double[][]{new double[]{89.0, 253.0, 197.0}, new double[]{26.0, 146.0, 109.0}, new double[]{49.0, 95.0, 45.0}, new double[]{73.0, 85.0, 246.0}, new double[]{189.0, 59.0, 163.0}, new double[]{240.0, 183.0, 28.0}, new double[]{164.0, 191.0, 7.0}, new double[]{104.0, 72.0, 164.0}, new double[]{131.0, 96.0, 44.0}, new double[]{78.0, 81.0, 102.0}},
                new double[][]{new double[]{15.0, 1.0, 63.0}, new double[]{135.0, 178.0, 61.0}, new double[]{132.0, 8.0, 37.0}, new double[]{10.0, 131.0, 207.0}, new double[]{170.0, 133.0, 225.0}, new double[]{94.0, 165.0, 109.0}, new double[]{229.0, 230.0, 80.0}, new double[]{114.0, 240.0, 137.0}, new double[]{158.0, 49.0, 224.0}, new double[]{150.0, 111.0, 139.0}},
                new double[][]{new double[]{101.0, 242.0, 233.0}, new double[]{248.0, 144.0, 92.0}, new double[]{21.0, 16.0, 120.0}, new double[]{91.0, 204.0, 64.0}, new double[]{88.0, 41.0, 149.0}, new double[]{28.0, 114.0, 160.0}, new double[]{85.0, 50.0, 218.0}, new double[]{173.0, 157.0, 160.0}, new double[]{125.0, 60.0, 228.0}, new double[]{234.0, 235.0, 225.0}},
                new double[][]{new double[]{81.0, 187.0, 237.0}, new double[]{208.0, 99.0, 75.0}, new double[]{94.0, 180.0, 151.0}, new double[]{33.0, 159.0, 66.0}, new double[]{8.0, 187.0, 212.0}, new double[]{13.0, 29.0, 147.0}, new double[]{20.0, 67.0, 189.0}, new double[]{109.0, 247.0, 146.0}, new double[]{172.0, 181.0, 102.0}, new double[]{63.0, 119.0, 14.0}},
                new double[][]{new double[]{185.0, 228.0, 237.0}, new double[]{154.0, 92.0, 22.0}, new double[]{28.0, 245.0, 137.0}, new double[]{40.0, 166.0, 237.0}, new double[]{4.0, 17.0, 188.0}, new double[]{59.0, 156.0, 36.0}, new double[]{250.0, 148.0, 192.0}, new double[]{248.0, 82.0, 58.0}, new double[]{181.0, 83.0, 112.0}, new double[]{31.0, 182.0, 82.0}},
                new double[][]{new double[]{57.0, 130.0, 7.0}, new double[]{146.0, 140.0, 108.0}, new double[]{220.0, 0.0, 9.0}, new double[]{46.0, 31.0, 110.0}, new double[]{95.0, 88.0, 73.0}, new double[]{244.0, 168.0, 29.0}, new double[]{247.0, 26.0, 92.0}, new double[]{89.0, 102.0, 159.0}, new double[]{159.0, 141.0, 158.0}, new double[]{159.0, 65.0, 96.0}}
        };

        // when
        ImageWrapper<short[][][]> provide = provider.getRandomImage(10, 10, ChannelType.BGR, rand, 0, 255, false);

        // then
        for (int y = 0; y < provide.getHeight(); y++) {
            for (int x = 0; x < provide.getWidth(); x++) {
                for (int c = 0; c < provide.getChannels(); c++) {
                    Assert.assertEquals(provide.getValue(x, y, c), reference[y][x][c]);
                }
            }
        }
    }

}
