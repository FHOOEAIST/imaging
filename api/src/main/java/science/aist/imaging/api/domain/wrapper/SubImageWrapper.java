/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;


import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;

/**
 * <p>Class that represents a sub ROI of a referenced image</p>
 *
 * @param <I> The type of the image
 * @author Christoph Praschl
 * @since 1.0
 */
public class SubImageWrapper<I> implements ImageWrapper<I> {
    private final ImageWrapper<I> reference;
    private final int startX;
    private final int startY;
    private final int width;
    private final int height;

    public SubImageWrapper(ImageWrapper<I> reference, JavaRectangle2D roi) {
        this(reference, roi.getTopLeft().getIntX(), roi.getTopLeft().getIntY(), (int) roi.getWidth(), (int) roi.getHeight());
    }

    public SubImageWrapper(ImageWrapper<I> reference, int startX, int startY, int width, int height) {
        if (startX < 0 || startY < 0 || width < 1 || height < 1) {
            throw new IllegalArgumentException("Roi definition not valid");
        }
        if (startX + width > reference.getWidth()) {
            throw new IllegalArgumentException("Subimage to wide");
        }

        if (startY + height > reference.getHeight()) {
            throw new IllegalArgumentException("Subimage to high");
        }

        this.reference = reference;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    /**
     * @return Attention: returns the referenced image not the sub image! If you want to get the sub image create a deep copy using {@link ImageWrapper#createCopy(ImageFactory)} or {@link ImageWrapper#copyTo(ImageWrapper)}
     */
    @Override
    public I getImage() {
        return reference.getImage();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public ChannelType getChannelType() {
        return reference.getChannelType();
    }

    @Override
    public void close() {
        reference.close();
    }

    @Override
    public double getValue(int x, int y, int channel) {
        return reference.getValue(x + startX, y + startY, channel);
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        reference.setValue(x + startX, y + startY, channel, val);
    }

    @Override
    public int getChannels() {
        return reference.getChannels();
    }

    @Override
    public boolean supportsParallelAccess() {
        return reference.supportsParallelAccess();
    }

    @Override
    public void applyFunction(PixelFunction function, int startX, int startY, int endX, int endY, int strideX, int strideY, boolean applyParallel) {
        reference.applyFunction(function, startX + this.startX, startY + this.startY, endX + this.startX, endY + this.startY, strideX, strideY, applyParallel);
    }
}
