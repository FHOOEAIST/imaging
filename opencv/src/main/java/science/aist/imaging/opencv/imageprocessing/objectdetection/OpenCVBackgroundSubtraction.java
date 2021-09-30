/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.objectdetection;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.opencv.imageprocessing.domain.OpenCVThresholdType;
import science.aist.imaging.opencv.imageprocessing.threshold.OpenCVThresholdFunction;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import science.aist.imaging.opencv.imageprocessing.averaging.AbstractAveragingFilter;

/**
 * <p>Function for removing the background for the given image based on the background model created with e.g. an {@link AbstractAveragingFilter}</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
public class OpenCVBackgroundSubtraction implements ImageFunction<Mat, Mat> {
    private ImageFunction<Mat, Mat> thresholdFunction;

    private ImageWrapper<Mat> model;
    private double hsvDistanceThreshold = 10.0;
    private double luvDistanceThreshold = 20.0;
    private double bgrDistanceThreshold = 60.0;

    public OpenCVBackgroundSubtraction() {
        OpenCVThresholdFunction thresholdFunctionInner = new OpenCVThresholdFunction();
        thresholdFunctionInner.setThresh(1);
        thresholdFunctionInner.setMaxval(255);
        thresholdFunctionInner.setType(OpenCVThresholdType.BINARY_INV);
        thresholdFunction = thresholdFunctionInner;
    }

    /**
     * @param model created with e.g. an {@link AbstractAveragingFilter}
     */
    public OpenCVBackgroundSubtraction(ImageWrapper<Mat> model) {
        this();
        this.model = model;
    }

    private static Mat partDistance(Mat model, Mat image, double v, ChannelType channelType) {
        Mat outModel = Mat.zeros(model.size(), model.type());
        Mat outImage = Mat.zeros(model.size(), model.type());
        int[] channels;

        if (channelType == ChannelType.HSV) {
            Imgproc.cvtColor(model, outModel, Imgproc.COLOR_BGR2HSV);
            Imgproc.cvtColor(image, outImage, Imgproc.COLOR_BGR2HSV);
            channels = new int[]{0};
        } else if (channelType == ChannelType.LUV) {
            Imgproc.cvtColor(model, outModel, Imgproc.COLOR_BGR2Luv);
            Imgproc.cvtColor(image, outImage, Imgproc.COLOR_BGR2Luv);
            channels = new int[]{1, 2};
        } else {
            model.copyTo(outModel);
            image.copyTo(outImage);
            channels = new int[]{0, 1, 2};
        }

        Mat mat = calcPartDistance(outModel, outImage, channels);
        outImage.release();
        outModel.release();

        Mat res = Mat.zeros(model.size(), CvType.CV_8UC1);
        for (int x = 0; x < model.width(); x++) {
            for (int y = 0; y < model.height(); y++) {
                double d = mat.get(y, x)[0];
                if (d <= v) {
                    res.put(y, x, 0);
                } else {
                    res.put(y, x, 1);
                }
            }
        }

        mat.release();

        return res;
    }

    private static Mat calcPartDistance(Mat model, Mat image, int[] channels) {
        Mat res = Mat.zeros(model.size(), CvType.CV_32FC1);
        for (int x = 0; x < model.width(); x++) {
            for (int y = 0; y < model.height(); y++) {
                double value = 0;
                for (int channel : channels) {
                    double v = model.get(y, x)[channel] - image.get(y, x)[channel];
                    value += v * v;
                }
                res.put(y, x, Math.sqrt(value));
            }
        }

        return res;
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> imageWrapper) {
        if (model == null) {
            throw new IllegalStateException("OpenCVBackgroundSubtraction.setModel() must be called before");
        }

        ChannelType channelType = imageWrapper.getChannelType();
        if (channelType != ChannelType.BGR) {
            throw new IllegalArgumentException("Image must be of type BGR");
        }

        Mat image = imageWrapper.getImage();

        Mat hsv = partDistance(model.getImage(), image, hsvDistanceThreshold, ChannelType.HSV);
        Mat luv = partDistance(model.getImage(), image, luvDistanceThreshold, ChannelType.LUV);
        Mat bgr = partDistance(model.getImage(), image, bgrDistanceThreshold, ChannelType.BGR);

        Mat sum = Mat.zeros(image.size(), CvType.CV_8UC1);
        Core.add(hsv, luv, sum);
        Core.add(sum, bgr, sum);

        hsv.release();
        luv.release();
        bgr.release();

        return thresholdFunction.apply(ImageFactoryFactory.getImageFactory(Mat.class).getImage(sum));
    }

}
