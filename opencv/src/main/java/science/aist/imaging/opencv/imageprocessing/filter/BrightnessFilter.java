/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.filter;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFactory;

/**
 * <p>Imagefunction for changing the brightness of an image.</p>
 *
 * <p>alpha &lt; 1 --&gt; darkens image</p>
 * <p>alpha &gt; 1 --&gt; lightens image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
@Setter
public class BrightnessFilter implements ImageFunction<Mat, Mat> {
    /**
     * alpha &lt; 1 --&gt; darkens image
     * alpha = 0 --&gt; does not touch image
     * alpha &gt; 1 --&gt; lightens image
     */
    private double alpha;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        ImageWrapper<Mat> wrapper = ((OpenCVFactory) ImageFactoryFactory.getImageFactory(Mat.class)).getImage(matImageWrapper.getHeight(), matImageWrapper.getWidth(), matImageWrapper.getImage().type());

        @Cleanup("release") Mat m = new Mat(matImageWrapper.getHeight(), matImageWrapper.getWidth(), matImageWrapper.getImage().type(), new Scalar(0, 0, 0));
        double beta = (1.0 - alpha);
        Core.addWeighted(matImageWrapper.getImage(), alpha, m, beta, 0.0, wrapper.getImage());

        return wrapper;
    }
}
