/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.transformation;

import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.service.core.imageprocessing.interpolation.AbstractInterpolationFunction;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.function.BiFunction;

/**
 * <p>Transforms the object</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@AllArgsConstructor
public class TransformFunction<T> implements BiFunction<ImageWrapper<?>, RotationOffset, ImageWrapper<T>> {
    /**
     * the allowed types for this function
     */
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE, ChannelType.BINARY);

    /**
     * The interpolation
     */
    @NonNull
    private final AbstractInterpolationFunction interpolation;

    @NonNull
    private final ImageFactory<T> provider;

    @Override
    public ImageWrapper<T> apply(ImageWrapper<?> imageWrapper, RotationOffset offset) {
        typeChecker.accept(imageWrapper);

        double transX = offset.getXOffset();
        double transY = offset.getYOffset();
        double rotAngle = offset.getRotationalOffset();
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();

        if (interpolation.getBackgroundColor() > 255)
            throw new IllegalArgumentException("Background color must be a greyscale color. Value between 0 and 255!");

        ImageWrapper<T> result = provider.getImage(height, width, ChannelType.GREYSCALE);

        //calc center position
        double midX = width / 2.0;
        double midY = height / 2.0;

        //getRotation angle degree ==> radian
        double radAngle = -rotAngle * Math.PI / 180.0;
        double cosTheta = Math.cos(radAngle);
        double sinTheta = Math.sin(radAngle);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
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
                posX -= transX;
                posY -= transY;

                //5) get scalar value of inImg[][] ==> bilinear inter?
                double scalarVal = interpolation.apply(imageWrapper, new JavaPoint2D(posX, posY));

                //6) fill in resulting value
                result.setValue(x, y, 0, scalarVal + 0.5);

            } //for y
        } //for x

        return result;
    }
}
