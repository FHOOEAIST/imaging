/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;


import science.aist.imaging.api.compare.GenericImageCompareFunction;
import science.aist.imaging.api.util.ToBooleanBiFunction;
import science.aist.seshat.Logger;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Abstract generic implementation of the ImageWrapper Interface</p>
 *
 * @param <I> Target type representing an image which should be wrapped.
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractImageWrapper<I> implements ImageWrapper<I> {
    /**
     * Logger
     */
    private static final Logger logger = Logger.getInstance();

    /**
     * Compare Function
     */
    private static final ToBooleanBiFunction<ImageWrapper<?>, ImageWrapper<?>> compareFunction = new GenericImageCompareFunction();
    /**
     * Stores all references of image wrappers which are created. This is used, to close the image wrappers after test methods
     */
    private static final Set<WeakReference<AbstractImageWrapper<?>>> imageWrapperReferences = new HashSet<>();
    /**
     * The wrapped image. It is a good idea to use a serializable type, but since there may be non-serializable types it is also ok if not.
     */
    @SuppressWarnings("java:S1948")
    protected I image;
    /**
     * Channel type of the wrapped image
     */
    protected ChannelType channelType = ChannelType.UNKNOWN;
    /**
     * Flag to check if an image is closed
     */
    protected boolean closed = false;

    private AbstractImageWrapper() {
        imageWrapperReferences.add(new WeakReference<>(this));
    }

    public AbstractImageWrapper(I image) {
        this();
        this.image = image;
    }

    /**
     * Frees all allocated Image wrappers by calling close method
     */
    public static void freeAllocatedImageWrappers() {
        AtomicInteger count = new AtomicInteger(0);
        imageWrapperReferences
                .stream()
                .map(Reference::get)
                .filter(Objects::nonNull)
                .filter(iw -> !iw.closed)
                .peek(iw -> count.incrementAndGet())
                .forEach(ImageWrapper::close);
        logger.info("Closed " + count.get() + " image wrappers");
        imageWrapperReferences.clear();
    }

    /**
     * Getter for the wrapped image.
     *
     * @return Returns the wrapped image.
     */
    @Override
    public I getImage() {
        return image;
    }

    /**
     * Getter for the channel type
     *
     * @return the channel type
     */
    @Override
    public ChannelType getChannelType() {
        return channelType;
    }

    @Override
    public void close() {
        image = null;
        channelType = null;
        closed = true;
    }

    /**
     * Uses {@link GenericImageCompareFunction} to compare if the two image wrappers are equal. If the obj is not a imagewrapper
     * then super.equals will be called.
     *
     * @param obj the object to compare with
     * @return whether the images are equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof ImageWrapper ? compareFunction.applyAsBoolean(this, (ImageWrapper<?>) obj) : super.equals(obj);
    }

    /**
     * Generated Code
     *
     * @return hashCode for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(image, channelType, closed);
    }
}
