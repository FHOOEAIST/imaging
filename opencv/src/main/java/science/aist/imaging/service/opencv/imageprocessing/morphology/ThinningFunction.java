/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.morphology;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.domain.ThinningType;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.ximgproc.Ximgproc;

/**
 * <p>Functional implementation for Skeleton Calculation</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class ThinningFunction implements ImageFunction<Mat, Mat> {
    private ThinningType thinningType = ThinningType.ZHANGSUEN;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        ImageWrapper<Mat> result = OpenCVFactory.getInstance().getImage(matImageWrapper.getWidth(), matImageWrapper.getHeight(), matImageWrapper.getChannelType());
        Ximgproc.thinning(matImageWrapper.getImage(), result.getImage(), thinningType.getId());
        return result;
    }
}
