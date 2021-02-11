/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.objectdetection;

import lombok.Setter;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Abstract Object detector</p>
 *
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <R> Type of Rectangle wrapped by RectangleWrapper
 * @author Christoph Praschl
 * @since 1.0
 */
public abstract class AbstractObjectDetector<I, P, R> implements ObjectDetector<I, P, R> {
    /**
     * Reference to Point Transformer
     */
    @Setter
    protected Transformer<Point2Wrapper<P>, JavaPoint2D> pointTransformer;

    /**
     * Method for detecting an object with preferably unique color.
     * Detects the biggest object colored with a color between the given color range.
     *
     * @param image Image where object should be detected
     * @return Returns the center point of the detected object.
     */
    @Override
    public Point2Wrapper<P> getObjectCenter(ImageWrapper<I> image) {
        RectangleWrapper<R, P> bounding = getBoundingBox(image);
        if (Math.abs(bounding.getWidth()) < 0.000001 || Math.abs(bounding.getHeight()) < 0.000001)
            return pointTransformer.transformTo(new JavaPoint2D(-1, -1));
        return bounding.getCenterPoint();
    }
}
