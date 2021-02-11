/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contrast;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Setter;
import org.opencv.core.Mat;

/**
 * <p>increase/decrease the contrast of an image
 * factor &gt; 1 increase the contrast. factor &lt; 1 decrease the contrast
 * http://shubhamagrawal.com/opencv/opencv-playing-with-brightness-contrast-histogram-blurness/</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVContrastFunction implements ImageFunction<Mat, Mat> {
    /**
     * factor > 1 increase the contrast. factor <1 decrease the contrast
     */
    private double factor;

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> image) {
        Mat m = image.getImage();
        Mat newM = new Mat();
        m.convertTo(newM, -1, factor);
        return ((OpenCVFactory)TypeBasedImageFactoryFactory.getImageFactory(Mat.class)).getImage(newM, image.getChannelType());
    }
}
