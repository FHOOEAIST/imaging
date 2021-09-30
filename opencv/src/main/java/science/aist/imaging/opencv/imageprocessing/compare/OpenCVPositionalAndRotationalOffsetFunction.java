/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.optimization.Optimizer;
import lombok.Setter;
import org.opencv.core.Mat;

import java.util.function.BiFunction;

/**
 * <p>Method for calculating the positional and rotational offset of two images.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVPositionalAndRotationalOffsetFunction implements BiFunction<ImageWrapper<Mat>, ImageWrapper<Mat>, RotationOffset> {
    /**
     * optimizer used for calculation
     */
    private Optimizer<Mat> optimizer;

    /**
     * Max. number of iterations which should be done. (Default: 1)
     */
    private int numberOfIterations = 1;

    /**
     * Search window radius for positional offset (Default: 30)
     */
    private int positionalRadius = 30;

    /**
     * Search window radius for rotational offset (Default: 2)
     */
    private int rotationalRadius = 2;

    /**
     * Defines each x points which are interesting for feature detection. E.q. with = 3 just every third point will be used as feature. (Default: 3)
     */
    private int stepsOfInterestingPoints = 3;
    /**
     * degression of positionalRadius and rotationalRadius per iteration (Default: 0.75)
     */
    private double degressionRate = 0.75;

    /**
     * Color which is used for filling new pixels after padding. (Default: {@link RGBColor#WHITE}
     */
    private RGBColor color = RGBColor.WHITE;

    @Override
    public RotationOffset apply(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        return optimizer.optimize(ref, current, numberOfIterations, positionalRadius, rotationalRadius, stepsOfInterestingPoints, degressionRate, color);
    }
}
