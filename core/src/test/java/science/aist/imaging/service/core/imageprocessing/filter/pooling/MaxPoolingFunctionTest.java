/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter.pooling;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Random;


/**
 * <p>Test class for {@link MaxPoolingFunction}</p>
 *
 * @author Christoph Praschl
 */
public class MaxPoolingFunctionTest {

    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        ImageWrapper<short[][][]> provide = provider.getImage(15, 15, ChannelType.GREYSCALE);
        Random random = new Random(768457);
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                provide.setValue(x, y, 0, random.nextInt(255));
            }
        }

        MaxPoolingFunction<short[][][], short[][][]> maxPooling = new MaxPoolingFunction<>(provider);

        double[][] reference = new double[][]{
                new double[]{237, 243, 243, 243, 223, 240, 240, 240, 166, 166, 146, 210, 239, 245, 245},
                new double[]{237, 243, 243, 251, 251, 251, 240, 240, 220, 166, 157, 210, 239, 245, 245},
                new double[]{237, 243, 243, 251, 251, 251, 250, 250, 220, 157, 157, 210, 239, 245, 245},
                new double[]{231, 231, 231, 251, 251, 251, 250, 250, 220, 181, 181, 225, 225, 225, 172},
                new double[]{242, 242, 231, 226, 226, 250, 250, 250, 200, 200, 181, 225, 225, 225, 172},
                new double[]{242, 242, 231, 223, 222, 222, 161, 200, 200, 200, 200, 225, 225, 225, 220},
                new double[]{242, 242, 223, 223, 222, 222, 161, 220, 220, 220, 223, 223, 223, 238, 238},
                new double[]{212, 223, 223, 223, 222, 222, 197, 220, 220, 243, 243, 243, 223, 238, 238},
                new double[]{242, 242, 242, 220, 184, 197, 197, 220, 220, 243, 243, 243, 223, 238, 238},
                new double[]{252, 252, 252, 245, 245, 245, 197, 202, 202, 243, 243, 243, 251, 251, 251},
                new double[]{252, 252, 252, 245, 254, 254, 254, 232, 232, 239, 239, 239, 251, 251, 251},
                new double[]{252, 252, 252, 245, 254, 254, 254, 232, 249, 249, 249, 239, 251, 251, 251},
                new double[]{235, 235, 213, 213, 254, 254, 254, 245, 249, 249, 249, 182, 182, 228, 228},
                new double[]{240, 240, 251, 251, 251, 245, 245, 245, 249, 249, 249, 197, 197, 228, 228},
                new double[]{240, 240, 251, 251, 251, 245, 245, 245, 221, 221, 221, 197, 197, 197, 127}
        };

        // when
        ImageWrapper<short[][][]> apply = maxPooling.apply(provide);

        // then
        for (int x = 0; x < provide.getWidth(); x++) {
            for (int y = 0; y < provide.getHeight(); y++) {
                Assert.assertEquals(apply.getValue(x, y, 0), reference[x][y]);
            }
        }
    }

}
