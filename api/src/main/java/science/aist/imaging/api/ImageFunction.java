/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>Interface for every sort of image function</p>
 * <p>This interface also provides different comfort function which are default implemented, to allow easy
 * chaining of different functions</p>
 * <pre>{@code
 *   // Example from the PASS project, which uses OpenCVDistanceMap implementation.
 *   ImageFunction.closeAfterApply(openCVDistanceMap)
 *             .compose((Function<JavaImage, ImageWrapper<Mat>>) imageTransformer::from)
 *             .andThen(ImageFunction.closeAfterApply(GraphStructuralElementToGraphRoomTransformer::openCVDistanceMapToJava))
 *             .apply(ji);
 * }</pre>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public interface ImageFunction<IN, OUT> extends Function<ImageWrapper<IN>, ImageWrapper<OUT>> {
    /**
     * We do not have everywhere a ImageFunction, sometimes it is just form the type function.
     * In those cases we need a construct, which allows us to close the image wrapper, after another function.
     * Also closes the input function if apply throws an exception.
     *
     * @param function the function to be executed
     * @param <T>      the input type which needs to extend Java Image Wrapper
     * @param <R>      the result
     * @param <I>      the type of the wrapped element of ImageWrapper
     * @return a new function, which executes the function that is passed as parameter and the closes the image wrapper.
     */
    static <T extends ImageWrapper<I>, R, I> Function<T, R> closeAfterApply(Function<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } finally {
                t.close();
            }
        };
    }

    /**
     * This function returns a function, that execute a given consumer after the apply function for the original function was called
     *
     * @param consumer the consumer to be executed after the accept function
     * @return a new function, that call the apply function of this function, then executes the consumer and the returns the result of the original function
     */
    default ImageFunction<IN, OUT> andThenConsumeInput(Consumer<ImageWrapper<IN>> consumer) {
        return (ImageWrapper<IN> in) -> {
            ImageWrapper<OUT> tmp = apply(in);
            consumer.accept(in);
            return tmp;
        };
    }

    /**
     * Closes the input image, after the apply function was executed
     *
     * @return a new function which closes the input of the function after it was executed
     */
    default ImageFunction<IN, OUT> andThenCloseInput() {
        return andThenConsumeInput(ImageWrapper::close);
    }
}
