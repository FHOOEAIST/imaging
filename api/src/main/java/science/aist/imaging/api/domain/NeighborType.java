/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Neighborhood definitions</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum NeighborType {
    N4(new boolean[][]{{false, true, false}, {true, true, true}, {false, true, false}}, 3, 3),
    N8(new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}}, 3, 3);

    private final boolean[][] mask;
    private final int width;
    private final int height;

    /**
     * @return Image binary representation of the neighbor type
     */
    public ImageWrapper<short[][][]> getImageMask() {
        ImageWrapper<short[][][]> image = Image2ByteFactory.getInstance().getImage(height, width, ChannelType.BINARY);
        image.applyFunction((image1, x, y, c) -> image.setValue(x, y, c, getMask()[x][y] ? 255 : 0));

        return image;
    }
}