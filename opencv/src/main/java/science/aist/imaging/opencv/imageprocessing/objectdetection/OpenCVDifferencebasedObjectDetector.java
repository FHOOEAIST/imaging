/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.objectdetection;

import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.api.objectdetection.AbstractDifferencebasedObjectDetector;
import science.aist.imaging.opencv.imageprocessing.contour.OpenCVBiggestContourFinder;


/**
 * <p>opencv implementation of a difference based Object Detector.
 * Detects Object based on differences between the reference image and the input image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVDifferencebasedObjectDetector extends AbstractDifferencebasedObjectDetector<Mat, Point, Rect> {
    /**
     * contour finder used to find biggest contour
     */
    private OpenCVBiggestContourFinder contourFinder;

    /**
     * Method for detecting an object based on the differences between the reference image and the input image. If no
     * reference image is set, first input image will be used as reference image.
     *
     * @param image Image where object should be detected
     * @return Returns the boundingbox of the detected object.
     */
    @Override
    public RectangleWrapper<Rect, Point> getBoundingBox(ImageWrapper<Mat> image) {
        if (referenceImage == null) {
            setReferenceImage(image);
        }
        if (referenceImage == null || referenceImage.getImage() == null)
            throw new IllegalArgumentException("Reference image does not wrap an image");

        @Cleanup("release") Mat img = new Mat();
        // find differences between ref image and input
        Core.absdiff(referenceImage.getImage(), image.getImage(), img);

        // process those differences
        Imgproc.threshold(img, img, 90, 255, Imgproc.THRESH_BINARY);
        if (image.getChannels() > 1) {
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        }
        Imgproc.threshold(img, img, 20, 255, Imgproc.THRESH_BINARY);

        // segment given color range
        Mat dst = new Mat();
        Core.inRange(img, new Scalar(240, 240, 240), new Scalar(255, 255, 255), dst);

        return contourFinder.apply(ImageFactoryFactory.getImageFactory(Mat.class).getImage(dst));
    }
}
