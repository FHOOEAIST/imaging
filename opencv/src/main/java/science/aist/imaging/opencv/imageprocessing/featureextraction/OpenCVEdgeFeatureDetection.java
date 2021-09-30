/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.featureextraction;

import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.featureextraction.AbstractEdgeFeatureDetection;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVImageWrapperImage2ByteTransformer;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFeatureWrapper;
import lombok.Cleanup;
import lombok.Setter;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>OpenCV Edge Feature Detector</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVEdgeFeatureDetection extends AbstractEdgeFeatureDetection<KeyPoint, Mat> {

    @Setter
    private ImageFunction<Mat, Mat> edgeDetector;

    @Setter
    private OpenCVImageWrapperImage2ByteTransformer imageTransformer;

    private static int compare(KeyPoint kp1, KeyPoint kp2) {
        return (int) (kp2.response - kp1.response);
    }

    /**
     * Method for computing feature of an image source.
     *
     * @param img                  is the source for which features should be extracted
     * @param additionalParameters additional parameters for the detector
     * @return A featurewrapper containing the computed features of the image.
     */
    @Override
    public FeatureWrapper<KeyPoint> getFeature(ImageWrapper<Mat> img, String additionalParameters) {
        // get edges of image
        @Cleanup ImageWrapper<Mat> edges = edgeDetector.apply(img);

        // convert wrapper to JavaImage respectively to double[][]
        @Cleanup ImageWrapper<short[][][]> convertedImage = imageTransformer.transformFrom(edges);
        short[][][] image = convertedImage.getImage();

        Collection<KeyPoint> resCollection = new ArrayList<>();

        // find relevant points (white points) in a binary image  and add them to features
        double count = getStepsOfInterestingPoints();
        final double EPSILON = 0.000000000000001;
        for (int x = 0; x < convertedImage.getWidth(); x++) {
            for (int y = 0; y < convertedImage.getHeight(); y++) {
                if (Math.abs(image[y][x][0] - 255) < EPSILON) {
                    if (Math.abs(count % getStepsOfInterestingPoints()) < EPSILON)
                        resCollection.add(new KeyPoint(x, y, 1));
                    count++;
                }
            }
        }

        return new OpenCVFeatureWrapper(resCollection);
    }

    @Override
    public FeatureWrapper<KeyPoint> getFeature(ImageWrapper<Mat> img, String additionalParameters, int bestX) {
        FeatureWrapper<KeyPoint> wrapper = getFeature(img, additionalParameters);

        //get the best x features after checking, that we even have enough features
        if (bestX < wrapper.getFeatures().size()) {
            List<KeyPoint> features = new ArrayList<>(wrapper.getFeatures());

            //sort the features, best features coming first
            // Sort them in descending order, so the best response KPs will come first
            features.sort(OpenCVEdgeFeatureDetection::compare);
            //
            return new OpenCVFeatureWrapper(features.subList(0, bestX));
        }
        return wrapper;
    }
}
