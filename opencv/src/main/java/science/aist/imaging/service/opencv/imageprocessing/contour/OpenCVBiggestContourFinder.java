/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.service.opencv.imageprocessing.conversion.OpenCVBGR2GrayscaleFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVRectangleWrapper;
import lombok.Cleanup;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Method which finds the position of the biggest connected (white!) contour in the given image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVBiggestContourFinder implements Function<ImageWrapper<Mat>, RectangleWrapper<Rect, Point>> {
    /**
     * gray scale function used for gray scaling
     */
    private final OpenCVBGR2GrayscaleFunction grayscaleFunction = new OpenCVBGR2GrayscaleFunction();

    @Override
    public RectangleWrapper<Rect, Point> apply(ImageWrapper<Mat> matImageWrapper) {
        // prepare contour segmentation
        List<MatOfPoint> contours = new ArrayList<>();
        @Cleanup("release") Mat hierarchy = new Mat();

        // find contours
        Imgproc.findContours(grayscaleFunction.apply(matImageWrapper).getImage(), contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_TC89_KCOS);

        // prepare finding the biggest coherently contour/area
        double largestArea = 0;
        int largestContourIndex = -1;

        // find the biggest coherently contour/area
        for (int i = 0; i < contours.size(); i++) {
            // get the size of the biggest coherently contour/area
            double currentArea = Imgproc.contourArea(contours.get(i), false);
            // check if currentArea is bigger as previous largest area
            if (currentArea > largestArea) {
                largestArea = currentArea;
                largestContourIndex = i;
            }
        }


        if (largestContourIndex == -1) {
            return new OpenCVRectangleWrapper(0, 0, 0, 0);
        }

        // get the bounding rectangle for the currently biggest contour
        return new OpenCVRectangleWrapper(Imgproc.boundingRect(contours.get(largestContourIndex)));
    }
}
