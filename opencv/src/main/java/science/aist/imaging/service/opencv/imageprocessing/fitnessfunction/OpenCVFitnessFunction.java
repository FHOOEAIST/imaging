/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.fitnessfunction;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.wrapper.FeatureWrapper;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.FitnessFunction;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

/**
 * <p>Implementation of the Fitnessfunction interface which provides metrics for example `SumOfSquareDifferences` to compute the similarity of two images.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVFitnessFunction implements FitnessFunction<KeyPoint, Mat> {

    /**
     * Method to calculate a long representation of a color array
     *
     * @param color Color array which should be converted
     * @return The long representation of the given color
     */
    private static long convertColorToLong(double[] color) {
        long ignoredValue = 0;
        for (int i = 0; i < color.length; i++) {
            ignoredValue += color[i] * Math.pow(1000, i);
        }
        return ignoredValue;
    }

    /**
     * This function is internal used to calculate similarity of two images
     *
     * @param a            The image which should be compared with b.
     * @param b            The image which is used for comparison.
     * @param dataToIgnore Data which should be ignored fo fitness calculation (e.g. Background color of an image). Can be null. Then no data will be ignored.
     * @return Returns a metrics representing the similarity of a and b.
     * (0 = no differences)
     */
    private static double getFitness(Mat a, Mat b, RGBColor dataToIgnore) {
        double ssd = 0;

        // check preconditions (same height, same number of channels, etc.)
        if (a.height() != b.height()) throw new IllegalArgumentException("a and b do not have the same height!");
        if (a.width() != b.width()) throw new IllegalArgumentException("a and b do not have the same width!");
        if (a.channels() != b.channels())
            throw new IllegalArgumentException("a and b do not have the same number of channels!");
        // calculate ignoredColor
        long ignoredColor = 0;
        if (dataToIgnore != null) {
            double[] dataToIgnoreArray;
            // if image has 3 channels put each channel of the RGB object and put it into the array
            // else image has 1 channel so calculate the average of the three RGB channels to get the greyscale value
            if (a.channels() == 3) {
                dataToIgnoreArray = new double[]{dataToIgnore.getBlue(), dataToIgnore.getGreen(), dataToIgnore.getRed()};
            } else {
                double greyScaledRGB = (dataToIgnore.getBlue() + dataToIgnore.getGreen() + dataToIgnore.getRed()) / 3;
                dataToIgnoreArray = new double[]{greyScaledRGB};
            }
            ignoredColor = convertColorToLong(dataToIgnoreArray);
        }

        int count = 0;
        // calculate ssd
        for (int i = 0; i < a.height(); i++) {
            for (int j = 0; j < a.width(); j++) {
                // only calculate ssd if there is no data which should be ignored
                // respectively the current data/color does not equal the data to be ignored
                if (dataToIgnore == null || convertColorToLong(a.get(i, j)) != ignoredColor) {
                    for (int k = 0; k < a.get(i, j).length; k++) {
                        double diff = a.get(i, j)[k] - b.get(i, j)[k];
                        ssd += diff * diff;
                        count++;
                    }
                }
            }
        }
        // calculate norm factor for ssd
        double normFactor = (double) count / (a.height() * a.width());

        if (Math.abs(normFactor) < 0.000000000001 || normFactor == 0) return ssd;
        return ssd / normFactor;
    }

    /**
     * This function is used to calculate similarity of two images.
     *
     * @param a            The feature which should be compared with b.
     * @param b            The feature which is used for comparison.
     * @param dataToIgnore Data which should be ignored fo fitness calculation (e.g. Background color of an image). Can be null. Then no data will be ignored.
     * @return Returns a metrics representing the difference of a and b.
     * (0 = no differences)
     */
    @Override
    public double getFitness(ImageWrapper<Mat> a, ImageWrapper<Mat> b, RGBColor dataToIgnore) {
        return getFitness(a.getImage(), b.getImage(), dataToIgnore);
    }

    /**
     * This function is used to calculate similarity of two images
     *
     * @param a The image which should be compared with b.
     * @param b The image which is used for comparison.
     * @return Returns a metrics representing the difference of a and b.
     * (0 = no differences)
     */
    @Override
    public double getFitness(ImageWrapper<Mat> a, ImageWrapper<Mat> b) {
        return getFitness(a.getImage(), b.getImage(), null);
    }

    /**
     * This function is used to calculate similarity of two features.
     *
     * @param a The feature which should be compared with b.
     * @param b The feature which is used for comparison.
     * @return Returns a metrics representing the difference of a and b.
     * (0 = no differences)
     */
    @Override
    public double getFitness(FeatureWrapper<KeyPoint> a, FeatureWrapper<KeyPoint> b) {
        KeyPoint[] arrayForConverting = new KeyPoint[0];
        MatOfKeyPoint matA = new MatOfKeyPoint(a.getFeatures().toArray(arrayForConverting));
        MatOfKeyPoint matB = new MatOfKeyPoint(b.getFeatures().toArray(arrayForConverting));

        return getFitness(matA, matB, null);
    }

}
