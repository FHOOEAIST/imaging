/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.creator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Collection;
import java.util.function.Function;

/**
 * <p>Creates an Image out of a Collection of JavaLines</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class ImageFromJavaLinesCreatorFunction<T> implements Function<Collection<? extends JavaLine2D>, ImageWrapper<T>> {
    /**
     * Fallback color value, if no imageCallback function is defined and a greyscale image is created.
     */
    private static final double[] FALLBACK_COLOR_GREYSCALE = new double[]{255, 255, 255};

    /**
     * Fallback color value, if no imageCallback function is defined, and a colored image is created.
     */
    private static final double[] FALLBACK_COLOR_COLORED = new double[]{0, 0, 0};

    /**
     * Padding in x-direction
     * (so image width will be maxX + paddingX)
     */
    private int paddingX = 1;

    /**
     * Padding in y-direction
     * (so image height will be maxY + paddingY)
     */
    private int paddingY = 1;

    /**
     * If the image is a colored image type or a greyscale
     */
    private boolean colored = false;

    /**
     * The color callback, to set an specific color for a specific java line
     */
    private JavaImageColorCallback<JavaLine2D> imageCallback;

    @NonNull
    private ImageFactory<T> provider;

    @Override
    @SuppressWarnings({"java:S1119", "java:S3047"})
    public ImageWrapper<T> apply(Collection<? extends JavaLine2D> javaLines) {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (JavaLine2D javaLine : javaLines) {
            if (javaLine.getStartPoint().getX() > maxX)
                maxX = javaLine.getStartPoint().getX();

            if (javaLine.getStartPoint().getY() > maxY)
                maxY = javaLine.getStartPoint().getY();

            if (javaLine.getEndPoint().getX() > maxX)
                maxX = javaLine.getEndPoint().getX();

            if (javaLine.getEndPoint().getY() > maxY)
                maxY = javaLine.getEndPoint().getY();
        }

        ImageWrapper<T> ji = provider.getImage((int) (maxY + paddingY + 1), (int) (maxX + paddingX + 1), colored ? ChannelType.RGB : ChannelType.GREYSCALE);

        double[] fallback = colored ? FALLBACK_COLOR_COLORED : FALLBACK_COLOR_GREYSCALE;
        for (JavaLine2D javaLine : javaLines) {
            double[] color = imageCallback != null ? imageCallback.getColorRepresentation(javaLine).getChannels() : fallback;
            for (JavaPoint2D javaPoint2D : javaLine.getBresenham())
                ji.setValues((int) javaPoint2D.getX(), (int) javaPoint2D.getY(), color);
        }

        return ji;
    }
}
