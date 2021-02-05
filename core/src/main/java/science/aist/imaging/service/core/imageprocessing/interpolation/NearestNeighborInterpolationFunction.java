/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.interpolation;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;

import java.util.Arrays;

/**
 * <p>Nearest Neighbor Interpolation for a single pixel on an image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class NearestNeighborInterpolationFunction extends AbstractInterpolationFunction {
    /**
     * Checks for the allowed types of this class
     */
    private static final TypeChecker typeChecker = new TypeChecker(Arrays.asList(ChannelType.GREYSCALE, ChannelType.BINARY));

    public NearestNeighborInterpolationFunction(double backgroundColor) {
        super(backgroundColor);
    }

    @Override
    public Double apply(ImageWrapper<?> imageWrapper, JavaPoint2D javaPoint2D) {
        typeChecker.accept(imageWrapper);

        // nearest neighbour
        int xCoord = javaPoint2D.getIntX();
        int yCoord = javaPoint2D.getIntY();

        if (xCoord >= 0 && xCoord < imageWrapper.getWidth() && yCoord >= 0 && yCoord < imageWrapper.getHeight()) {
            return imageWrapper.getValue(xCoord, yCoord, 0);
        }

        return backgroundColor;
    }

}
