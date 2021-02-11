/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.creator;

import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;

import java.util.Arrays;
import java.util.Collection;

/**
 * <p>This class tests {@link ImageFromJavaLinesCreatorFunction}</p>
 *
 * @author Andreas Pointner
 */
public class ImageFromJavaLinesCreatorFunctionTest {

    @AfterMethod
    void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    @Test
    void testCreateLineGreyscaleNoColorCreator() {
        // given
        Collection<JavaLine2D> cl = Arrays.asList(new JavaLine2D(0, 0, 2, 0), new JavaLine2D(1, 1, 1, 3));
        ImageFromJavaLinesCreatorFunction<short[][][]> jifjlc = new ImageFromJavaLinesCreatorFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));
        jifjlc.setColored(false);
        jifjlc.setPaddingX(0);
        jifjlc.setPaddingY(0);

        // when
        ImageWrapper<short[][][]> ji = jifjlc.apply(cl);

        // then
        Assert.assertEquals(ji.getWidth(), 3);
        Assert.assertEquals(ji.getHeight(), 4);

        Assert.assertEquals(ji.getImage()[0][1][0], 255);
        Assert.assertEquals(ji.getImage()[0][0][0], 255);
        Assert.assertEquals(ji.getImage()[0][2][0], 255);
        Assert.assertEquals(ji.getImage()[1][1][0], 255);
        Assert.assertEquals(ji.getImage()[2][1][0], 255);
        Assert.assertEquals(ji.getImage()[3][1][0], 255);
    }

    @Test
    void testCreateLineColoredNoColorCreator() {
        // given
        Collection<JavaLine2D> cl = Arrays.asList(new JavaLine2D(0, 0, 2, 0), new JavaLine2D(1, 1, 1, 3));
        ImageFromJavaLinesCreatorFunction<short[][][]> jifjlc = new ImageFromJavaLinesCreatorFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));
        jifjlc.setColored(true);
        jifjlc.setPaddingX(0);
        jifjlc.setPaddingY(0);

        // when
        ImageWrapper<short[][][]> ji = jifjlc.apply(cl);

        // then
        Assert.assertEquals(ji.getWidth(), 3);
        Assert.assertEquals(ji.getHeight(), 4);
        Assert.assertTrue(isEqual(ji.getImage()[0][1], RGBColor.BLACK.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[0][0], RGBColor.BLACK.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[0][2], RGBColor.BLACK.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[1][1], RGBColor.BLACK.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[2][1], RGBColor.BLACK.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[3][1], RGBColor.BLACK.getChannelsShort()));
    }

    @Test
    void testCreateLineColoredWithColorCreator() {
        // given
        JavaLine2D jl1 = new JavaLine2D(0, 0, 2, 0);
        JavaLine2D jl2 = new JavaLine2D(1, 1, 1, 3);
        Collection<JavaLine2D> cl = Arrays.asList(jl1, jl2);
        ImageFromJavaLinesCreatorFunction<short[][][]> jifjlc = new ImageFromJavaLinesCreatorFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));
        jifjlc.setColored(true);
        jifjlc.setPaddingX(0);
        jifjlc.setPaddingY(0);
        jifjlc.setImageCallback(jl -> jl == jl1 ? new Color(0, 0, 255) : new Color(0, 255, 0));

        // when
        ImageWrapper<short[][][]> ji = jifjlc.apply(cl);

        // then
        Assert.assertEquals(ji.getWidth(), 3);
        Assert.assertEquals(ji.getHeight(), 4);
        Assert.assertTrue(isEqual(ji.getImage()[0][0], RGBColor.BLUE.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[0][1], RGBColor.BLUE.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[0][2], RGBColor.BLUE.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[1][1], RGBColor.GREEN.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[2][1], RGBColor.GREEN.getChannelsShort()));
        Assert.assertTrue(isEqual(ji.getImage()[3][1], RGBColor.GREEN.getChannelsShort()));
    }

    private boolean isEqual(short[] first, short[] second) {
        if (first.length != second.length) return false;

        for (int i = 0; i < first.length; i++) {
            if (first[i] != second[i]) return false;
        }

        return true;
    }

}
