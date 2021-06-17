/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.jack.general.util.CastUtils;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>Tests {@link ImageFunction}</p>
 *
 * @author Andreas Pointner
 * @since 1.2
 */
public class ImageFunctionTest {

    @Test
    public void testCloseAfterApply() {
        // given
        ImageFunction<Void, Void> func = x -> x;
        ImageWrapper<Void> input = CastUtils.cast(Mockito.mock(ImageWrapper.class));

        // when
        Function<ImageWrapper<Void>, ImageWrapper<Void>> imageWrapperImageWrapperFunction = ImageFunction.closeAfterApply(func);

        // then
        imageWrapperImageWrapperFunction.apply(input);
        Mockito.verify(input, Mockito.times(1)).close();
    }

    @Test
    public void testAndThenConsumeInput() {
        // given
        ImageFunction<Void, Void> func = x -> x;
        ImageWrapper<Void> input = CastUtils.cast(Mockito.mock(ImageWrapper.class));
        Consumer<ImageWrapper<Void>> consume = CastUtils.cast(Mockito.mock(Consumer.class));

        // when
        ImageFunction<Void, Void> toTest = func.andThenConsumeInput(consume);

        // then
        toTest.apply(input);
        Mockito.verify(consume, Mockito.times(1)).accept(input);
    }

    @Test
    public void testAndThenCloseInput() {
        // given
        ImageFunction<Void, Void> func = x -> x;
        ImageWrapper<Void> input = CastUtils.cast(Mockito.mock(ImageWrapper.class));

        // when
        func = func.andThenCloseInput();

        // then
        func.apply(input);
        Mockito.verify(input, Mockito.times(1)).close();
    }

    @SuppressWarnings("Convert2Lambda") // spying on lambdas did not work as expected
    @Test
    public void testAndThen() {
        // given
        ImageWrapper<Void> input = CastUtils.cast(Mockito.mock(ImageWrapper.class));
        ImageFunction<Void, Void> func = Mockito.spy(new ImageFunction<Void, Void>() {
            @Override
            public ImageWrapper<Void> apply(ImageWrapper<Void> voidImageWrapper) {
                return voidImageWrapper;
            }
        });
        ImageFunction<Void, Void> func2 = Mockito.spy(new ImageFunction<Void, Void>() {
            @Override
            public ImageWrapper<Void> apply(ImageWrapper<Void> voidImageWrapper) {
                return voidImageWrapper;
            }
        });

        // when
        ImageFunction<Void, Void> toTest = func.andThen(func2);

        // then
        toTest.apply(input);
        Mockito.verify(func, Mockito.times(1)).apply(input);
        Mockito.verify(func2, Mockito.times(1)).apply(input);
        Mockito.verify(input, Mockito.times(1)).close();
    }
}
