/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.positioning;

/**
 * <p>Interface which provides functionality to evaluate e.g. the position of an object in the image
 * based on a given object size for calibration</p>
 *
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @author Christoph Praschl
 * @since 1.0
 */
public interface SizebasedPositionEvaluator<I, P> extends PositionEvaluator<I, P> {

    /**
     * @return (real) Object Width in mm
     */
    double getObjectWidthInMM();

    /**
     * @param objectWidthInMM (real) Object width in mm
     */
    void setObjectWidthInMM(double objectWidthInMM);
}
