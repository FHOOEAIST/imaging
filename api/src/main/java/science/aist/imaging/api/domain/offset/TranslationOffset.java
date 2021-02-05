/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.offset;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>Class for representing translation offset of two images in pixels.</p>
 * <p>Also contains a failure-value representing the correctness of the result.
 * <ul>
 * <li>Failure &lt; 0 means that correctness of the offsets was not determined</li>
 * <li>Failure = 0 means offset is accurate.</li>
 * <li>Failure &gt; 0 means offset is not total accurate (the higher this value, the less it is sure that the offset is correct).</li>
 * </ul>
 * @author Christoph Praschl
 * @since 1.0
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TranslationOffset implements Serializable {
    protected static final double EPSILON = 0.0000000001;
    protected double failure; // field representing the failure-value which represents the correctness of the offset result
    protected double xOffset; // pixel offset on x-axis
    protected double yOffset; // pixel offset on y-axis

    public TranslationOffset() {
    }

    public TranslationOffset(double xOffset, double yOffset) {
        this(xOffset, yOffset, Double.MIN_VALUE);
    }

    public TranslationOffset(double xOffset, double yOffset, double failure) {
        this.failure = failure;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Getter for failure-value which represents the correctness of the offset result
     *
     * @return failure
     */
    public double getFailure() {
        return failure;
    }

    /**
     * Setter for failure-value which represents the correctness of the offset result
     *
     * @param failure Value which should be set
     */
    public void setFailure(double failure) {
        this.failure = failure;
    }

    /**
     * Getter pixel offset on x-axis
     *
     * @return Returns the offset on x-axis
     */
    public double getXOffset() {
        return xOffset;
    }

    /**
     * Setter for  of x-axis offset
     *
     * @param xOffset Value which should be set
     */
    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * Getter pixel offset on y-axis
     *
     * @return Returns the offset on y-axis
     */
    public double getYOffset() {
        return yOffset;
    }

    /**
     * Setter for  of y-axis offset
     *
     * @param yOffset Value which should be set
     */
    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * Method which compares to doubles
     *
     * @param a First double to compare with second double
     * @param b Second double which should be compared
     * @return True if a == b; else false
     */
    protected boolean equalDoubles(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     * Method which compares value with Double.MAX_VALUE
     *
     * @param d double which should be compared
     * @return Returns true if d is equal to Double.MAX_Value
     */
    protected boolean equalToMaxValue(double d) {
        return equalDoubles(d, Double.MAX_VALUE);
    }

    /**
     * Method which compares value with Double.MIN_VALUE
     *
     * @param d double which should be compared
     * @return Returns true if d is equal to Double.MIN_VALUE
     */
    protected boolean equalToMinValue(double d) {
        return equalDoubles(d, Double.MIN_VALUE);
    }

    /**
     * toString Overload for TranslationOffset class
     * Printing the TranslationOffset with rounded values (6 digits after decimal point)
     * Displays ? for not set values. Excludes offset areas if no value in area is set
     *
     * @return the string representation of offset
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.CEILING);
        StringBuilder sb = new StringBuilder();

        // display failure
        if (equalToMinValue(failure)) {
            sb.append("[f: ?]");
        } else {
            sb.append("[f: ");
            sb.append(df.format(failure));
            sb.append("]");
        }
        // print x, y and rotational offset
        if (!equalToMaxValue(xOffset) || !equalToMaxValue(yOffset)) {
            sb.append(" - {");
            if (!equalToMaxValue(xOffset)) {
                sb.append("x: ").append(df.format(xOffset)).append("px");
            } else {
                sb.append("x: ?");
            }

            sb.append("; ");
            if (!equalToMaxValue(yOffset)) {
                sb.append("y: ").append(df.format(yOffset)).append("px");
            } else {
                sb.append("y: ?");
            }
            sb.append("}");
        }

        return sb.toString();
    }

}
