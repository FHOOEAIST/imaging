/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.jack.math.MathUtils;

/**
 * <p>Test class for {@link SubImageWrapper}</p>
 *
 * @author Christoph Praschl
 */
public class SubImageWrapperTest {
    private ImageWrapper<short[][][]> getSampleImage() {
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(255, 255);
        for (int x = 0; x < 255; x++) {
            for (int y = 0; y < 255; y++) {
                image.setValue(x, y, 0, x);
            }
        }

        return image;
    }


    @Test
    public void testGetValues() {
        // given
        ImageWrapper<short[][][]> sample = getSampleImage();
        SubImageWrapper<short[][][]> subImage = new SubImageWrapper<>(sample, 50, 50, 100, 100);

        // when/then
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                Assert.assertTrue(MathUtils.equals(sample.getValue(x + 50, y + 50, 0), subImage.getValue(x, y, 0)));
            }
        }
    }

    @Test
    public void testSetValues() {
        // given
        ImageWrapper<short[][][]> sample = getSampleImage();
        SubImageWrapper<short[][][]> subImage = new SubImageWrapper<>(sample, 50, 50, 100, 100);

        // when
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                double value = subImage.getValue(x, y, 0);
                subImage.setValue(x, y, 0, value + 1);
            }
        }

        // then
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                Assert.assertTrue(MathUtils.equals(sample.getValue(x + 50, y + 50, 0), subImage.getValue(x, y, 0)));
            }
        }
    }

    @Test
    public void testApplyFunction() {
        // given
        ImageWrapper<short[][][]> sample = getSampleImage();
        SubImageWrapper<short[][][]> subImage = new SubImageWrapper<>(sample, 50, 50, 100, 100);

        // when
        subImage.applyFunction((image, x, y, c) -> {
            double value = image.getValue(x, y, 0);
            image.setValue(x, y, 0, value + 1);
        });

        // then
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                Assert.assertTrue(MathUtils.equals(sample.getValue(x + 50, y + 50, 0), subImage.getValue(x, y, 0)));
            }
        }
    }


}
