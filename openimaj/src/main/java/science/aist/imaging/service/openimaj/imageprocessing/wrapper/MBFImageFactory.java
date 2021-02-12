/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.openimaj.imageprocessing.wrapper;

import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.service.openimaj.imageprocessing.transformer.ColourSpace2ChannelTypeTransformer;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageFactory} for OpenIMAJ's {@link MBFImage}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class MBFImageFactory implements ImageFactory<MBFImage> {

    /**
     * Do not instantiate this class directly. This constructor is only need, to work with {@link java.util.ServiceLoader}.
     * Get yourself an instance using {@link ImageFactoryFactory#getImageFactory(Class)} method.
     * Using {@code class = ImageProcessor.class} for this specific factory.
     */
    public MBFImageFactory() {
        // Note: This is needed for usage with ServiceLoader.
    }

    private static final ColourSpace2ChannelTypeTransformer transformer = new ColourSpace2ChannelTypeTransformer();

    @Override
    public ImageWrapper<MBFImage> getImage(int height, int width, ChannelType channel) {
        ColourSpace colourSpace = transformer.transformFrom(channel);
        return getImage(height, width, channel, new MBFImage(width, height, colourSpace));
    }

    /**
     * Creates a greyscale image with the given height and width
     *
     * @param height        height of the image
     * @param width         width of the image
     * @param numOfChannels with the number of channels
     * @return image with the given properties
     */
    public ImageWrapper<MBFImage> getImage(int height, int width, int numOfChannels) {
        MBFImage fImage = new MBFImage(width, height, numOfChannels);
        ChannelType channelType = transformer.transformTo(fImage.getColourSpace());
        return getImage(height, width, channelType, fImage);
    }

    @Override
    public ImageWrapper<MBFImage> getImage(int height, int width, ChannelType channel, MBFImage image) {
        if (channel.getNumberOfChannels() != image.getColourSpace().getNumBands()) {
            throw new IllegalArgumentException("Given channel type does not match");
        }

        if (channel == ChannelType.UNKNOWN || channel == ChannelType.UNKNOWN_2_CHANNEL || channel == ChannelType.UNKNOWN_3_CHANNEL || channel == ChannelType.UNKNOWN_4_CHANNEL) {
            throw new IllegalArgumentException("Unknown Channel type is not supported because the value normalization (0..1) for OpenIMAJ is not possible like that.");
        }

        if (height != image.getHeight()) {
            throw new IllegalArgumentException("Given height does not match the given image");
        }

        if (width != image.getWidth()) {
            throw new IllegalArgumentException("Given width does not match the given image");
        }

        return new MBFImageWrapper(image, channel);
    }

    @Override
    public ImageWrapper<MBFImage> getImage(MBFImage image) {
        ChannelType channelType = transformer.transformTo(image.getColourSpace());
        return getImage(image.getHeight(), image.getWidth(), channelType, image);
    }

    @Override
    public Class<MBFImage> getSupportedType() {
        return MBFImage.class;
    }
}
