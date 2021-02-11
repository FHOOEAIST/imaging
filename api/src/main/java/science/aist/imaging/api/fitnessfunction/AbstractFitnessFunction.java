/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.fitnessfunction;

import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.function.ToDoubleBiFunction;

/**
 * <p>Abstract implementation of a fitness function</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
@Setter
@Accessors(chain = true)
public abstract class AbstractFitnessFunction implements ToDoubleBiFunction<ImageWrapper<?>, ImageWrapper<?>> {
    private static final String ILLEGAL_ROI = "Illegal roi ";

    @Setter(AccessLevel.NONE)
    @Getter
    protected boolean isLowerErrorValueBetter;

    protected JavaRectangle2D roi;

    @Override
    public double applyAsDouble(ImageWrapper<?> imageWrapper, ImageWrapper<?> imageWrapper2) {
        if (imageWrapper.getWidth() != imageWrapper2.getWidth()) {
            log.info("Images are different in width: {} <> {}", imageWrapper.getWidth(), imageWrapper2.getWidth());
            throw new IllegalArgumentException("Images must have same width");
        }
        if (imageWrapper.getHeight() != imageWrapper2.getHeight()) {
            log.info("Images are different in height: {} <> {}", imageWrapper.getHeight(), imageWrapper2.getHeight());
            throw new IllegalArgumentException("Images must have same height");

        }

        int startX = 0;
        int startY = 0;
        int endX = imageWrapper.getWidth();
        int endY = imageWrapper.getHeight();

        if (roi != null) {
            startX = roi.getTopLeft().getIntX();
            startY = roi.getTopLeft().getIntY();
            endX = roi.getBottomRight().getIntX();
            endY = roi.getBottomRight().getIntX();
        }

        if (startX >= endX) {
            log.info(ILLEGAL_ROI + "(" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + ")");
            throw new IllegalArgumentException("Roi startX must be left endX");
        }

        if (startY >= endY) {
            log.info(ILLEGAL_ROI + "(" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + ")");
            throw new IllegalArgumentException("Roi startY must be left endX");
        }

        if (startX < 0) {
            log.info(ILLEGAL_ROI + "(" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + ")");
            throw new IllegalArgumentException("Roi startX must be >= 0");
        }

        if (startY < 0) {
            log.info(ILLEGAL_ROI + "(" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + ")");
            throw new IllegalArgumentException("Roi startY must be >= 0");
        }

        if (endX > imageWrapper.getWidth()) {
            log.info(ILLEGAL_ROI + "(" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + ")");
            throw new IllegalArgumentException("Roi startX must be <= width");
        }

        if (endY > imageWrapper.getHeight()) {
            log.info(ILLEGAL_ROI + "(" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + ")");
            throw new IllegalArgumentException("Roi startY must be <= height");
        }


        return applyFitness(imageWrapper, imageWrapper2, startX, startY, endX, endY, imageWrapper.getChannels());
    }

    /**
     * abstract method for applying the actual fitness implementation
     *
     * @param imageWrapper  image1 to compare
     * @param imageWrapper2 image2 to compare
     * @param startX        start x position of the region of interest
     * @param startY        start y position of the region of interest
     * @param endX          end x position of the region of interest
     * @param endY          end y position of the region of interest
     * @param channels      number of channels
     * @return fitnessvalue
     */
    protected abstract double applyFitness(ImageWrapper<?> imageWrapper, ImageWrapper<?> imageWrapper2, int startX, int startY, int endX, int endY, int channels);

    /**
     * @return the best possible fitness value
     */
    public abstract double getBestPossibleError();
}
