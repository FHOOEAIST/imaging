/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.tesseract.imageprocessing.transformers;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.jack.general.transformer.BackwardTransformer;

import java.util.stream.IntStream;

/**
 * @author Andreas Pointner
 * @since 1.0
 */
public class ByteArrayJavaImageTransformer implements BackwardTransformer<byte[], ImageWrapper<short[][][]>> {
    /**
     * @param javaImage to be transformed
     * @return item corresponding to DTO
     */
    @Override
    public byte[] transformTo(ImageWrapper<short[][][]> javaImage) {
        int width = javaImage.getWidth();
        short[][][] img = javaImage.getImage();
        byte[] res = new byte[javaImage.getWidth() * width * javaImage.getChannels()];
        IntStream.range(0, javaImage.getHeight()).parallel().forEach(y -> {
            for (int x = 0; x < width; x++) {
                if (javaImage.getChannelType() == ChannelType.RGB) {
                    res[(x + y * width) * 3 + 2] = (byte) img[y][x][0]; // r
                    res[(x + y * width) * 3 + 1] = (byte) img[y][x][1]; // g
                    res[(x + y * width) * 3] = (byte) img[y][x][2]; // b
                } else if (javaImage.getChannelType() == ChannelType.BGR) {
                    res[(x + y * width) * 3 + 2] = (byte) img[y][x][2]; // r
                    res[(x + y * width) * 3 + 1] = (byte) img[y][x][1]; // g
                    res[(x + y * width) * 3] = (byte) img[y][x][0]; // b
                } else if (javaImage.getChannelType() == ChannelType.GREYSCALE || javaImage.getChannelType() == ChannelType.BINARY) {
                    res[x + y * width] = (byte) img[y][x][0];
                } else {
                    throw new IllegalStateException("no supported");
                }
            }
        });
        return res;
    }
}
