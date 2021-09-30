/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.openjfx.imageprocessing.wrapper;

import javafx.scene.image.WritableImage;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Imagefactory implementation for JavaFX's {@link WritableImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.2
 */
public class WriteableImageFactory implements ImageFactory<WritableImage> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = ImageProcessor.class} for this specific factory.
     */
    public WriteableImageFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<WritableImage> getImage(int height, int width, ChannelType channel) {
        return new WritableImageWrapper(new WritableImage(width, height), channel);
    }

    @Override
    public ImageWrapper<WritableImage> getImage(int height, int width, ChannelType channel, WritableImage image) {
        if (height != image.getHeight()) {
            throw new IllegalArgumentException("Given height does not match the given image");
        }

        if (width != image.getWidth()) {
            throw new IllegalArgumentException("Given width does not match the given image");
        }

        return new WritableImageWrapper(image, channel);
    }

    @Override
    public ImageWrapper<WritableImage> getImage(WritableImage image) {
        return new WritableImageWrapper(image, ChannelType.RGB);
    }

    @Override
    public Class<WritableImage> getSupportedType() {
        return WritableImage.class;
    }
}
