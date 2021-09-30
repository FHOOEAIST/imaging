/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.offset.OrientationOffset;
import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.optimization.Optimizer;
import lombok.Setter;
import org.opencv.core.Mat;

import java.util.function.BiFunction;

/**
 * <p>Function for calculating the orientation offset with the positional offset (by using the {@link OpenCVPositionalOffsetFunction} method)</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVOrientationOffsetFunction implements BiFunction<ImageWrapper<Mat>, ImageWrapper<Mat>, OrientationOffset> {
    /**
     * positionalOffsetFunction used for orientation offset calculation
     */
    private OpenCVPositionalOffsetFunction positionalOffsetFunction = new OpenCVPositionalOffsetFunction();
    /**
     * optimizer used for positional offset calculation
     */
    private Optimizer<Mat> optimizer;

    private double horizontalAngle;
    private double verticalAngle;

    @Override
    public OrientationOffset apply(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        if (optimizer != null) {
            positionalOffsetFunction.setOptimizer(optimizer);
        }
        TranslationOffset positionalOffset = positionalOffsetFunction.apply(ref, current);
        double resultHorizontalAngle = (horizontalAngle * positionalOffset.getXOffset()) / ref.getImage().width();
        double resultVerticalAngle = (verticalAngle * positionalOffset.getYOffset()) / ref.getImage().height();

        double absResultHorizontalAngle = Math.abs(resultHorizontalAngle);
        OrientationOffset result = new OrientationOffset(positionalOffset.getXOffset(), positionalOffset.getYOffset());

        if (!(absResultHorizontalAngle > 360 && absResultHorizontalAngle < Double.MAX_VALUE)) {
            result.setHorizontalAngleOffset(resultHorizontalAngle);
        }
        double absResultVerticalAngle = Math.abs(resultVerticalAngle);
        if (!(absResultVerticalAngle > 360 && absResultVerticalAngle < Double.MAX_VALUE)) {
            result.setVerticalAngleOffset(resultVerticalAngle);
        }

        return result;
    }
}
