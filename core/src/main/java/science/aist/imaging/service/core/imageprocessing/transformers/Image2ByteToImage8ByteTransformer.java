/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Transformer for Image2Byte to Image8Byte</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class Image2ByteToImage8ByteTransformer implements Transformer<ImageWrapper<short[][][]>, ImageWrapper<double[][][]>> {

    /**
     * Note that double to short cast can result in unpredictable results if image was not normalized before
     *
     * @param image8Byte to be transformed
     * @return From corresponding to To
     */
    @Override
    public ImageWrapper<short[][][]> transformTo(ImageWrapper<double[][][]> image8Byte) {
        int height = image8Byte.getHeight();
        int width = image8Byte.getWidth();
        ChannelType channel = image8Byte.getChannelType();

        ImageWrapper<short[][][]> res = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(height, width, channel);

        double[][][] inImage = image8Byte.getImage();

        res.applyFunction((image, x, y, c) -> res.getImage()[y][x][c] = (short) (inImage[y][x][c]));

        return res;
    }

    @Override
    public ImageWrapper<double[][][]> transformFrom(ImageWrapper<short[][][]> image1Byte) {
        int height = image1Byte.getHeight();
        int width = image1Byte.getWidth();
        ChannelType channel = image1Byte.getChannelType();

        ImageWrapper<double[][][]> res = ImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width, channel);

        short[][][] inImage = image1Byte.getImage();

        res.applyFunction((image, x, y, c) -> res.getImage()[y][x][c] = (inImage[y][x][c]));

        return res;
    }
}
