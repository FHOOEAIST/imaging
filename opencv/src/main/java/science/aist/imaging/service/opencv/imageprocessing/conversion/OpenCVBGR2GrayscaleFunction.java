/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.conversion;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * <p>Imagefunction for greyscaling an opencv BGR image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVBGR2GrayscaleFunction implements ImageFunction<Mat, Mat> {
    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> matImageWrapper) {
        // get image from wrapper
        Mat src = matImageWrapper.getImage();
        // check if image is already greyscale or mat is not representing an image at all
        // greyscale image = mat with 1 channel
        // coloured image = mat with 3 or 4 channels (4th for transparency)
        if (src.channels() < 3 || src.channels() > 4) return matImageWrapper;
        // create new Mat as result of the greyscale method
        Mat res = new Mat(src.size(), src.type());
        // apply greyscale
        Imgproc.cvtColor(matImageWrapper.getImage(), res, Imgproc.COLOR_BGR2GRAY);
        return OpenCVFactory.getInstance().getImage(res, ChannelType.GREYSCALE);
    }
}
