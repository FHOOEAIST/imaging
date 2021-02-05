/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation.morph;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.jack.math.MathUtils;

/**
 * <p>Implementation of Dilation</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class DilateFunction<T, R> implements ImageFunction<T, R> {
    private static final TypeChecker TYPE_CHECKER = new TypeChecker(ChannelType.BINARY);
    private static final double PIXEL_SET = 255;
    private static final double PIXEL_UNSET = 0;
    private static final double[][] DILATION_MASK = new double[][]{
            {PIXEL_UNSET, PIXEL_SET, PIXEL_UNSET},
            {PIXEL_SET, PIXEL_SET, PIXEL_SET},
            {PIXEL_UNSET, PIXEL_SET, PIXEL_UNSET}
    };
    private static final int RADIUS = 1;
    @NonNull
    private ImageFactory<R> provider;

    @Override
    @SuppressWarnings("java:S1119")
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        TYPE_CHECKER.accept(imageWrapper);
        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();
        ImageWrapper<R> resultWrapper = provider.getImage(height, width, ChannelType.BINARY);
        resultWrapper.applyFunction((image, x, y, c) -> {
            resultWrapper.setValue(x, y, 0, PIXEL_UNSET);
            offset:
            for (int xOffset = -RADIUS; xOffset <= RADIUS; xOffset++) {
                for (int yOffset = -RADIUS; yOffset <= RADIUS; yOffset++) {
                    int nbX = x + xOffset;
                    int nbY = y + yOffset;
                    if (nbX >= 0 && nbX < width && nbY >= 0 && nbY < height &&
                            MathUtils.equals(imageWrapper.getValue(nbX, nbY, 0), PIXEL_SET) &&
                            DILATION_MASK[yOffset + RADIUS][xOffset + RADIUS] == PIXEL_SET) {
                        resultWrapper.setValue(x, y, 0, PIXEL_SET);
                        break offset;
                    }
                }
            }
        });

        return resultWrapper;
    }
}

