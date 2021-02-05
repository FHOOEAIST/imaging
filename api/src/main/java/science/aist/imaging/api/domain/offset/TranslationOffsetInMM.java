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
 * <p>Description: Class for representing translation offset of two images in pixels and millimeters.</p>
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
public class TranslationOffsetInMM extends TranslationOffset {
    protected double xOffsetInMM; // mm offset on x-axis
    protected double yOffsetInMM; // mm offset on y-axis

    private double ratio;

    protected TranslationOffsetInMM() {
    }

    public TranslationOffsetInMM(double xOffset, double yOffset, double xOffsetInMM, double yOffsetInMM) {
        super(xOffset, yOffset);
        this.ratio = xOffsetInMM / xOffset;
        this.xOffsetInMM = xOffsetInMM;
        this.yOffsetInMM = yOffsetInMM;
    }

    /**
     * Method for creating a TranslationOffsetInMM Object
     *
     * @param offset The pixel based Offset
     * @param ratio  The ratio between width or height in pixels to width in millimeters
     * @return Returns new TranslationOffsetInMM object.
     */
    public static TranslationOffsetInMM create(TranslationOffset offset, double ratio) {
        TranslationOffsetInMM res = new TranslationOffsetInMM();
        res.ratio = ratio;
        res.xOffset = offset.xOffset;
        res.yOffset = offset.yOffset;
        res.failure = offset.failure;
        res.xOffsetInMM = ratio * offset.xOffset;
        res.yOffsetInMM = ratio * offset.yOffset;
        return res;
    }

    /**
     * Method for creating a TranslationOffsetInMM Object
     * (Dimension parameters must  represent both the height or both the width!)
     *
     * @param offset           The pixel based Offset
     * @param dimensionInPixel The height/width in pixels
     * @param dimensionInMM    The height/width in millimeters
     * @return Returns new TranslationOffsetInMM object.
     */
    public static TranslationOffsetInMM create(TranslationOffset offset, double dimensionInPixel, double dimensionInMM) {
        return create(offset, dimensionInMM / dimensionInPixel);
    }

    /**
     * Setter for  of x-axis offset
     * (Also updates xOffsetInMM!)
     *
     * @param xOffset Value which should be set
     */
    @Override
    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
        this.xOffsetInMM = ratio * xOffset;
    }

    /**
     * Setter for  of y-axis offset
     * (Also updates yOffsetInMM!)
     *
     * @param yOffset Value which should be set
     */
    @Override
    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
        this.yOffsetInMM = ratio * yOffset;
    }

    /**
     * Getter for mm offset on x-axis
     *
     * @return Returns the offset on x-axis
     */
    public double getxOffsetInMM() {
        return xOffsetInMM;
    }

    /**
     * Setter for  of x-axis offset
     * (Also updates xOffset!)
     *
     * @param xOffsetInMM Value which should be set
     */
    public void setxOffsetInMM(double xOffsetInMM) {
        this.xOffsetInMM = xOffsetInMM;
        this.xOffset = xOffsetInMM / ratio;
    }

    /**
     * Getter for mm offset on y-axis
     *
     * @return Returns the offset on y-axis
     */
    public double getyOffsetInMM() {
        return yOffsetInMM;
    }

    /**
     * Setter for  of y-axis offset
     * (Also updates yOffset!)
     *
     * @param yOffsetInMM Value which should be set
     */
    public void setyOffsetInMM(double yOffsetInMM) {
        this.yOffsetInMM = yOffsetInMM;
        this.yOffset = yOffsetInMM / ratio;
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

        sb.append(" - {");
        if (!equalToMaxValue(xOffset)) {
            sb.append("x: ").append(df.format(xOffset)).append("px");
            sb.append(" | ").append(df.format(xOffsetInMM)).append("mm");
        } else {
            sb.append("x: ?");
        }

        sb.append("; ");
        if (!equalToMaxValue(yOffset)) {
            sb.append("y: ").append(df.format(yOffset)).append("px");
            sb.append(" | ").append(df.format(yOffsetInMM)).append("mm");
        } else {
            sb.append("y: ?");
        }
        sb.append("}");

        return sb.toString();
    }
}
