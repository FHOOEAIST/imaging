/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.openimaj.imageprocessing.wrapper;

import org.openimaj.image.FImage;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageFactory} for OpenIMAJ's {@link FImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class FImageFactory  implements ImageFactory<FImage> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = ImageProcessor.class} for this specific factory.
     */
    public FImageFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    @Override
    public ImageWrapper<FImage> getImage(int height, int width, ChannelType channel) {
        return getImage(height, width, channel, new FImage(width, height));
    }

    @Override
    public ImageWrapper<FImage> getImage(int height, int width, ChannelType channel, FImage image) {
        if (channel != ChannelType.BINARY && channel != ChannelType.GREYSCALE){
            throw new IllegalArgumentException("ChannelType not allowed. FImage does only support 1-channel images.");
        }

        if(height != image.getHeight()){
            throw new IllegalArgumentException("Given height does not match the given image");
        }

        if(width != image.getWidth()){
            throw new IllegalArgumentException("Given width does not match the given image");
        }

        return new FImageWrapper(image, channel);
    }

    @Override
    public ImageWrapper<FImage> getImage(FImage image) {
        return getImage(image.getHeight(), image.getWidth(), ChannelType.GREYSCALE, image);
    }

    @Override
    public Class<FImage> getSupportedType() {
        return FImage.class;
    }
}
