/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.houghspace;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;

/**
 * @author Andreas Pointner
 */
public class HoughSpaceLinesTest {

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    /**
     * Tests HoughSpaceLines.setHoughSpaceValue function
     */
    @Test
    void testSetGet() {
        // given
        HoughSpaceLines hsl = new HoughSpaceLines(10, 10, 180, 0, 1);

        // when
        hsl.setHoughSpaceValue(1, 1, 12, 10.0);

        // then
        Assert.assertEquals(hsl.getHoughSpaceValue(1, 1, 12), 10.0);
    }

    /**
     * Tests HoughSpaceLines.getBestRidx, HoughSpaceLines.getBestWeight, HoughSpaceLines.getBestX, HoughSpaceLines.getBestY function
     */
    @Test
    void testMax() {
        // given
        HoughSpaceLines hsl = new HoughSpaceLines(10, 10, 180, 0, 1);
        hsl.setHoughSpaceValue(1, 1, 10, 10.0);
        hsl.setHoughSpaceValue(1, 2, 11, 9.0);
        hsl.setHoughSpaceValue(3, 2, 7, 13.0);

        // when
        int bestRotationIdx = hsl.getBestRidx();
        double bestWeight = hsl.getBestWeight();
        int bestX = hsl.getBestX();
        int bestY = hsl.getBestY();

        // then
        Assert.assertEquals(bestRotationIdx, 7);
        Assert.assertEquals(bestWeight, 13.0);
        Assert.assertEquals(bestX, 3);
        Assert.assertEquals(bestY, 2);
    }

    /**
     * Tests HoughSpaceLines.getNumOfRotations function
     */
    @Test
    void testNumRotation() {
        // given
        HoughSpaceLines hsl = new HoughSpaceLines(10, 10, 180, 0, .25);

        // when
        int numRot = hsl.getNumOfRotations();

        // then
        Assert.assertEquals(numRot, (int) (180 / .25 + .5));
    }

    /**
     * Tests HoughSpaceLines.getBestXYradInSector function
     */
    @Test
    void testGetBestXYradInSector() {
        // given
        HoughSpaceLines hsl = new HoughSpaceLines(10, 10, 180, 0, 1);
        hsl.setHoughSpaceValue(1, 1, 10, .5);
        hsl.setHoughSpaceValue(2, 2, 11, 1.);
        hsl.setHoughSpaceValue(6, 1, 12, .5);
        hsl.setHoughSpaceValue(6, 3, 13, 1.);
        hsl.setHoughSpaceValue(3, 7, 14, .5);
        hsl.setHoughSpaceValue(4, 9, 15, 1.);
        hsl.setHoughSpaceValue(7, 7, 16, .5);
        hsl.setHoughSpaceValue(8, 8, 17, 1.);

        // when
        double[] topLeft = hsl.getBestXYradInSector(0, 5, 0, 5);
        double[] topRight = hsl.getBestXYradInSector(5, 10, 0, 5);
        double[] bottomLeft = hsl.getBestXYradInSector(0, 5, 5, 10);
        double[] bottomRight = hsl.getBestXYradInSector(5, 10, 5, 10);

        // then
        Assert.assertEquals(topLeft, new double[]{2, 2, 11});
        Assert.assertEquals(topRight, new double[]{6, 3, 13});
        Assert.assertEquals(bottomLeft, new double[]{4, 9, 15});
        Assert.assertEquals(bottomRight, new double[]{8, 8, 17});
    }

    /**
     * Tests HoughSpaceLines.getBestXYradInSectorForRotation function
     */
    @Test
    void testGetBestXYradInSectorForRotation() {
        // given
        HoughSpaceLines hsl = new HoughSpaceLines(10, 10, 180, 0, 1);
        hsl.setHoughSpaceValue(1, 1, 10, .5);
        hsl.setHoughSpaceValue(2, 2, 11, 1.);
        hsl.setHoughSpaceValue(6, 1, 12, .5);
        hsl.setHoughSpaceValue(6, 3, 13, 1.);
        hsl.setHoughSpaceValue(3, 7, 14, .5);
        hsl.setHoughSpaceValue(4, 9, 15, 1.);
        hsl.setHoughSpaceValue(7, 7, 16, .5);
        hsl.setHoughSpaceValue(8, 8, 17, 1.);

        // when
        double[] topLeft = hsl.getBestXYradInSectorForRotation(0, 5, 0, 5, 10, 10);
        double[] topRight = hsl.getBestXYradInSectorForRotation(5, 10, 0, 5, 12, 12);
        double[] bottomLeft = hsl.getBestXYradInSectorForRotation(0, 5, 5, 10, 14, 14);
        double[] bottomRight = hsl.getBestXYradInSectorForRotation(5, 10, 5, 10, 16, 16);

        // then
        Assert.assertEquals(topLeft, new double[]{1, 1, 10});
        Assert.assertEquals(topRight, new double[]{6, 1, 12});
        Assert.assertEquals(bottomLeft, new double[]{3, 7, 14});
        Assert.assertEquals(bottomRight, new double[]{7, 7, 16});
    }

    /**
     * Tests HoughSpaceLines.getXBestXYradInSectorForRotation function
     */
    @Test
    void testGetXBestXYradInSectorForRotation() {
        // given
        HoughSpaceLines hsl = new HoughSpaceLines(10, 10, 180, 0, 1);
        hsl.setHoughSpaceValue(1, 1, 10, .5);
        hsl.setHoughSpaceValue(2, 2, 11, 1.);
        hsl.setHoughSpaceValue(6, 1, 12, .5);
        hsl.setHoughSpaceValue(6, 3, 13, 1.);
        hsl.setHoughSpaceValue(3, 7, 14, .5);
        hsl.setHoughSpaceValue(4, 9, 15, 1.);
        hsl.setHoughSpaceValue(7, 7, 16, .5);
        hsl.setHoughSpaceValue(8, 8, 17, 1.);

        // when
        double[] topLeft = hsl.getXBestXYradInSector(2, 0, 5, 0, 5);

        // then
        Assert.assertEquals(topLeft, new double[]{1, 1, 10});
    }
}
