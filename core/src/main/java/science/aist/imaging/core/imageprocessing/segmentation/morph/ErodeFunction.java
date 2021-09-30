/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.segmentation.morph;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;

/**
 * <p>Implementation of Erosion</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class ErodeFunction<T, R> implements ImageFunction<T, R> {

    private static final TypeChecker TYPE_CHECKER = new TypeChecker(ChannelType.BINARY);
    private static final int PIXEL_SET = 255;
    private static final int PIXEL_UNSET = 0;
    private static final short[][] EROSION_MASK = new short[][]{
            {PIXEL_UNSET, PIXEL_SET, PIXEL_UNSET},
            {PIXEL_SET, PIXEL_SET, PIXEL_SET},
            {PIXEL_UNSET, PIXEL_SET, PIXEL_UNSET}
    };
    private static final int RADIUS = 1;
    @NonNull
    private final ImageFactory<R> provider;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        TYPE_CHECKER.accept(imageWrapper);
        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();
        ImageWrapper<R> resultWrapper = provider.getImage(height, width, ChannelType.BINARY);

        resultWrapper.applyFunction((image, x, y, c) -> {
            int cnt = 0;
            int total = 0;

            for (int xOffset = -RADIUS; xOffset <= RADIUS; xOffset++) {
                for (int yOffset = -RADIUS; yOffset <= RADIUS; yOffset++) {
                    int nbX = x + xOffset;
                    int nbY = y + yOffset;
                    if (nbX >= 0 && nbX < width && nbY >= 0 && nbY < height) {
                        total++;
                        if (imageWrapper.getValue(nbX, nbY, 0) >= EROSION_MASK[yOffset + RADIUS][xOffset + RADIUS]) {
                            cnt++;
                        }
                    }
                }
            }

            if (cnt == total) {
                image.setValue(x, y, 0, PIXEL_SET);
            } else {
                image.setValue(x, y, 0, PIXEL_UNSET);
            }
        });

        return resultWrapper;
    }
}
