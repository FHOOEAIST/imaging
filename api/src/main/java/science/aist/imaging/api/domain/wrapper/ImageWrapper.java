/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.io.Serializable;
import java.util.stream.IntStream;

/**
 * <p>Wrapper interface containing the implemented image-container class</p>
 *
 * @param <I> Target type representing an image which should be wrapped.
 * @author Christoph Praschl
 * @since 1.0
 */
public interface ImageWrapper<I> extends AutoCloseable, Serializable {
    /**
     * Getter for the wrapped image.
     *
     * @return Returns the wrapped image.
     */
    I getImage();

    /**
     * @return The image width
     */
    int getWidth();

    /**
     * @return The image height
     */
    int getHeight();

    /**
     * @return The number of channels
     */
    default int getChannels() {
        return getChannelType().getNumberOfChannels();
    }

    /**
     * @return The channelType of the image
     */
    ChannelType getChannelType();

    /**
     * Overrides signature of close method from {@link AutoCloseable} Interface to not throw an exception
     */
    void close();

    /**
     * Returns the value for a specific pixel and channel
     *
     * @param x       the x-coordinate of the pixel
     * @param y       the y-coordinate of the pixel
     * @param channel the channel to select of a specific pixel
     * @return the value for the pixel and channel
     */
    double getValue(int x, int y, int channel);

    /**
     * Returns all value for a given coordinate
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return all pixel values at the given position
     */
    default double[] getValues(int x, int y) {
        int c = getChannels();
        double[] result = new double[c];


        for (int i = 0; i < c; i++) {
            result[i] = getValue(x, y, i);
        }

        return result;
    }

    /**
     * Returns a color value for the given coordinate
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return color value of the given position
     */
    default Color getValuesAsColor(int x, int y) {
        return new Color(getValues(x, y));
    }

    /**
     * Sets a value for a specific pixel and channel
     *
     * @param x       the x-coordinate of the pixel
     * @param y       the y-coordinate of the pixel
     * @param channel the channel to select of a specific pixel
     * @param val     the value for the pixel and channel
     */
    void setValue(int x, int y, int channel, double val);

    /**
     * Sets values for the given coordinate
     *
     * @param x      the x-coordinate of the pixel
     * @param y      the y-coordinate of the pixel
     * @param values values for the given coordinate
     */
    default void setValues(int x, int y, double[] values) {
        for (int c = 0; c < getChannels(); c++) {
            setValue(x, y, c, values[c]);
        }
    }

    /**
     * Sets values for the given coordinate
     *
     * @param x     the x-coordinate of the pixel
     * @param y     the y-coordinate of the pixel
     * @param color to be set at the given coordinate
     */
    default void setValues(int x, int y, Color color) {
        setValues(x, y, color.getChannels());
    }

    /**
     * @return true iff the image wrapper supports parallel write access. Else false
     */
    default boolean supportsParallelAccess() {
        return false;
    }

    /**
     * Applies the given function for every pixel of the image
     * if {@link ImageWrapper#supportsParallelAccess()} this is done parallel.
     * Note: This function does not change pixels per default; only if you call image.setValue() in the given {@link PixelFunction}!
     *
     * @param function function applied for ever pixel
     */
    default void applyFunction(PixelFunction function) {
        applyFunction(function, supportsParallelAccess());
    }

    /**
     * Applies the given function for the pixels of the image segment defined by [startX, endX] and [startY, endX]
     * if {@link ImageWrapper#supportsParallelAccess()} this is done parallel.
     * Note: This function does not change pixels per default; only if you call image.setValue() in the given {@link PixelFunction}!
     *
     * @param function function applied for ever pixel
     * @param startX   start x coordinate of image segment
     * @param startY   start y coordinate of image segment
     * @param endX     end x coordinate of image segment
     * @param endY     end y coordinate of image segment
     */
    default void applyFunction(PixelFunction function, int startX, int startY, int endX, int endY) {
        applyFunction(function, startX, startY, endX, endY, 1, 1, supportsParallelAccess());
    }

    /**
     * Applies the given function for every pixel of the image
     *
     * @param applyParallel flag which decides if function is applied in parallel (ATTENTION: when using this in combination with {@link ImageWrapper#setValue(int, int, int, double) for the given image; Always check  {@link ImageWrapper#supportsParallelAccess()}! }
     * @param function      function applied for ever pixel
     */
    default void applyFunction(PixelFunction function, boolean applyParallel) {
        applyFunction(function, 0, 0, getWidth(), getHeight(), 1, 1, applyParallel);
    }

    /**
     * Applies the given function for the pixels of the image segment defined by [startX, endX] and [startY, endX]
     *
     * @param function      function applied for ever pixel
     * @param startX        start x coordinate of image segment
     * @param startY        start y coordinate of image segment
     * @param endX          end x coordinate of image segment
     * @param endY          end y coordinate of image segment
     * @param strideX       stride for the x coordinate (e.g. take every, every-second, every-third, ... element)
     * @param strideY       stride for the y coordinate (e.g. take every, every-second, every-third, ... element)
     * @param applyParallel flag which decides if function is applied in parallel (ATTENTION: when using this in combination with {@link ImageWrapper#setValue(int, int, int, double) for the given image; Always check  {@link ImageWrapper#supportsParallelAccess()}! }
     */
    default void applyFunction(PixelFunction function, int startX, int startY, int endX, int endY, int strideX, int strideY, boolean applyParallel) {
        if (startX < 0 || startX > getWidth()) {
            throw new IllegalArgumentException("StartX illegal " + startX);
        }

        if (endX < startX || endX > getWidth()) {
            throw new IllegalArgumentException("EndX illegal " + endX);
        }

        if (strideX < 1) {
            throw new IllegalArgumentException("StrideX illegal " + strideX);
        }

        applyColumnFunction((image, y) -> {
            for (int x = startX; x < endX; x += strideX) {
                for (int c = 0; c < getChannels(); c++) {
                    function.apply(this, x, y, c);
                }
            }
        }, startY, endY, strideY, applyParallel);
    }


    /**
     * Function that is applied for a given column
     *
     * @param function      to be applied
     * @param start         start column
     * @param end           end column
     * @param stride        stride for the y coordinate (e.g. take every, every-second, every-third, ... element)
     * @param applyParallel flag which decides if function is applied in parallel (ATTENTION: when using this in combination with {@link ImageWrapper#setValue(int, int, int, double) for the given image; Always check  {@link ImageWrapper#supportsParallelAccess()}! }
     */
    default void applyColumnFunction(ColumnFunction function, int start, int end, int stride, boolean applyParallel) {
        if (start < 0 || start > getHeight()) {
            throw new IllegalArgumentException("Start illegal " + start);
        }

        if (end < start || end > getHeight()) {
            throw new IllegalArgumentException("End illegal " + end);
        }

        if (stride < 1) {
            throw new IllegalArgumentException("Stride illegal " + stride);
        }

        if (!supportsParallelAccess() && applyParallel) {
            throw new IllegalArgumentException("ImageWrapper does not support parallel access!");
        }

        int wY = end - start;

        IntStream stream;
        if (wY % stride != 0) {
            stream = IntStream.rangeClosed(0, wY / stride);
        } else {
            stream = IntStream.range(0, wY / stride);
        }

        stream = stream.map(y -> y * stride + start);
        if (applyParallel) {
            stream = stream.parallel();
        }

        stream.forEach(y -> function.apply(this, y));
    }

    /**
     * Copy the image of this wrapper into the given wrapper
     *
     * @param wrapper target
     */
    default void copyTo(ImageWrapper<?> wrapper) {
        wrapper.applyFunction((image, x, y, c) -> image.setValue(x, y, c, this.getValue(x, y, c)));
    }

    /**
     * Creates a copy using the given {@link ImageFactory}
     *
     * @param provider provider
     * @param <X>      type of wrapped image
     * @return copy of this
     */
    default <X> ImageWrapper<X> createCopy(ImageFactory<X> provider) {
        ImageWrapper<X> provide = provider.getImage(getHeight(), getWidth(), getChannelType());
        this.copyTo(provide);
        return provide;
    }

    /**
     * Creates a copy with the given type
     *
     * @param targetClass type of the copy
     * @param <X>      type of wrapped image
     * @return copy of this
     */
    default <X> ImageWrapper<X> createCopy(Class<X> targetClass) {
        return createCopy(ImageFactoryFactory.getImageFactory(targetClass));
    }

    /**
     * @return the supported image type.
     */
    Class<I> getSupportedType();

}
