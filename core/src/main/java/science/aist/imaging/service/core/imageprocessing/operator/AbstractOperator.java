/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.operator;

import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.BinaryOperator;

/**
 * <p>Abstract operator implementation for combining two images</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractOperator<I> implements BinaryOperator<ImageWrapper<I>> {
    /***
     * ImageProvider used to create a new result-image
     */
    @Setter
    protected ImageFactory<I> provider;

    @Override
    public ImageWrapper<I> apply(ImageWrapper<I> wrapper, ImageWrapper<I> wrapper2) {
        return apply(wrapper, wrapper2, null);
    }

    /**
     * <p>Applies the operator for the two images</p>
     * <ul>
     *  <li>if mask == null: for every pixel</li>
     *  <li>else where mask &gt; 0.0 </li>
     * </ul>
     *
     * @param wrapper1 operator 1
     * @param wrapper2 operator 2
     * @param mask     optional mask used for restricting operator to certain positions
     * @return the modified wrapper1 or a new image if {@link AbstractOperator#provider} was set
     */
    public ImageWrapper<I> apply(ImageWrapper<I> wrapper1, ImageWrapper<I> wrapper2, ImageWrapper<I> mask) {
        checkMask(mask);

        if(wrapper1.getHeight() != wrapper2.getHeight() ||
            wrapper1.getWidth() != wrapper2.getWidth()||
            wrapper1.getChannels() != wrapper2.getChannels()){
            throw new IllegalArgumentException("Given images does not match in dimensions (width/height/channels)");
        }

        ImageWrapper<I> result = prepareResult(wrapper1);

        result.applyFunction((image, x, y, c) -> {
            if (mask == null || mask.getValue(x, y, 0) > 0.0) {
                double val1 = image.getValue(x, y, c);
                double val2 = wrapper2.getValue(x, y, c);
                double res = execute(val1, val2);
                image.setValue(x, y, c, res);
            }
        });

        return result;
    }

    /**
     * <p>Applies the operator for the given image with the scalar value</p>
     *
     * @param wrapper1 operator 1
     * @param scalar scalar value to be applied
     * @return the modified wrapper1 or a new image if {@link AbstractOperator#provider} was set
     */
    public ImageWrapper<I> apply(ImageWrapper<I> wrapper1, double scalar) {
        return apply(wrapper1, scalar, null);
    }

    /**
     * <p>Applies the operator for the given image with the scalar value</p>
     * <ul>
     *  <li>if mask == null: for every pixel</li>
     *  <li>else where mask &gt; 0.0 </li>
     * </ul>
     *
     * @param wrapper1 operator 1
     * @param scalar scalar value to be applied
     * @param mask     optional mask used for restricting operator to certain positions
     * @return the modified wrapper1 or a new image if {@link AbstractOperator#provider} was set
     */
    public ImageWrapper<I> apply(ImageWrapper<I> wrapper1, double scalar, ImageWrapper<I> mask) {
        checkMask(mask);
        ImageWrapper<I> result = prepareResult(wrapper1);

        result.applyFunction((image, x, y, c) -> {
            if (mask == null || mask.getValue(x, y, 0) > 0.0) {
                double val1 = image.getValue(x, y, c);
                double res = execute(val1, scalar);
                image.setValue(x, y, c, res);
            }
        });

        return result;
    }

    /**
     * Checks the given mask
     * @param mask to be checked
     */
    protected void checkMask(ImageWrapper<I> mask){
        if (mask != null && mask.getChannels() != 1) {
            throw new IllegalArgumentException("Given mask must be null or must not have more than one channels.");
        }
    }

    /**
     * Prepare the result image using the image provider or the given image
     * @param wrapper1 used for creating the result image or used as result image
     * @return result image
     */
    protected ImageWrapper<I> prepareResult(ImageWrapper<I> wrapper1){
        ImageWrapper<I> result;

        if (provider != null) {
            result = wrapper1.createCopy(provider);
        } else {
            result = wrapper1;
        }
        return result;
    }

    /**
     * Method that calculates the new value based on the two source values
     *
     * @param val1 source value of image 1
     * @param val2 source value of image 2
     * @return combined values
     */
    protected abstract double execute(double val1, double val2);

}
