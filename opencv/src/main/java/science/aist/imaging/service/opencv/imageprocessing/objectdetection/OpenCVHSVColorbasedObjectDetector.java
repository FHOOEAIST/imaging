/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.color.HSVColor;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.objectdetection.AbstractColorbasedObjectDetector;
import science.aist.imaging.service.opencv.imageprocessing.contour.OpenCVBiggestContourFinder;
import science.aist.imaging.service.opencv.imageprocessing.conversion.OpenCVBGR2HSVFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.*;

/**
 * <p>Color-based Object Detector on HSV colored images.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVHSVColorbasedObjectDetector extends AbstractColorbasedObjectDetector<Mat, Point, Rect, HSVColor> {

    @Setter
    private OpenCVBiggestContourFinder biggestContourFinder;
    @Setter
    private OpenCVBGR2HSVFunction openCVBGR2HSVFunction;

    /**
     * Method for detecting an object.
     *
     * @param image Image where object should be detected
     * @return Returns the boundingbox of the detected object.
     */
    @Override
    public RectangleWrapper<Rect, Point> getBoundingBox(ImageWrapper<Mat> image) {
        @Cleanup ImageWrapper<Mat> hsvImage = openCVBGR2HSVFunction.apply(image);
        // prepare color segmentation with given color range
        // hue / 2.0 because opencv represents hsv with hue [0, 179]
        // saturation and value * 255 because opencv represents hsv with saturation/value [0, 255]
        Mat dst = new Mat();
        Scalar lowerb = new Scalar(lowerBound.getHue() / 2.0, lowerBound.getSaturation() * 255, lowerBound.getValue() * 255);
        Scalar upperb = new Scalar(upperBound.getHue() / 2.0, upperBound.getSaturation() * 255, upperBound.getValue() * 255);

        // segment given color range
        Core.inRange(hsvImage.getImage(), lowerb, upperb, dst);

        return biggestContourFinder.apply(OpenCVFactory.getInstance().getImage(dst));
    }
}
