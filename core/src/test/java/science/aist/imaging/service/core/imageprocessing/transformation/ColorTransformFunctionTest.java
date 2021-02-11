/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.function.Function;

/**
 * <p>Test class for {@link ColorTransformFunction}</p>
 *
 * @author Christoph Praschl
 */
public class ColorTransformFunctionTest {

    @Test
    public void testApply() {
        // given
        ImageFactory<short[][][]> provider = ImageFactoryFactory.getImageFactory(short[][][].class);
        Function<Color, Color> colorFunction = color -> {
            if ((int) color.getChannel(0) == 0) {
                return new Color(1);
            } else {
                return new Color(0);
            }
        };
        ColorTransformFunction<short[][][], short[][][]> colorTransformFunction = new ColorTransformFunction<>(provider, colorFunction);

        ImageWrapper<short[][][]> provide = provider.getImage(11, 11, ChannelType.GREYSCALE);
        provide.setValue(5, 5, 0, 10);

        // when
        ImageWrapper<short[][][]> apply = colorTransformFunction.apply(provide);

        // then
        for (int x = 0; x < apply.getWidth(); x++) {
            for (int y = 0; y < apply.getHeight(); y++) {
                int value = (int) apply.getValue(x, y, 0);
                if (x == 5 && y == 5) {
                    Assert.assertEquals(value, 0);
                } else {
                    Assert.assertEquals(value, 1);
                }
            }
        }
    }

}
