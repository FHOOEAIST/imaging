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
import science.aist.imaging.api.featureextraction.AbstractPointFeatureDetection;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVFeatureWrapper;
import lombok.CustomLog;
import lombok.NonNull;
import lombok.Setter;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.features2d.Feature2D;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>OpenCV Point Feature Detection</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
public class OpenCVPointFeatureDetection extends AbstractPointFeatureDetection<KeyPoint, Mat> {
    @Setter
    @NonNull
    private Feature2D featureDetector = FastFeatureDetector.create();

    /**
     * Method for computing feature of an image source.
     *
     * @param img                  is the source for which features should be extracted
     * @param additionalParameters additional parameters, which will be saved in a file and reloaded
     * @return A featurewrapper containing the computed features of the image.
     */
    @Override
    public FeatureWrapper<KeyPoint> getFeature(ImageWrapper<Mat> img, String additionalParameters) {
        //set the additional parameters, if given
        if (additionalParameters != null && !"".equals(additionalParameters)) {
            File outputDir = new File(System.getProperty("java.io.tmpdir"));
            try {
                File settingsFile = File.createTempFile("orbDetectorParams", ".YAML", outputDir);

                try (FileOutputStream stream = new FileOutputStream(settingsFile);
                     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
                    outputStreamWriter.write(additionalParameters);
                }

                //read the parameters out of the file we just created
                featureDetector.read(settingsFile.getPath());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        // Prepare a MatOfKeyPoints for result
        MatOfKeyPoint tempRes = new MatOfKeyPoint();
        // detect the features and save them in tempRes
        featureDetector.detect(img.getImage(), tempRes);
        // create new FeatureWrapper and return it
        return new OpenCVFeatureWrapper(tempRes.toList());
    }

    @Override
    public FeatureWrapper<KeyPoint> getFeature(ImageWrapper<Mat> img, String additionalParameters, int bestX) {
        FeatureWrapper<KeyPoint> wrapper = getFeature(img, additionalParameters);

        //get the best x features after checking, that we even have enough features
        if (bestX < wrapper.getFeatures().size()) {
            List<KeyPoint> features = new ArrayList<>(wrapper.getFeatures());

            //sort the features, best features coming first
            // Sort them in descending order, so the best response KPs will come first
            features.sort((kp1, kp2) -> (int) (kp2.response - kp1.response));
            //
            return new OpenCVFeatureWrapper(features.subList(0, bestX));
        }
        return wrapper;
    }
}
