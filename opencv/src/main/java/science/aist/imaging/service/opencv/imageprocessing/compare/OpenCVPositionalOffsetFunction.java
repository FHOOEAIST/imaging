/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.optimization.Optimizer;
import lombok.Setter;
import org.opencv.core.Mat;

import java.util.function.BiFunction;

/**
 * <p>Function for calculating the offset of two images using an optimizer.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVPositionalOffsetFunction implements BiFunction<ImageWrapper<Mat>, ImageWrapper<Mat>, TranslationOffset> {

    /**
     * optimizer used for calculation
     */
    private Optimizer<Mat> optimizer;

    @Override
    public TranslationOffset apply(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        return optimizer.optimizePositionalOffset(ref, current);
    }
}
