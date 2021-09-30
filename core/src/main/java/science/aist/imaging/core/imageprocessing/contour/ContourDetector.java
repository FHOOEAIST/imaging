/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.contour;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.jack.math.MathUtils;

import java.util.function.Function;

/**
 * <p>Generic contour detector which creates a contour image with 255 for contour pixels and 0 for background pixels</p>
 * <p>iff {@link ContourDetector#innerContour} === true: inner contour will be created</p>
 * <p>else outer contour will be created</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@RequiredArgsConstructor
public class ContourDetector<T> implements Function<ImageWrapper<?>, ImageWrapper<T>> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.BINARY);

    @NonNull
    private final ImageFactory<T> provider;

    @Setter
    private boolean innerContour = true;

    @Override
    @SuppressWarnings("java:S1119")
    public ImageWrapper<T> apply(ImageWrapper<?> image) {
        typeChecker.accept(image);

        ImageWrapper<T> result = provider.getImage(image.getHeight(), image.getWidth(), ChannelType.BINARY);

        double color = innerContour ? 255.0 : 0.0;

        // find the contour points in the image
        // a pixel is a contour pixel iff === WHITE and at least one neighbor == BLACK
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                // check if current pixel is white
                if (MathUtils.equals(image.getValue(x, y, 0), color)) {
                    neighborhood:

                    // check neighborhood for black pixels
                    for (int innerx = -1; innerx <= 1; innerx++) {
                        for (int innery = -1; innery <= 1; innery++) {
                            int xPos = x + innerx;
                            int yPos = y + innery;
                            if (xPos >= 0 && xPos < image.getWidth() &&
                                    yPos >= 0 && yPos < image.getHeight() &&
                                    !MathUtils.equals(image.getValue(xPos, yPos, 0), color)) {
                                result.setValue(x, y, 0, 255);

                                // if one black neighbor is found, we no the current pixel is a contour pixel, so we don't have to look at the complete neighborhood
                                break neighborhood;
                            }
                        }
                    }
                }
            }
        }

        return result;

    }

}
