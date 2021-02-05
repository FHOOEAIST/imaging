/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.transformation;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVBorderMode;
import science.aist.imaging.service.opencv.imageprocessing.domain.OpenCVInterpolationType;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVPoint2WrapperJavaPointTransformer;
import science.aist.imaging.service.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import lombok.CustomLog;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;


/**
 * <p>This function transforms a image in perspective. <br>
 * Therefore the topLeft is transformed to topLeftTarget <br>
 * topRight is transformed to topRightTarget <br>
 * bottomLeft is transformed to bottomLeftTarget <br>
 * bottomRight is transformed to bottomRightTarget</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
@CustomLog
public class OpenCVPerspectiveTransformation implements ImageFunction<Mat, Mat> {
    private final OpenCVPoint2WrapperJavaPointTransformer transformer = new OpenCVPoint2WrapperJavaPointTransformer();
    private final OpenCVScalarRGBColorTransformer colorTransformer = new OpenCVScalarRGBColorTransformer();
    private JavaPoint2D topLeft;
    private JavaPoint2D topRight;
    private JavaPoint2D bottomLeft;
    private JavaPoint2D bottomRight;
    private JavaPoint2D topLeftTarget;
    private JavaPoint2D topRightTarget;
    private JavaPoint2D bottomLeftTarget;
    private JavaPoint2D bottomRightTarget;
    private RGBColor borderValue = RGBColor.BLACK;
    private OpenCVInterpolationType interpolationType = OpenCVInterpolationType.INTER_LINEAR;
    private OpenCVBorderMode borderMode = OpenCVBorderMode.BORDER_CONSTANT;

    /**
     * @param borderMode pixel extrapolation method (BORDER_CONSTANT (default) or BORDER_REPLICATE).
     */
    public void setBorderMode(OpenCVBorderMode borderMode) {
        if (borderMode != OpenCVBorderMode.BORDER_CONSTANT && borderMode != OpenCVBorderMode.BORDER_REPLICATE)
            throw new IllegalArgumentException("Only OpenCVBorderMode.BORDER_CONSTANT and OpenCVBorderMode.BORDER_REPLICATE are allowed ");

        this.borderMode = borderMode;
    }

    @Override
    public ImageWrapper<Mat> apply(ImageWrapper<Mat> image) {
        Mat img = image.getImage();
        Mat res = new Mat();
        log.debug("performing perspective Transformation with points: " +
                "(" + topLeft.getX() + ", " + topLeft.getY() + "), " +
                "(" + topRight.getX() + ", " + topRight.getY() + "), " +
                "(" + bottomLeft.getX() + ", " + bottomLeft.getY() + "), " +
                "(" + bottomRight.getX() + ", " + bottomRight.getY() + ")" +
                " to " +
                "(" + topLeftTarget.getX() + ", " + topLeftTarget.getY() + "), " +
                "(" + topRightTarget.getX() + ", " + topRightTarget.getY() + "), " +
                "(" + bottomLeftTarget.getX() + ", " + bottomLeftTarget.getY() + "), " +
                "(" + bottomRightTarget.getX() + ", " + bottomRightTarget.getY() + ")"
        );
        // inputQuad
        MatOfPoint2f inputQuad = new MatOfPoint2f(
                transformer.transformTo(topLeft).getPoint(),
                transformer.transformTo(topRight).getPoint(),
                transformer.transformTo(bottomRight).getPoint(),
                transformer.transformTo(bottomLeft).getPoint()
        );
        // outputQuad
        MatOfPoint2f outputQuad = new MatOfPoint2f(
                transformer.transformTo(topLeftTarget).getPoint(),
                transformer.transformTo(topRightTarget).getPoint(),
                transformer.transformTo(bottomRightTarget).getPoint(),
                transformer.transformTo(bottomLeftTarget).getPoint()
        );
        Mat lambda = null;
        try {
            // Create the transformation matrix
            lambda = Imgproc.getPerspectiveTransform(inputQuad, outputQuad);
            // transform the image
            Imgproc.warpPerspective(img, res, lambda, res.size(), interpolationType.getInterpolationType(), borderMode.getBorderType(), colorTransformer.transformTo(borderValue));

            // return the result
            return OpenCVFactory.getInstance().getImage(res);
        } finally {
            inputQuad.release();
            outputQuad.release();
            if (lambda != null) {
                lambda.release();
            }
        }
    }
}
