/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.averaging;

import science.aist.imaging.api.domain.AverageType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Filter implementation for median averaging</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class MedianFilter extends AbstractAveragingFilter {
    public MedianFilter() {
        averageType = AverageType.MEDIAN;
    }

    @Override
    protected ImageWrapper<Mat> averaging(Collection<ImageWrapper<Mat>> imageWrappers, ImageWrapper<Mat> firstMat) {
        Mat background = Mat.zeros(firstMat.getImage().size(), CvType.CV_32FC(firstMat.getChannels()));
        for (int x = 0; x < firstMat.getWidth(); x++) {
            for (int y = 0; y < firstMat.getHeight(); y++) {
                double[] channels = new double[firstMat.getChannels()];
                for (int c = 0; c < firstMat.getChannels(); c++) {
                    List<Double> values = new ArrayList<>();
                    for (ImageWrapper<Mat> mat : imageWrappers) {
                        values.add(mat.getImage().get(y, x)[c]);
                    }
                    channels[c] = values.get(values.size() / 2);
                }

                background.put(y, x, channels);
            }
        }

        background.convertTo(background, firstMat.getImage().type());

        return OpenCVFactory.getInstance().getImage(background);
    }
}
