/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.wrapper;

import science.aist.imaging.api.domain.wrapper.AbstractFeatureWrapper;
import org.opencv.core.KeyPoint;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Implementation of the FeatureWrapper Interface for opencv</p>
 * @author Christoph Praschl
 * @since 1.0
 */
public class OpenCVFeatureWrapper extends AbstractFeatureWrapper<KeyPoint> {
    /**
     * Constructor for construction a FeatureWrapper of given Collection
     *
     * @param collection features which contains all features
     */
    public OpenCVFeatureWrapper(Collection<KeyPoint> collection) {
        super(collection);
    }

    /**
     * Method for transforming the contained feature points
     *
     * @param imageHeight      The height of the image from which the features were extracted.
     * @param imageWidth       The width of the image from which the features were extracted.
     * @param xOffset          The offset on the x-axis
     * @param yOffset          The offset on the y-axis
     * @param rotationalOffset The rotational offset.
     */
    @Override
    public Collection<KeyPoint> getTransformedFeatures(double imageWidth, double imageHeight, double xOffset, double yOffset, double rotationalOffset) {
        Collection<KeyPoint> temp = new ArrayList<>(features);
        Collection<KeyPoint> res = new ArrayList<>();
        //calc center position
        double midX = imageWidth / 2.0;
        double midY = imageHeight / 2.0;

        //getRotation angle degree ==> radian
        double radAngle = -rotationalOffset * Math.PI / 180.0;
        double cosTheta = Math.cos(radAngle);
        double sinTheta = Math.sin(radAngle);

        for (KeyPoint k : temp) {
            Point p = k.pt;
            double x = p.x;
            double y = p.y;
            //now calc x', y' to access values of inImg

            //1) move center for getRotation
            double posX = x - midX;
            double posY = y - midY;

            //2) rotate
            double newX = posX * cosTheta + posY * sinTheta;
            double newY = -posX * sinTheta + posY * cosTheta;

            //3) add and move back center
            posX = newX + midX;
            posY = newY + midY;

            //4) do translation
            posX -= xOffset;
            posY -= yOffset;

            res.add(new KeyPoint((float) posX, (float) posY, k.size, k.angle, k.response, k.octave, k.class_id));
        }

        return res;
    }
}
