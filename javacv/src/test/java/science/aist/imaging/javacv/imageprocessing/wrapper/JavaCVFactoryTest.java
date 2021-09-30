/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.javacv.imageprocessing.wrapper;

import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Random;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC3;

/**
 * <p>Test class for {@link JavaCVFactory}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class JavaCVFactoryTest {

    @Test
    public void testGetImage() {
        // given
        ImageFactory<Mat> imageProcessorFactory = ImageFactoryFactory.getImageFactory(Mat.class);
        int width = 10;
        int height = 15;
        Mat img = new Mat(height, width, CV_8UC3);
        Random rand = new Random(768457);

        try (UByteRawIndexer indexer = img.createIndexer()) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int c = 0; c < 3; c++) {
                        indexer.put(y, x, c, rand.nextInt(255));
                    }
                }
            }
        }


        int[][][] reference = new int[][][]{
                new int[][]{new int[]{164,214,202},new int[]{223,210,103},new int[]{52,93,166},new int[]{87,146,68},new int[]{71,194,136},new int[]{99,237,243},new int[]{36,194,185},new int[]{240,158,142},new int[]{73,49,130},new int[]{210,239,245},new int[]{79,198,108},new int[]{93,251,49},new int[]{108,220,78},new int[]{60,157,50},new int[]{45,134,44}},
                new int[][]{new int[]{154,12,139},new int[]{226,78,107},new int[]{250,133,87},new int[]{135,78,3},new int[]{197,122,31},new int[]{108,231,14},new int[]{118,26,123},new int[]{116,57,62},new int[]{181,102,167},new int[]{225,172,127},new int[]{242,54,136},new int[]{221,184,89},new int[]{97,50,200},new int[]{78,44,150},new int[]{74,97,61}},
                new int[][]{new int[]{81,212,223},new int[]{20,222,104},new int[]{161,145,37},new int[]{200,160,60},new int[]{53,220,187},new int[]{56,82,219},new int[]{119,184,57},new int[]{145,148,220},new int[]{162,121,223},new int[]{12,213,238},new int[]{210,111,220},new int[]{43,179,116},new int[]{197,153,55},new int[]{153,243,166},new int[]{169,62,137}},
                new int[][]{new int[]{188,242,191},new int[]{171,65,118},new int[]{73,66,103},new int[]{30,45,217},new int[]{191,57,151},new int[]{28,252,79},new int[]{220,245,178},new int[]{158,160,202},new int[]{97,239,206},new int[]{220,251,133},new int[]{80,194,213},new int[]{70,53,254},new int[]{149,91,232},new int[]{139,99,42},new int[]{114,69,123}},
                new int[][]{new int[]{235,54,15},new int[]{50,66,34},new int[]{200,64,123},new int[]{249,124,7},new int[]{182,17,228},new int[]{3,36,151},new int[]{182,202,144},new int[]{245,99,100},new int[]{63,141,87},new int[]{27,127,102},new int[]{105,240,91},new int[]{251,185,119},new int[]{55,213,80},new int[]{221,83,126},new int[]{197,99,107}},
                new int[][]{new int[]{72,252,91},new int[]{225,213,188},new int[]{71,95,48},new int[]{38,23,200},new int[]{252,212,74},new int[]{18,71,69},new int[]{179,163,238},new int[]{98,57,39},new int[]{140,69,120},new int[]{227,17,217},new int[]{197,92,131},new int[]{54,25,251},new int[]{125,171,215},new int[]{178,160,233},new int[]{33,228,233}},
                new int[][]{new int[]{148,116,170},new int[]{56,153,124},new int[]{234,240,122},new int[]{198,130,85},new int[]{68,17,88},new int[]{0,4,99},new int[]{221,147,113},new int[]{245,136,136},new int[]{248,214,176},new int[]{205,27,67},new int[]{212,228,245},new int[]{57,61,44},new int[]{213,184,213},new int[]{152,195,219},new int[]{86,87,92}},
                new int[][]{new int[]{169,249,111},new int[]{9,147,58},new int[]{96,79,69},new int[]{185,82,202},new int[]{172,2,160},new int[]{242,54,80},new int[]{228,218,253},new int[]{79,52,225},new int[]{127,224,159},new int[]{23,47,183},new int[]{224,222,58},new int[]{153,15,109},new int[]{13,206,189},new int[]{172,6,34},new int[]{57,172,50}},
                new int[][]{new int[]{163,253,86},new int[]{243,0,163},new int[]{64,211,12},new int[]{106,139,192},new int[]{99,64,225},new int[]{48,194,37},new int[]{16,221,104},new int[]{171,73,243},new int[]{248,124,84},new int[]{53,69,66},new int[]{183,230,95},new int[]{174,60,220},new int[]{144,133,43},new int[]{60,99,185},new int[]{96,219,121}},
                new int[][]{new int[]{180,189,67},new int[]{103,98,27},new int[]{9,145,5},new int[]{131,230,43},new int[]{246,193,64},new int[]{39,131,220},new int[]{54,59,88},new int[]{235,8,152},new int[]{106,248,20},new int[]{95,61,24},new int[]{241,17,150},new int[]{69,178,192},new int[]{180,106,115},new int[]{191,152,156},new int[]{94,88,218}},
        };


        // when
        ImageWrapper<Mat> image = imageProcessorFactory.getImage(img);

        // then
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int c = 0; c < 3; c++) {
                    int val = (int) image.getValue(x, y, c);
                    Assert.assertEquals(val, reference[x][y][c]);
                }
            }
        }
    }
}
