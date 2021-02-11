/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.contour;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.transformation.ThresholdFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;

/**
 * <p>Test class for {@link ContourDetector}</p>
 *
 * @author Christoph Praschl
 */
public class ContourDetectorTest {
    private final Image2ByteInputStreamLoader imageLoader = new Image2ByteInputStreamLoader();

    @Test
    public void testApply() {
        test(true, "/logo/countourdetection1.bmp");
    }

    @Test
    public void testApply2() {
        test(false, "/logo/countourdetection2.bmp");
    }


    private void test(boolean innerBoundary, String compareImage) {
        // given
        ThresholdFunction<short[][][], short[][][]> t = new ThresholdFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
        t.setBackground(0);
        t.setForeground(255);
        t.setLowerThresh(80);

        ImageWrapper<short[][][]> img1 = t.apply(imageLoader.apply(getClass().getResourceAsStream("/logo/logoBinary.bmp")));
        ImageWrapper<short[][][]> compareValue = t.apply(imageLoader.apply(getClass().getResourceAsStream(compareImage)));

        ContourDetector<short[][][]> contourDetector = new ContourDetector<>(ImageFactoryFactory.getImageFactory(short[][][].class));
        contourDetector.setInnerContour(innerBoundary);

        // when
        ImageWrapper<short[][][]> apply = contourDetector.apply(img1);

        // then
        GenericImageCompareFunction compare = new GenericImageCompareFunction();
        Assert.assertTrue(compare.applyAsBoolean(apply, compareValue));
    }
}