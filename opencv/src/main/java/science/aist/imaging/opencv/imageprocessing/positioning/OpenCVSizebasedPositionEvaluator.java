/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.positioning;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.offset.TranslationOffsetInMM;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.objectdetection.AbstractColorbasedObjectDetector;
import science.aist.imaging.api.positioning.SizebasedPositionEvaluator;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>PositionEvaluator Implementation (based on a AbstractColorbasedObjectDetector&lt;Mat, Point&gt;)
 * which provides functionality to evaluate e.g. the position of an object in the image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVSizebasedPositionEvaluator implements SizebasedPositionEvaluator<Mat, Point> {
    @Getter
    @Setter
    private double objectWidthInMM = 10;
    @Setter
    @Getter(AccessLevel.PACKAGE)
    private AbstractColorbasedObjectDetector<Mat, Point, Rect, RGBColor> objectDetector;
    @Setter
    private Transformer<ImageWrapper<Mat>, ImageWrapper<short[][][]>> image2ByteTransformer;
    @Setter
    private ImageFunction<short[][][], short[][][]> threshold;
    @Setter
    private ImageFunction<short[][][], short[][][]> toGreyscale;

    /**
     * Method for calibrating the PositionEvaluator. Needs an image containing the object and a white background.
     *
     * @param imageForCalibration Image for calibration containing the object on a white background.
     */
    @Override
    public void calibrate(ImageWrapper<Mat> imageForCalibration) {
        ImageWrapper<short[][][]> ji = image2ByteTransformer.transformFrom(imageForCalibration);

        try (ImageWrapper<Mat> image = toGreyscale
                .andThen(ImageFunction.closeAfterApply(threshold))
                .andThen(ImageFunction.closeAfterApply(image2ByteTransformer::transformTo))
                .apply(ji)) {

            objectDetector.setLowerBound(RGBColor.BLACK);
            objectDetector.setUpperBound(RGBColor.BLACK);
            Point2Wrapper<Point> center = objectDetector.getObjectCenter(image);

            short[] color = ji.getImage()[(int) center.getY()][(int) center.getX()];
            RGBColor centerColor = new RGBColor(color[2], color[1], color[0]);

            RGBColor lowerBound = RGBColor.darken(centerColor, 0.3f);
            RGBColor upperBound = RGBColor.lighten(centerColor, 0.3f);

            objectDetector.setLowerBound(lowerBound);
            objectDetector.setUpperBound(upperBound);
        }
    }

    /**
     * Method for evaluating e.g. the position of an object in the image (Note: Before using method calibrate())
     *
     * @param image Image where Object should be found.
     * @return Position of the Object in the image (Position -1/-1 -&gt; no position found)
     */
    @Override
    public Point2Wrapper<Point> getPosition(ImageWrapper<Mat> image) {
        return objectDetector.getObjectCenter(image);
    }

    /**
     * Method for evaluating the offset of an object between two images (Note: Before using method calibrate() and
     * setObjectDimensionInMM() !)
     *
     * @param ref     The reference image containing the object
     * @param current The current image containing the object
     * @return The offset between the object´s position in ref and the object´s position in current
     */
    @Override
    public TranslationOffsetInMM getOffset(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        RectangleWrapper<Rect, Point> refBoundingBox = objectDetector.getBoundingBox(ref);
        Point2Wrapper<Point> centerCur = getPosition(current);
        TranslationOffset offset = new TranslationOffset(centerCur.getX() - refBoundingBox.getCenterPoint().getX(), centerCur.getY() - refBoundingBox.getCenterPoint().getY());
        return TranslationOffsetInMM.create(offset, refBoundingBox.getWidth(), objectWidthInMM);
    }
}
