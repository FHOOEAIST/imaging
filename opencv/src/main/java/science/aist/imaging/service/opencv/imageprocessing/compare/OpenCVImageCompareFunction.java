/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.compare;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.CustomLog;
import lombok.Setter;
import org.opencv.core.Mat;

import java.util.function.BiPredicate;

/**
 * <p>Compare function to check if two images are equal or not</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
@Setter
public class OpenCVImageCompareFunction implements BiPredicate<ImageWrapper<Mat>, ImageWrapper<Mat>> {

    /**
     * Colortolerance for double equality test (Default: 0.1)
     */
    private double colorTolerance = 0.1;

    @Override
    public boolean test(ImageWrapper<Mat> img, ImageWrapper<Mat> with) {
        // get the mat of both images
        Mat img1 = img.getImage();
        Mat img2 = with.getImage();
        // check if their height is equal
        if (img1.height() != img2.height()) {
            log.debug("not the same height");
            return false;
        }
        // check if their width is equal
        if (img1.width() != img2.width()) {
            log.debug("not the same width");
            return false;
        }
        // Iterate over each row

        for (int x = 0; x < img1.width(); x++) {
            // Iterate over each col
            for (int y = 0; y < img1.height(); y++) {
                // get the colors of the images
                double[] pix1 = img1.get(y, x);
                double[] pix2 = img2.get(y, x);
                // compare if they are the same
                if (pix1.length != pix2.length) {
                    log.debug("not the same color");
                    return false;
                }
                // iterate over the colors
                for (int t = 0; t < pix1.length; t++) {
                    // check if the double values are equal. double values sometimes have problems with rounding, so check if there are equal in a range.
                    if (Math.abs(pix1[t] - pix2[t]) > colorTolerance) {
                        log.debug("not the same color at position (" + x + " " + y + "): image1 (" + pix1[t] + ") vs image2(" + pix2[t] + ")");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
