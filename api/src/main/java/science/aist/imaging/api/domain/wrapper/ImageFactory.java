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

import java.util.Arrays;
import java.util.Random;

/**
 * <p>Factory class for a given imagewrapper type</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public interface ImageFactory<X> {
    /**
     * Creates a image with the given height, width and channeltype
     *
     * @param height  height of the image
     * @param width   width of the image
     * @param channel channeltype of the image
     * @return image with the given properties
     */
    ImageWrapper<X> getImage(int height, int width, ChannelType channel);

    /**
     * Creates a image with the given height, width and channeltype
     *
     * @param height  height of the image
     * @param width   width of the image
     * @param channel channel type of the image
     * @param image   the data which should be encapsulated in the image
     * @return image with the given properties
     */
    ImageWrapper<X> getImage(int height, int width, ChannelType channel, X image);

    /**
     * Creates a image with the given image
     *
     * @param image the data which should be encapsulated in the image
     * @return image with the given properties
     */
    ImageWrapper<X> getImage(X image);


    /**
     * Creates a greyscale image with the given height and width
     *
     * @param height height of the image
     * @param width  width of the image
     * @return image with the given properties
     */
    default ImageWrapper<X> getImage(int height, int width) {
        return this.getImage(height, width, ChannelType.GREYSCALE);
    }

    /**
     * Method which provides an image of the required type with the given properties and initializes it with the given
     * default value
     *
     * @param height       height of the result image
     * @param width        width of the result image
     * @param type         type of the result image
     * @param defaultvalue used for initialization of the image
     * @return image
     */
    default ImageWrapper<X> getImage(int height, int width, ChannelType type, Color defaultvalue) {
        return getImage(height, width, type, defaultvalue.getChannels());
    }

    /**
     * Method which provides an image of the required type with the given properties and initializes it with the given
     * default value
     *
     * @param height       height of the result image
     * @param width        width of the result image
     * @param type         type of the result image
     * @param defaultvalue used for initialization of the image
     * @return image
     */
    default ImageWrapper<X> getImage(int height, int width, ChannelType type, double[] defaultvalue) {
        if (type.getNumberOfChannels() != defaultvalue.length) {
            throw new IllegalArgumentException("Default value does not match the size of the given channeltype");
        }

        ImageWrapper<X> provide = getImage(height, width, type);
        provide.applyFunction((image, x, y, c) -> image.setValue(x, y, c, defaultvalue[c]));
        return provide;
    }

    /**
     * Method which provides an image of the required type with the given properties and initializes it with the given
     * default value
     *
     * @param height       height of the result image
     * @param width        width of the result image
     * @param type         type of the result image
     * @param defaultvalue used for initialization of the image
     * @return image
     */
    default ImageWrapper<X> getImage(int height, int width, ChannelType type, double defaultvalue) {
        int channels = type.getNumberOfChannels();
        double[] values = new double[channels];
        Arrays.fill(values, defaultvalue);
        return getImage(height, width, type, values);
    }

    /**
     * Method which provides an image of the required type with the given properties and initializes it with random
     * values
     *
     * @param height        height of the result image
     * @param width         width of the result image
     * @param type          type of the result image
     * @param random        used for random initialization of the image
     * @param min           The minimal value
     * @param max           The maximal value
     * @param applyParallel create random image parallel (Attention: Will lead to a real random image, since the moment
     *                      when a pixel is set can't be ensured)
     * @return image
     */
    default ImageWrapper<X> getRandomImage(int height, int width, ChannelType type, Random random, double min, double max, boolean applyParallel) {
        ImageWrapper<X> provide = getImage(height, width, type);
        provide.applyFunction((image, x, y, c) -> image.setValue(x, y, c, Math.min(min + random.nextDouble() * max, max)), applyParallel);
        return provide;
    }
}
