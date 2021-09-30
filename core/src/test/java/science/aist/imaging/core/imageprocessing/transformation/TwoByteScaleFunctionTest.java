/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformation;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.storage.Image2ByteInputStreamLoader;

import java.io.InputStream;
import java.util.function.Function;

/**
 * <p>Test class for {@link TwoByteScaleFunction}</p>
 *
 * @author Andreas Pointner
 */
public class TwoByteScaleFunctionTest {
    private final Function<InputStream, ImageWrapper<short[][][]>> imageLoader = new Image2ByteInputStreamLoader();
    private final TwoByteScaleFunction<short[][][], short[][][]> twoByteScaleFunction = new TwoByteScaleFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();

    /**
     * scale up
     */
    @Test
    public void testApply1() {
        // given
        ImageWrapper<short[][][]> input = imageLoader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = imageLoader.apply(getClass().getResourceAsStream("/logo/scaled1.bmp"));

        // when
        twoByteScaleFunction.setNewWidth(294);
        twoByteScaleFunction.setNewHeight(217);
        ImageWrapper<short[][][]> result = twoByteScaleFunction.apply(input);

        // then
        // Do not ask me why, every image is a bmp image, nevertheless they are then not totally equal...
        Assert.assertTrue(imageCompare.setEpsilon(2.0).applyAsBoolean(result, compare));
    }

    /**
     * scale distorted
     */
    @Test
    public void testApply2() {
        // given
        ImageWrapper<short[][][]> input = imageLoader.apply(getClass().getResourceAsStream("/logo/original.JPG"));
        ImageWrapper<short[][][]> compare = imageLoader.apply(getClass().getResourceAsStream("/logo/scaled2.bmp"));

        // when
        twoByteScaleFunction.setNewWidth(200);
        twoByteScaleFunction.setNewHeight(300);
        ImageWrapper<short[][][]> result = twoByteScaleFunction.andThenCloseInput().apply(input);

        // then
        Assert.assertTrue(imageCompare.setEpsilon(2.0).applyAsBoolean(result, compare));
    }

    /**
     * scale down
     */
    @Test
    public void testApply3() {
        // given
        ImageWrapper<short[][][]> input = imageLoader.apply(getClass().getResourceAsStream("/logo/original.bmp"));
        ImageWrapper<short[][][]> compare = imageLoader.apply(getClass().getResourceAsStream("/logo/scaled3.bmp"));

        // when
        twoByteScaleFunction.setNewWidth(130);
        twoByteScaleFunction.setNewHeight(96);
        ImageWrapper<short[][][]> result = twoByteScaleFunction.apply(input);

        // then
        // Do not ask me why, every image is a bmp image, nevertheless they are then not totally equal...
        Assert.assertTrue(imageCompare.setEpsilon(2.0).applyAsBoolean(result, compare));
    }
}
