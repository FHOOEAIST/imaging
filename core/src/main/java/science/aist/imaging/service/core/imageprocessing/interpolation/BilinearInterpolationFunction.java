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
 * <p>Bilinear Interpolation for a single pixel on a image</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class BilinearInterpolationFunction extends AbstractInterpolationFunction {
    /**
     * Checks for the allowed types of this class
     */
    private static final TypeChecker typeChecker = new TypeChecker(Arrays.asList(ChannelType.GREYSCALE, ChannelType.BINARY));

    public BilinearInterpolationFunction(double backgroundColor) {
        super(backgroundColor);
    }

    @Override
    public Double apply(ImageWrapper<?> imageWrapper, JavaPoint2D javaPoint2D) {
        typeChecker.accept(imageWrapper);

        double idxX = javaPoint2D.getX();
        double idxY = javaPoint2D.getY();

        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();

        int x1 = (int) idxX;
        int y1 = (int) idxY;
        int x2 = x1 + 1;
        int y2 = y1 + 1;

        if ((x1 >= 0) && (y1 >= 0) && (x1 < width) && (y1 < height)) {
            if ((x2 >= 0) && (y2 >= 0) && (x2 < width) && (y2 < height)) {
                //do bilinear interpolate
                double interX = idxX - x1;
                double interY = idxY - y1;
                double valAtPos1 = imageWrapper.getValue(x1, y1, 0);
                double valAtPos2 = imageWrapper.getValue(x1, y2, 0);
                double valAtPos3 = imageWrapper.getValue(x2, y2, 0);
                double valAtPos4 = imageWrapper.getValue(x2, y1, 0);

                double val1 = valAtPos1 * (1.0 - interX) + valAtPos4 * interX;
                double val2 = valAtPos2 * (1.0 - interX) + valAtPos3 * interX;

                return val1 * (1.0 - interY) + val2 * interY;

            } else {
                //NN as fallback
                return imageWrapper.getValue(x1, y1, 0);
            } //else
        } else {
            return backgroundColor; // return background color
        } //else ==> outside mask
    }
}
