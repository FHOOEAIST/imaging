/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.objectdetection.AbstractFeaturebasedObjectDetector;
import science.aist.imaging.service.opencv.imageprocessing.conversion.OpenCVBGR2GrayscaleFunction;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVRectangleWrapper;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.Setter;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Feature2D;
import org.opencv.features2d.ORB;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>opencv implementation for a feature based Object Detector.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVFeaturebasedObjectDetector extends AbstractFeaturebasedObjectDetector<Mat, Point, Rect> {
    @Setter
    @NonNull
    private Feature2D detector = ORB.create();
    @Setter
    @NonNull
    private DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

    private Mat descriptors = null; // descriptors describing the KeyPoints of ObjectReferenceImage

    @Setter
    private OpenCVBGR2GrayscaleFunction grayscaleFunction; // grayscale function used for conversion

    @Override
    public void setObjectReferenceImage(ImageWrapper<Mat> objectReferenceImage) {
        this.objectReferenceImage = grayscaleFunction.apply(objectReferenceImage);

        // detect keypoints
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        detector.detect(objectReferenceImage.getImage(), keypoints);
        // get descriptors of keypoints
        descriptors = new Mat();
        detector.compute(objectReferenceImage.getImage(), keypoints, descriptors);
    }

    /**
     * Method for detecting an object.
     *
     * @param image Image where object should be detected
     * @return Returns the boundingbox of the detected object.
     */
    @Override
    @SuppressWarnings("java:S3047")
    public RectangleWrapper<Rect, Point> getBoundingBox(ImageWrapper<Mat> image) {
        if (this.objectReferenceImage == null)
            throw new IllegalStateException("No Reference Image of the object is set");

        // get greyscaled image
        @Cleanup("release") Mat grayscaleImg = grayscaleFunction.apply(image).getImage();

        @Cleanup("release") MatOfKeyPoint localKeypoints = new MatOfKeyPoint();
        @Cleanup("release") Mat localDescriptors = new Mat();
        @Cleanup("release") MatOfDMatch matches = new MatOfDMatch();

        // detect keypoints
        detector.detect(grayscaleImg, localKeypoints);

        // get descriptors of keypoints
        detector.compute(grayscaleImg, localKeypoints, localDescriptors);

        // Detect matches
        matcher.match(descriptors, localDescriptors, matches);
        List<DMatch> matchesList = matches.toList();

        // initialize max and min distances between matches
        double maxDistance = 0.0;
        double minDistance = 100.0;

        // find max/min distances
        for (DMatch aMatchesList : matchesList) {
            double dist = aMatchesList.distance;
            if (dist < minDistance)
                minDistance = dist;
            if (dist > maxDistance)
                maxDistance = dist;
        }

        // find fitting matches based on the max/min distances with threshold of 1.5
        List<DMatch> fittingMatches = new ArrayList<>();
        for (DMatch aMatchesList : matchesList) {
            if ((aMatchesList.distance >= minDistance * threshold) && (aMatchesList.distance <= maxDistance * (1.0 / threshold)))
                fittingMatches.add(aMatchesList);
        }

        // Check if at least x fitting matches were found
        // otherwise no match
        if (fittingMatches.size() >= minNumberMatchingFeatures) {
            // get the matched points
            List<KeyPoint> localKeypointsList = localKeypoints.toList();
            List<Point> matchedKeyPoints = new ArrayList<>();
            for (DMatch d : fittingMatches) {
                matchedKeyPoints.add(localKeypointsList.get(d.trainIdx).pt);
            }

            // get Boundingbox of the matchedKeyPoints
            MatOfPoint p = new MatOfPoint(matchedKeyPoints.toArray(new Point[0]));
            return new OpenCVRectangleWrapper(Imgproc.boundingRect(p));
        } else {
            return new OpenCVRectangleWrapper(0, 0, 0, 0);
        }
    }
}
