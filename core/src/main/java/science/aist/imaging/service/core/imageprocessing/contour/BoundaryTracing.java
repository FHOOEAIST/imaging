/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.contour;

import science.aist.imaging.api.domain.NeighborType;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.jack.math.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Generic boundary tracer. Which extracts independent contours. Use e.g. {@link ContourDetector} to get a binary, contour image first.</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class BoundaryTracing implements Function<ImageWrapper<?>, List<JavaPolygon2D>> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.BINARY);
    private static final List<NeighborType> neighborTypes = Arrays.asList(NeighborType.N4, NeighborType.N8);

    @Override
    @SuppressWarnings("java:S1119")
    public List<JavaPolygon2D> apply(ImageWrapper<?> image) {
        typeChecker.accept(image);

        boolean[][] contourpoints = new boolean[image.getWidth()][image.getHeight()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                contourpoints[x][y] = MathUtils.equals(image.getValue(x, y, 0), 255.0);
            }
        }

        List<JavaPolygon2D> result = new ArrayList<>();

        // go through the whole image and get the next contour pixel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (contourpoints[x][y]) {
                    List<JavaPoint2D> polyPoints = new ArrayList<>();
                    polyPoints.add(new JavaPoint2D(x, y));
                    // reset contour pixel, so we are not visiting it twice
                    contourpoints[x][y] = false;
                    // save position
                    int lastX = x;
                    int lastY = y;

                    // go through the pixel neighborhood to build up the contour
                    boolean foundNeighbor;
                    do {
                        foundNeighbor = false;

                        neighborhood:
                        // switch between neighborhoods
                        for (NeighborType type : neighborTypes) {
                            // go through all neighbors
                            for (int innery = -1; innery <= 1; innery++) {
                                for (int innerx = -1; innerx <= 1; innerx++) {

                                    int xPos = lastX + innerx;
                                    int yPos = lastY + innery;

                                    // check for current neighborhood type (4 or 8) - if 4 continue ignore the outer neighbours
                                    // and check if current neighbor is also a contour point
                                    if (type.getMask()[innery + 1][innerx + 1] &&
                                            xPos >= 0 && xPos < image.getWidth() &&
                                            yPos >= 0 && yPos < image.getHeight() &&
                                            contourpoints[xPos][yPos]) {
                                        polyPoints.add(new JavaPoint2D(xPos, yPos));

                                        // reset contour pixel, so we are not visiting it twice
                                        contourpoints[xPos][yPos] = false;
                                        lastX = xPos;
                                        lastY = yPos;
                                        foundNeighbor = true;
                                        break neighborhood;
                                    }
                                }
                            }
                        }
                    } while (foundNeighbor);

                    // if available add a new polygon to the result list
                    if (!polyPoints.isEmpty()) {
                        result.add(new JavaPolygon2D(polyPoints));
                    }
                }
            }
        }

        return result;
    }
}
