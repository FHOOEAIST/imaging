/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * <p>Class giving data regarding a recognized object in an image.</p>
 * <p>Contains data, at which coordinates the object was found, in which image it was found and which
 * threshold was used for calculation.</p>
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RecognizedObject<T, V> {
    /**
     * Id of the object in the current image.
     * Set it to -1 by default, so we know if we have set an id yet or not.
     */
    private int id = -1;

    /**
     * Data in which image the object was found
     */
    private T fromImage;

    /**
     * Coordinates, which specify the pixels belonging to the object
     */
    private List<JavaPoint2D> coordinates;

    /**
     * Threshold that has been used to find the object
     */
    private double thresholdUsed;

    /**
     * Flag if we recognized this object as a human
     */
    private boolean isHuman;

    /**
     * Value defining information over the entire object. Depends on the type of image.
     * E.g. could be average depth of the object, average greyscale value, ...
     */
    private V value;

    public RecognizedObject(T fromImage, List<JavaPoint2D> coordinates, double thresholdUsed) {
        this.fromImage = fromImage;
        this.coordinates = coordinates;
        this.thresholdUsed = thresholdUsed;
    }

    /**
     * Calculates most upper left point to create a bounding box around the object.
     *
     * @return JavaPoint with the most upper left point
     */
    public JavaPoint2D calculateUpperRightBoundingBox() {
        if (coordinates.isEmpty()) {
            return null;
        }

        int upper = -1;
        int right = -1;

        for (JavaPoint2D coordinate : coordinates) {
            if (upper == -1 || coordinate.getY() > upper) {
                upper = coordinate.getIntY();
            }
            if (right == -1 || coordinate.getX() > right) {
                right = coordinate.getIntX();
            }
        }

        return new JavaPoint2D(right, upper);
    }

    /**
     * Calculates most lower right point to create a bounding box around the object.
     *
     * @return JavaPoint with the most lower right point
     */
    public JavaPoint2D calculateLowerLeftBoundingBox() {
        if (coordinates.isEmpty()) {
            return null;
        }

        int lower = -1;
        int left = -1;

        for (JavaPoint2D coordinate : coordinates) {
            if (lower == -1 || coordinate.getY() < lower) {
                lower = coordinate.getIntY();
            }
            if (left == -1 || coordinate.getX() < left) {
                left = coordinate.getIntX();
            }
        }

        return new JavaPoint2D(left, lower);
    }
}
