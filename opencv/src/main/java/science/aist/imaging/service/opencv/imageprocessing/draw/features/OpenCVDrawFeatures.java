/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.draw.features;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVFeatures2d;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;
import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.*;
import org.opencv.features2d.Features2d;

import java.util.function.BiConsumer;

/**
 * <p>Mat Consumer for drawing features</p>
 * <p>Draws features onto an given image with {@link org.opencv.imgproc.Imgproc#line(Mat, Point, Point, Scalar, int)}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVDrawFeatures implements BiConsumer<ImageWrapper<Mat>, FeatureWrapper<KeyPoint>> {

    private OpenCVScalarRGBColorTransformer colorTransformer = new OpenCVScalarRGBColorTransformer();

    /**
     * Color of keypoints.
     */
    private RGBColor color = RGBColor.BLACK;
    /**
     * Flags setting drawing features. Possible flags bit values are defined by DrawMatchesFlags. See details above in "drawMatches".
     */
    private OpenCVFeatures2d features2d = OpenCVFeatures2d.DRAW_RICH_KEYPOINTS;

    @Override
    public void accept(ImageWrapper<Mat> matImageWrapper, FeatureWrapper<KeyPoint> keyPointFeatureWrapper) {
        @Cleanup("release") MatOfKeyPoint matOfKey = new MatOfKeyPoint(keyPointFeatureWrapper.getFeatures().toArray(new KeyPoint[0]));
        @Cleanup("release") Mat res = new Mat();
        Scalar c = colorTransformer.transformTo(color);
        Features2d.drawKeypoints(matImageWrapper.getImage(), matOfKey, res, c, features2d.getFeatures2D());

        for (int x = 0; x < res.width(); x++) {
            for (int y = 0; y < res.height(); y++) {
                matImageWrapper.getImage().put(y, x, res.get(y, x));
            }
        }
    }
}
