/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.optimization;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.featureextraction.AbstractEdgeFeatureDetection;
import science.aist.imaging.api.optimization.Optimizer;
import science.aist.imaging.service.opencv.imageprocessing.conversion.OpenCVBGR2GrayscaleFunction;
import science.aist.imaging.service.opencv.imageprocessing.distance.OpenCVDistanceMap;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVDistanceMask;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVDistanceType;
import science.aist.imaging.service.opencv.imageprocessing.edgedetection.OpenCVCannyEdgeDetection;
import science.aist.imaging.service.opencv.imageprocessing.threshold.OpenCVThresholdFunction;
import science.aist.imaging.service.opencv.imageprocessing.transformation.OpenCVPaddingFunction;
import lombok.Setter;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * <p>Implementation of the Optimizer Interface which is used to improve the quality and/or speed of image processing algorithms.</p>
 *
 * @author Christoph Praschl
 * @author Gerald Zwettler
 * @since 1.0
 */
@Setter
public class OpenCVOptimizer implements Optimizer<Mat> {

    private AbstractEdgeFeatureDetection<KeyPoint, Mat> extractor;
    private OpenCVCannyEdgeDetection edgeDetector;

    private OpenCVPaddingFunction paddingFunction;
    private OpenCVDistanceMap distanceMapfunction;
    private OpenCVBGR2GrayscaleFunction grayscaleFunction;
    private OpenCVThresholdFunction thresholdFunction;

    /**
     * Method for calculating the offset of two images in an optimized way.
     *
     * @param ref                      The image which is used for comparison.
     * @param current                  The image which should be compared with ref
     * @param numberOfIterations       Max. number of iterations which should be done.
     * @param positionalRadius         The search window for positional offset (look between -positionalRadius to +positionRadius. e.g. if image is translated max +/- 10 pixels on x-axis and y-axis)
     * @param rotationalRadius         The search window for rotational offset (look between -rotationalRadius to +rotationalRadius. e.g. if image is translated max +/- 2 degrees)
     * @param stepsOfInterestingPoints Defines each x points which are interesting for feature detection. E.q. with = 3 just every third point will be used as feature.
     * @param degressionRate           degression of positionalRadius and rotationalRadius per iteration
     * @param paddingFillColor         Color which is used for filling new pixels after padding.
     * @return Rotational and Translational offset between ref and current
     */
    public RotationOffset optimize(ImageWrapper<Mat> ref, ImageWrapper<Mat> current, int numberOfIterations, int positionalRadius, int rotationalRadius, int stepsOfInterestingPoints, double degressionRate, RGBColor paddingFillColor) {
        if (ref.getImage().width() != current.getImage().width())
            throw new IllegalArgumentException("Images must have same width!");
        if (ref.getImage().height() != current.getImage().height())
            throw new IllegalArgumentException("Images must have same height!");

        // calculate need padding
        double paddingValue = Double.max(ref.getImage().width(), ref.getImage().height()) / 2;

        // padding around ref image
        paddingFunction.setPaddings((int) paddingValue);
        paddingFunction.setColor(paddingFillColor);
        // get Distancemap of ref
        distanceMapfunction.setMask(OpenCVDistanceMask.MASK_5);
        distanceMapfunction.setType(OpenCVDistanceType.C);
        thresholdFunction.setThresh(150);

        try (ImageWrapper<Mat> paddedRef = paddingFunction.apply(ref);
             ImageWrapper<Mat> distanceMap = distanceMapfunction.apply(thresholdFunction.apply(grayscaleFunction.apply(paddedRef)));
             ImageWrapper<Mat> currentEdge = paddingFunction.apply(edgeDetector.apply(current))) {

            Mat image = distanceMap.getImage();

            // detect edges of current

            // get features of edges of current
            extractor.setStepsOfInterestingPoints(stepsOfInterestingPoints);
            FeatureWrapper<KeyPoint> features = extractor.getFeature(currentEdge, null);


            // prepare results
            AtomicInteger finalBestfailure = new AtomicInteger(Integer.MAX_VALUE);
            AtomicInteger finalX = new AtomicInteger(Integer.MAX_VALUE);
            AtomicInteger finalY = new AtomicInteger(Integer.MAX_VALUE);
            AtomicInteger finalRotation = new AtomicInteger(Integer.MAX_VALUE);
            AtomicInteger posRadius = new AtomicInteger(positionalRadius);
            AtomicInteger rotRadius = new AtomicInteger(rotationalRadius);


            for (int iter = 0; iter < numberOfIterations; iter++) {
                IntStream.rangeClosed(-positionalRadius, positionalRadius).parallel().anyMatch(xoffset -> {
                            for (int yoffset = -posRadius.get(); yoffset <= posRadius.get(); yoffset++) {
                                for (int rotationaloffset = -rotRadius.get(); rotationaloffset <= rotRadius.get(); rotationaloffset++) {
                                    int currentFailure = 0;
                                    // transform features
                                    Collection<KeyPoint> transformedCol = features.getTransformedFeatures(currentEdge.getImage().width(), currentEdge.getImage().height(), xoffset, yoffset, rotationaloffset);

                                    // iterate over all transformed feature points
                                    for (KeyPoint feature : transformedCol) {
                                        Point p = feature.pt;
                                        // get value of distance map (array contains only one value! so index 0 is ok for distance map) on the position of the keypoint
                                        double[] arr = image.get((int) (p.y), (int) (p.x));
                                        currentFailure += (int) arr[0];
                                    }

                                    // check if current Failure is smaller than best failure
                                    //   --> if so better result was found
                                    if (currentFailure < finalBestfailure.get()) {
                                        finalBestfailure.set(currentFailure);
                                        finalX.set(xoffset);
                                        finalY.set(yoffset);
                                        finalRotation.set(rotationaloffset);
                                        // break for-loop if failure == 0 --> best possible result found
                                        if (Math.abs(finalBestfailure.get()) < 0.00000000000001) return true;
                                    }
                                }  // rotationaloffset - for
                                // break for-loop if failure == 0 --> best possible result found
                                if (Math.abs(finalBestfailure.get()) < 0.00000000000001) return true;
                            }  // yoffset - for
                            return false;
                        }
                );
                positionalRadius *= degressionRate;
                rotationalRadius *= degressionRate;
                if (Math.abs(finalBestfailure.get()) < 0.00000000000001) break;
            }

            return new RotationOffset(finalX.get(), finalY.get(), finalBestfailure.get(), finalRotation.get());
        }

    }

    /**
     * This method optimizes the quality and/or the speed of image processing algorithms.
     *
     * @param ref     The image which is used for comparison.
     * @param current The image which should be compared with ref
     * @return Rotational and Translational offset between ref and current
     */
    @Override
    public RotationOffset optimize(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        int numberOfIterations = 1;
        int positionalRadius = 30;
        int rotationalRadius = 2;
        int stepsOfInterestingPoints = 3;
        double degressionRate = 0.75;// degression of startrange per iteration
        return optimize(ref, current, numberOfIterations, positionalRadius, rotationalRadius, stepsOfInterestingPoints, degressionRate, RGBColor.WHITE);
    }


    /**
     * Method for calculating the offset of two images.
     *
     * @param ref     The image which is used for comparison.
     * @param current The image which should be compared with ref
     * @return Rotational and Translational offset between ref and current
     */
    @Override
    public TranslationOffset optimizePositionalOffset(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        // opencv Method phaseCorrelate needs greyscale mats with floating point arrays! so conversion is needed
        // greyscale images
        ref = grayscaleFunction.apply(ref);
        current = grayscaleFunction.apply(current);
        // convert images
        MatOfFloat r = new MatOfFloat();
        ref.getImage().convertTo(r, CvType.CV_64FC1);
        MatOfFloat c = new MatOfFloat();
        current.getImage().convertTo(c, CvType.CV_64FC1);
        // do phase correlation
        Point res = Imgproc.phaseCorrelate(r, c);
        return new TranslationOffset(res.x, res.y);
    }
}
