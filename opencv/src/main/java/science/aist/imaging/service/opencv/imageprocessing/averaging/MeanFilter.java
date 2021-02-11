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
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.Collection;

/**
 * <p>Filter implementation for mean averaging</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class MeanFilter extends AbstractAveragingFilter {
    public MeanFilter() {
        averageType = AverageType.MEAN;
    }

    @Override
    protected ImageWrapper<Mat> averaging(Collection<ImageWrapper<Mat>> imageWrappers, ImageWrapper<Mat> firstMat) {
        Mat background = Mat.zeros(firstMat.getImage().size(), CvType.CV_32FC(firstMat.getChannels()));

        // sum up
        for (ImageWrapper<Mat> mat : imageWrappers) {
            for (int x = 0; x < firstMat.getWidth(); x++) {
                for (int y = 0; y < firstMat.getHeight(); y++) {
                    double[] doubles = mat.getImage().get(y, x);
                    double[] currentMean = background.get(y, x);
                    for (int c = 0; c < doubles.length; c++) {
                        currentMean[c] += doubles[c];
                    }
                    background.put(y, x, currentMean);
                }
            }
        }

        // average
        for (int x = 0; x < firstMat.getWidth(); x++) {
            for (int y = 0; y < firstMat.getHeight(); y++) {
                double[] currentMean = background.get(y, x);
                for (int c = 0; c < currentMean.length; c++) {
                    currentMean[c] /= imageWrappers.size();
                }
                background.put(y, x, currentMean);
            }
        }

        background.convertTo(background, firstMat.getImage().type());

        return TypeBasedImageFactoryFactory.getImageFactory(Mat.class).getImage(background);
    }
}
