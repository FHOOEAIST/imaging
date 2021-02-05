/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.pointprocessing;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Uses Moore Neighbor Tracing Algorithm to extract the inner boundary</p>
 * <a href="http://user.engineering.uiowa.edu/~dip/LECTURE/Segmentation2.html#tracing">http://user.engineering.uiowa.edu/~dip/LECTURE/Segmentation2.html#tracing</a>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class MooreNeighborInnerBoundaryTracing<T extends JavaPoint2D> implements Function<Collection<T>, List<T>> {
    /**
     * Padding in x-direction
     */
    private static final int X_PAD = 1;

    /**
     * Padding in y-direction
     */
    private static final int Y_PAD = 1;

    /**
     * Moore Directions
     */
    private final int[][] direction = {
            {-1, 0}, // left
            {-1, 1}, // bottom left
            {0, 1},  // bottom
            {1, 1},  // bottom right
            {1, 0},  // right
            {1, -1}, // top right
            {0, -1}, // top
            {-1, -1} // top left
    };

    /**
     * Calculates the next point, which has to be checked
     *
     * @param pos the current position
     * @param dir the next direction
     * @return the coordinates of the next point to be checked
     */
    private Point nextToCheck(Point pos, int dir) {
        return new Point(pos.x + direction[dir][0], pos.y + direction[dir][1]);
    }

    @Override
    @SuppressWarnings("java:S3047")
    public List<T> apply(Collection<T> area) {
        // If there is only one point, the algorithm does not work, but the inner contour, can then be seen as that exact point
        if (area.size() == 1) return Collections.singletonList(area.iterator().next());

        int width = 0;
        int height = 0;
        T first = null;

        // Extract area with and height
        for (T t : area) {
            if (first == null) {
                first = t;
            }
            if (t.getX() > width) {
                width = (int) (t.getX() + .5);
            }
            if (t.getY() > height) {
                height = (int) (t.getY() + .5);
            }
        }

        // If either with or height is smaller or equal to zero, the extraction failed
        if (width <= 0 || height <= 0)
            throw new IllegalStateException("Invalid area");

        // Create the image, add a little padding:
        width += X_PAD * 2 + 1;
        height += Y_PAD * 2 + 1;

        //noinspection unchecked
        T[][] points = (T[][]) Array.newInstance(first.getClass(), height, width);
        byte[][] image = new byte[height][width];

        // Build a two dimensional array, to make processing easier and faster (no searching in the Collection required)
        for (T t : area) {
            int px = (int) t.getX();
            int py = (int) t.getY();
            image[py + Y_PAD][px + X_PAD] = 1;
            points[py][px] = t;
        }

        // Find the start point
        Point startPoint = null;

        for (int y = 0; y < height && startPoint == null; y++) {
            for (int x = 0; x < width && startPoint == null; x++) {
                if (image[y][x] > 0) {
                    startPoint = new Point(x, y);
                }
            }
        }

        if (startPoint == null) throw new IllegalStateException("No start point found");


        // The algorithm itself as explained in http://user.engineering.uiowa.edu/~dip/LECTURE/Segmentation2.html#tracing
        int dir = 7;
        int lastDir = dir;

        Point lastHit = startPoint;

        List<T> res = new ArrayList<>();
        while (true) {
            Point nextToCheck = nextToCheck(lastHit, dir);
            if (image[nextToCheck.y][nextToCheck.x] > 0) {
                lastHit = new Point(nextToCheck.x, nextToCheck.y);
                res.add(points[lastHit.y - Y_PAD][lastHit.x - X_PAD]);
                dir = dir % 2 == 0 ? (dir + 7) % 8 : (dir + 6) % 8;
                lastDir = dir;
                if (lastHit.equals(startPoint)) break;
            } else {
                dir = (dir + 1) % 8;
                if (dir == lastDir) {
                    throw new IllegalStateException("Couldn't extract any contour");
                }
            }
        }

        return res;
    }
}
