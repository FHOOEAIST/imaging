/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.jack.general.util.CastUtils;

import java.util.function.Function;

/**
 * <p>Generic image function that enables the interoperability between different {@link ImageWrapper} implementations</p>
 *
 * <p>Note: The {@link GenericImageFunction} converts the input as well as the output images if necessary, and for this effects the performance because of the required copy operation.</p>

 * @author Christoph Praschl
 * @since 1.1
 * @param <I> Input type of this generic image function
 * @param <O> Output type of this generic image function
 * @param <I2> Input type of the wrapped image function
 * @param <O2> Output type of this generic image function
 */
public class GenericImageFunction<I, O, I2, O2> implements ImageFunction<I, O> {

    private final Function<ImageWrapper<I2>, ImageWrapper<O2>> function;
    private final ImageFactory<I2> functionInputFactory;
    private final ImageFactory<O> outputFactory;

    /**
     * Constructor of a GenericImageFunction
     * @param function wrapped function to be applied
     * @param functionInputType input type of the wrapped(!) function
     * @param outputType output type of the generic function
     */
    public GenericImageFunction(Function<ImageWrapper<I2>, ImageWrapper<O2>> function, Class<I2> functionInputType, Class<O> outputType){
        this(function, ImageFactoryFactory.getImageFactory(functionInputType), ImageFactoryFactory.getImageFactory(outputType));
    }

    /**
     * Constructor of a GenericImageFunction
     * @param function wrapped function to be applied
     * @param functionInputFactory Factory of the input type of the wrapped(!) function
     * @param outputFactory Factory of the output type of the generic function
     */
    public GenericImageFunction(Function<ImageWrapper<I2>, ImageWrapper<O2>> function, ImageFactory<I2> functionInputFactory, ImageFactory<O> outputFactory) {
        this.function = function;
        this.functionInputFactory = functionInputFactory;
        this.outputFactory = outputFactory;
    }

    @Override
    public ImageWrapper<O> apply(ImageWrapper<I> image) {
        ImageWrapper<I2> input;
        if(image.getSupportedType().equals(functionInputFactory.getSupportedType())){
            input = CastUtils.cast(image);
        } else {
            input = image.createCopy(functionInputFactory);
        }

        ImageWrapper<O2> functionResult = function.apply(input);

        if(functionResult.getSupportedType().equals(outputFactory.getSupportedType())){
            return CastUtils.cast(functionResult);
        } else {
            try {
                return functionResult.createCopy(outputFactory);
            } finally {
                functionResult.close();  // close interim result
            }
        }
    }
}
