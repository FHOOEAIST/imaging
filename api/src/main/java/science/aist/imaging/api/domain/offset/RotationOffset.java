/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.offset;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>Class for representing translation and rotational offset of two images.</p>
 * <p>Also contains a failure-value representing the correctness of the result.
 * <ul>
 * <li>Failure &lt; 0 means that correctness of the offsets was not determined</li>
 * <li>Failure = 0 means offset is accurate.</li>
 * <li>Failure &gt; 0 means offset is not total accurate (the higher this value, the less it is sure that the offset is correct).</li>
 * </ul>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RotationOffset extends TranslationOffset {
    protected double rotationalOffset; // rotationalOffset in degrees

    public RotationOffset() {
        super();
        rotationalOffset = Double.MAX_VALUE;
    }

    public RotationOffset(double rotationalOffset) {
        this.rotationalOffset = rotationalOffset;
    }

    public RotationOffset(double xOffset, double yOffset, double rotationalOffset) {
        super(xOffset, yOffset);
        this.rotationalOffset = rotationalOffset;
    }

    public RotationOffset(double xOffset, double yOffset, double failure, double rotationalOffset) {
        super(xOffset, yOffset, failure);
        checkRotationValue(rotationalOffset, "Rotational offset");
        this.rotationalOffset = rotationalOffset;
    }

    public RotationOffset(TranslationOffset offset, double rotationalOffset) {
        this(offset.xOffset, offset.yOffset, offset.failure, rotationalOffset);
    }

    /**
     * Getter of rotational offset (in degrees)
     *
     * @return Returns the rotational offset
     */
    public double getRotationalOffset() {
        return rotationalOffset;
    }

    /**
     * Setter for  of rotational offset (in degrees)
     *
     * @param rotationalOffset Value (in degrees) which should be set
     */
    public void setRotationalOffset(double rotationalOffset) {
        checkRotationValue(rotationalOffset, "Rotational offset");
        this.rotationalOffset = rotationalOffset;
    }


    protected void checkRotationValue(double rotation, String offsetName) {
        double absR = Math.abs(rotation);
        if (absR > 360 && absR < Double.MAX_VALUE)
            throw new IllegalArgumentException(offsetName + " can only be between 0 and +/-360. Or Double.MAX_Value to represent no offset is calculated. " + absR + " is not allowed.");
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
        if (!equalToMaxValue(xOffset) || !equalToMaxValue(yOffset) || !equalToMaxValue(rotationalOffset)) {
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

            sb.append("; ");
            if (!equalToMaxValue(rotationalOffset)) {
                sb.append("r: ").append(df.format(rotationalOffset)).append("°");
            } else {
                sb.append("r: ?");
            }
            sb.append("}");
        }

        return sb.toString();
    }

}
