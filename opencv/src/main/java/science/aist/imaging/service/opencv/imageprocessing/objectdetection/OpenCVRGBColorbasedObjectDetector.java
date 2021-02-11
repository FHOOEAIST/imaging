/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.api.objectdetection.AbstractColorbasedObjectDetector;
import science.aist.imaging.service.opencv.imageprocessing.contour.OpenCVBiggestContourFinder;
import lombok.Setter;
import org.opencv.core.*;

/**
 * <p>opencv implementation for a color based Object Detector.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVRGBColorbasedObjectDetector extends AbstractColorbasedObjectDetector<Mat, Point, Rect, RGBColor> {
    @Setter
    private OpenCVBiggestContourFinder contourFinder;

    /**
     * Method for detecting an object with preferably unique color.
     * Detects the biggest object colored with a color between the given color range.
     *
     * @param image Image where object should be detected
     * @return Returns the boundingbox of the detected object.
     */
    @Override
    public RectangleWrapper<Rect, Point> getBoundingBox(ImageWrapper<Mat> image) {
        // prepare color segmentation with given color range
        Mat dst = new Mat();
        Scalar lowerb = new Scalar(lowerBound.getBlue(), lowerBound.getGreen(), lowerBound.getRed());
        Scalar upperb = new Scalar(upperBound.getBlue(), upperBound.getGreen(), upperBound.getRed());

        // segment given color range
        Core.inRange(image.getImage(), lowerb, upperb, dst);

        return contourFinder.apply(ImageFactoryFactory.getImageFactory(Mat.class).getImage(dst));
    }
}
