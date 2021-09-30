/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.transformation.OpenCVResizeFunction;
import org.opencv.core.Mat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVPositionalOffsetFunctionTest extends OpenCVTest {
    /**
     * OpenCVPositionalOffsetFunction.apply
     * test it with translation just on x-axis
     */
    @Test
    void testCalculateOffset() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> xTranslatedImg = loadImageFromClassPath("/logo/xTranslated.tif");

        OpenCVPositionalOffsetFunction positionalOffsetFunction = new OpenCVPositionalOffsetFunction();
        positionalOffsetFunction.setOptimizer(optimizer);

        // when
        TranslationOffset o = positionalOffsetFunction.apply(img1, xTranslatedImg);

        // then
        Assert.assertNotNull(o);
        double res = Math.abs(o.getXOffset() - 10.0);
        Assert.assertTrue(res > 0 && res < 0.05);
    }

    /**
     * OpenCVPositionalOffsetFunction.apply
     * test it with translation on both axis
     */
    @Test
    void testCalculateOffset2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        ImageWrapper<Mat> translatedImg = loadImageFromClassPath("/logo/translated3.tif");

        OpenCVPositionalOffsetFunction positionalOffsetFunction = new OpenCVPositionalOffsetFunction();
        positionalOffsetFunction.setOptimizer(optimizer);

        // when
        TranslationOffset o = positionalOffsetFunction.apply(img1, translatedImg);

        // then
        Assert.assertNotNull(o);
        double xRes = Math.abs(o.getXOffset() - 20.0);
        Assert.assertTrue(xRes > 0 && xRes < 0.05);
        double yRes = Math.abs(o.getYOffset() + 10);
        Assert.assertTrue(yRes > 0 && yRes < 0.05);
    }

    /**
     * OpenCVPositionalOffsetFunction.apply
     * test with same image --> offset 0
     */
    @Test
    void testCalculateOffset3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");

        OpenCVPositionalOffsetFunction positionalOffsetFunction = new OpenCVPositionalOffsetFunction();
        positionalOffsetFunction.setOptimizer(optimizer);

        // when
        TranslationOffset o = positionalOffsetFunction.apply(img1, img1);

        // then
        Assert.assertNotNull(o);
        Assert.assertTrue(o.getXOffset() < 0.1);
        Assert.assertTrue(o.getYOffset() < 0.1);
    }

    /**
     * OpenCVPositionalOffsetFunction.apply
     * test with real-world image examples
     */
    @Test
    void testCalculateOffset4() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/hololens/originalTranslationTest.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/hololens/translatedTranslationTest.jpg");
        OpenCVResizeFunction resizeFunction = new OpenCVResizeFunction();

        resizeFunction.setWidthAndRelativeHeight(200, img1.getWidth(), img1.getHeight());

        img1 = resizeFunction.apply(img1);
        img2 = resizeFunction.apply(img2);

        OpenCVPositionalOffsetFunction positionalOffsetFunction = new OpenCVPositionalOffsetFunction();
        positionalOffsetFunction.setOptimizer(optimizer);

        // when
        TranslationOffset o = positionalOffsetFunction.apply(img1, img2);

        // then
        log.debug(o + "");
        Assert.assertNotNull(o);
        double absXOffset = Math.abs(o.getXOffset());
        Assert.assertTrue(absXOffset < 24.1);
        double absYOffset = Math.abs(o.getYOffset());
        Assert.assertTrue(absYOffset < 0.1);
    }

}
