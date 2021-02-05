/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation;

import science.aist.imaging.api.domain.NeighborType;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.service.core.imageprocessing.transformation.ThresholdFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;

/**
 * <p>Test class for {@link RegionGrowingFunction}</p>
 *
 * @author Andreas Pointner
 */

public class RegionGrowingFunctionTest {

    private final Image2ByteInputStreamLoader loader = new Image2ByteInputStreamLoader();
    private final GenericImageCompareFunction imageCompare = new GenericImageCompareFunction();
    private final RegionGrowingFunction<short[][][], short[][][]> regionGrowing = new RegionGrowingFunction<>(Image2ByteFactory.getInstance());
    private final ThresholdFunction<short[][][], short[][][]> threshold = new ThresholdFunction<>(Image2ByteFactory.getInstance());

    @Test
    public void testApplyN8() {
        // given
        regionGrowing.setLowerThresh(150);
        regionGrowing.setUpperThresh(255);
        regionGrowing.setNeighborType(NeighborType.N8);
        regionGrowing.setSeedPoints(Collections.singletonList(new JavaPoint2D(69, 73)));
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/logoBinary.bmp"));
        threshold.setLowerThresh((short) 250);
        ImageWrapper<short[][][]> compare = threshold.apply(loader.apply(getClass().getResourceAsStream("/logo/logo_n8_region.png")));

        // when
        ImageWrapper<short[][][]> result = regionGrowing.apply(input);


        // then
        Assert.assertTrue(imageCompare.applyAsBoolean(compare, result));
    }

    @Test
    public void testApplyN4() {
        // given
        regionGrowing.setLowerThresh(150);
        regionGrowing.setUpperThresh(255);
        regionGrowing.setNeighborType(NeighborType.N4);
        regionGrowing.setSeedPoints(Collections.singletonList(new JavaPoint2D(69, 73)));
        ImageWrapper<short[][][]> input = loader.apply(getClass().getResourceAsStream("/logo/logoBinary.bmp"));
        threshold.setLowerThresh((short) 250);
        ImageWrapper<short[][][]> compare = threshold.apply(loader.apply(getClass().getResourceAsStream("/logo/logo_n4_region.png")));


        // when
        ImageWrapper<short[][][]> result = regionGrowing.apply(input);

        // then
        Assert.assertTrue(imageCompare.applyAsBoolean(compare, result));
    }
}