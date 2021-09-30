/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.helper;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Test {@link NormalizeFunction}</p>
 *
 * @author Andreas Pointner
 */
public class NormalizeFunctionTest {

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    void testApply() {
        // given
        NormalizeFunction<short[][][], short[][][]> normalize = new NormalizeFunction<>(ImageFactoryFactory.getImageFactory(short[][][].class));
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(2, 2);
        short[][][] img = image.getImage();
        img[0][0][0] = 300;
        img[0][1][0] = 510;
        img[1][0][0] = 0;
        img[1][1][0] = 30;

        // when
        ImageWrapper<short[][][]> resImg = normalize.apply(image);

        // then
        short[][][] res = resImg.getImage();
        Assert.assertEquals(res[0][0][0], 150);
        Assert.assertEquals(res[0][1][0], 255);
        Assert.assertEquals(res[1][0][0], 0);
        Assert.assertEquals(res[1][1][0], 15);
    }
}
