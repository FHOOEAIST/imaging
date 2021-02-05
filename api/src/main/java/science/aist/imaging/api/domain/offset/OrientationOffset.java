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
 * <p>Class for representing different offset types (e.g. x-Axis offset, y-Axis offset, rotational offset, ...) of two
 * images.</p>
 * <p>xOffset, yOffset, rotationOffset, horizontalAngleOffset and verticalAngleOffset == Double.MAX_Value means that the
 * respective offset was not calculated</p>
 * <p>
 * Also contains a failure-value representing the correctness of the result.
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
public class OrientationOffset extends RotationOffset {

    private double horizontalAngleOffset; // orientational offset on x-axis
    private double verticalAngleOffset;  // orientational offset on y-axis

    public OrientationOffset() {

        this(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE);
    }

    public OrientationOffset(double xOffset, double yOffset) {
        this(xOffset, yOffset, Double.MAX_VALUE, Double.MIN_VALUE);
    }

    public OrientationOffset(double xOffset, double yOffset, double rotationalOffset) {
        this(xOffset, yOffset, rotationalOffset, Double.MIN_VALUE);
    }

    public OrientationOffset(double xOffset, double yOffset, double rotationalOffset, double failure) {
        super(xOffset, yOffset, failure, rotationalOffset);
        horizontalAngleOffset = Double.MAX_VALUE;
        verticalAngleOffset = Double.MAX_VALUE;
    }

    public OrientationOffset(TranslationOffset offset, double rotation, double horizontalAngleOffset, double verticalAngleOffset) {
        super(offset, rotation);
        this.horizontalAngleOffset = horizontalAngleOffset;
        this.verticalAngleOffset = verticalAngleOffset;
    }

    public OrientationOffset(RotationOffset offset, double horizontalAngleOffset, double verticalAngleOffset) {
        super(offset, offset.rotationalOffset);
        this.horizontalAngleOffset = horizontalAngleOffset;
        this.verticalAngleOffset = verticalAngleOffset;
    }


    /**
     * Getter of orientational offset on x-axis (in degrees)
     *
     * @return Returns the orientational offset on x-axis (in degrees)
     */
    public double getHorizontalAngleOffset() {
        return horizontalAngleOffset;
    }

    /**
     * Setter for orientational offset on x-axis (in degrees)
     *
     * @param horizontalAngleOffset Value (in degrees) which should be set
     */
    public void setHorizontalAngleOffset(double horizontalAngleOffset) {
        checkRotationValue(horizontalAngleOffset, "Orientational (horizontal Angle) offset");
        this.horizontalAngleOffset = horizontalAngleOffset;
    }

    /**
     * Getter of orientational offset on y-axis (in degrees)
     *
     * @return Returns the orientational offset on y-axis (in degrees)
     */
    public double getVerticalAngleOffset() {
        return verticalAngleOffset;
    }

    /**
     * Setter for orientational offset on y-axis (in degrees)
     *
     * @param verticalAngleOffset Value (in degrees) which should be set
     */
    public void setVerticalAngleOffset(double verticalAngleOffset) {
        checkRotationValue(verticalAngleOffset, "Orientational (vertical Angle) offset");
        this.verticalAngleOffset = verticalAngleOffset;
    }

    /**
     * toString Overload for OrientationOffset class Printing the OrientationOffset with rounded values (6 digits after
     * decimal point) Displays ? for not set values. Excludes offset areas if no value in area is set (transformation
     * area with x-, y- and rotational offset; and orientational area with vertical and horizontal offset)
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

        // print horizontal and vertical offset
        if (!equalToMaxValue(horizontalAngleOffset) || !equalToMaxValue(verticalAngleOffset)) {
            sb.append(" - {");
            if (!equalToMaxValue(horizontalAngleOffset)) {
                sb.append("h: ").append(df.format(horizontalAngleOffset)).append("°");
            } else {
                sb.append("h: ?");
            }
            sb.append("; ");
            if (!equalToMaxValue(verticalAngleOffset)) {
                sb.append("v: ").append(df.format(verticalAngleOffset)).append("°");
            } else {
                sb.append("v: ?");
            }
            sb.append("}");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrientationOffset) {
            OrientationOffset o = (OrientationOffset) obj;
            return equalDoubles(xOffset, o.getXOffset()) &&
                    equalDoubles(yOffset, o.getYOffset()) &&
                    equalDoubles(verticalAngleOffset, o.getVerticalAngleOffset()) &&
                    equalDoubles(horizontalAngleOffset, o.getHorizontalAngleOffset()) &&
                    equalDoubles(rotationalOffset, o.getRotationalOffset()) &&
                    equalDoubles(failure, o.getFailure());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + (int) xOffset;
        hash = hash * 31 + (int) yOffset;
        hash = hash * 7 + (int) verticalAngleOffset;
        hash = hash * 3 + (int) horizontalAngleOffset;
        hash = hash * 11 + (int) rotationalOffset;
        hash = hash * 29 + (int) failure;
        return hash;
    }

}
