/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.SubImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * <p>Test class for {@link ContentAwareCrop}</p>
 *
 * @author Christoph Praschl
 */
public class ContentAwareCropTest {

    @Test
    public void testApply() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);
        double[] c1 = new double[]{255, 0, 0};
        double[] c2 = new double[]{255, 255, 0};
        double[] c3 = new double[]{255, 0, 255};
        double[] c4 = new double[]{255, 255, 255};

        image.setValues(4, 4, c1);
        image.setValues(5, 4, c2);
        image.setValues(4, 5, c3);
        image.setValues(5, 5, c4);

        ContentAwareCrop<short[][][]> contentAwareCrop = new ContentAwareCrop<>();

        // when
        SubImageWrapper<short[][][]> apply = contentAwareCrop.apply(image);

        // then
        assertEquals(apply.getWidth(), 2);
        assertEquals(apply.getHeight(), 2);
        assertTrue(Arrays.equals(apply.getValues(0, 0), c1));
        assertTrue(Arrays.equals(apply.getValues(1, 0), c2));
        assertTrue(Arrays.equals(apply.getValues(0, 1), c3));
        assertTrue(Arrays.equals(apply.getValues(1, 1), c4));
    }

    @Test
    public void testApply2() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);
        double[] c1 = new double[]{255, 0, 0};
        double[] c2 = new double[]{255, 255, 0};
        double[] c3 = new double[]{255, 0, 255};
        double[] c4 = new double[]{255, 255, 255};

        image.setValues(4, 4, c1);
        image.setValues(5, 4, c2);
        image.setValues(4, 5, c3);
        image.setValues(5, 5, c4);

        ContentAwareCrop<short[][][]> contentAwareCrop = new ContentAwareCrop<>();

        // when
        contentAwareCrop.setNoCropLeft(true);
        SubImageWrapper<short[][][]> apply = contentAwareCrop.apply(image);

        // then
        assertEquals(apply.getWidth(), 6);
        assertEquals(apply.getHeight(), 2);
    }

    @Test
    public void testApply3() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);
        double[] c1 = new double[]{255, 0, 0};
        double[] c2 = new double[]{255, 255, 0};
        double[] c3 = new double[]{255, 0, 255};
        double[] c4 = new double[]{255, 255, 255};

        image.setValues(4, 4, c1);
        image.setValues(5, 4, c2);
        image.setValues(4, 5, c3);
        image.setValues(5, 5, c4);

        ContentAwareCrop<short[][][]> contentAwareCrop = new ContentAwareCrop<>();

        // when
        contentAwareCrop.setNoCropBottom(true);
        SubImageWrapper<short[][][]> apply = contentAwareCrop.apply(image);

        // then
        assertEquals(apply.getWidth(), 2);
        assertEquals(apply.getHeight(), 6);
    }

    @Test
    public void testApply4() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(10, 10, ChannelType.RGB);
        double[] c1 = new double[]{255, 0, 0};
        double[] c2 = new double[]{255, 255, 0};
        double[] c3 = new double[]{255, 0, 255};
        double[] c4 = new double[]{255, 255, 255};

        image.setValues(4, 4, c1);
        image.setValues(5, 4, c2);
        image.setValues(4, 5, c3);
        image.setValues(5, 5, c4);

        ContentAwareCrop<short[][][]> contentAwareCrop = new ContentAwareCrop<>();

        // when
        contentAwareCrop.setMarginUnify(1);
        SubImageWrapper<short[][][]> apply = contentAwareCrop.apply(image);

        // then
        assertEquals(apply.getWidth(), 4);
        assertEquals(apply.getHeight(), 4);
    }

}
