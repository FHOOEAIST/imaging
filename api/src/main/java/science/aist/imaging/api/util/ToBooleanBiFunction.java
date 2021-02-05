/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.util;

import java.util.function.BiFunction;

/**
 * <p>Represents a function that accepts two arguments and produces an boolean-valued
 * result.  This is the {@code boolean}-producing primitive specialization for
 * {@link BiFunction}.</p>
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsBoolean(Object, Object)}.</p>
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @author Andreas Pointner
 * @see BiFunction
 * @since 1.0
 */
@FunctionalInterface
public interface ToBooleanBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    boolean applyAsBoolean(T t, U u);
}
