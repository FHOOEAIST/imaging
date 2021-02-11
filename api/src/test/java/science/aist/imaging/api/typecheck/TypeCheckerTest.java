/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.typecheck;

import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Tests {@link TypeChecker}</p>
 *
 * @author Andreas Pointner
 */
public class TypeCheckerTest {
    @Test
    public void testSuccess() {
        // given
        ImageWrapper<short[][][]> greyScaleImg = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(0, 0);
        List<ChannelType> allowedTypes = Arrays.asList(ChannelType.GREYSCALE, ChannelType.BINARY);
        TypeChecker typeChecker = new TypeChecker(allowedTypes);

        // when
        typeChecker.accept(greyScaleImg);

        // then
        // No exception
    }

    @Test(expectedExceptions = TypeException.class)
    public void testFail() {
        // given
        ImageWrapper<short[][][]> greyScaleImg = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(0, 0);
        List<ChannelType> allowedTypes = Arrays.asList(ChannelType.RGBA, ChannelType.RGB);
        TypeChecker typeChecker = new TypeChecker(allowedTypes);

        // when
        typeChecker.accept(greyScaleImg);

        // then
        // Exception
    }
}
