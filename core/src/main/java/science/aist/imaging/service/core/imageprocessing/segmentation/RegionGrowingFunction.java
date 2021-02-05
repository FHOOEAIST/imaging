/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.segmentation;

import science.aist.imaging.api.domain.NeighborType;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import science.aist.jack.math.MathUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * <p>Implementation of Region Growing</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
@RequiredArgsConstructor
public class RegionGrowingFunction<T, R> implements ImageFunction<T, R> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE);
    private static final double BG_VAL = 0;
    private static final double FG_VAL = 255;
    private static final double UNINITIALIZED = -1;
    private static final int NB_ARR_RADIUS = 1;

    private List<JavaPoint2D> seedPoints;
    private int lowerThresh = 127;
    private int upperThresh = 255;
    private NeighborType neighborType = NeighborType.N8;

    @NonNull
    private ImageFactory<R> provider;

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        typeChecker.accept(imageWrapper);
        int height = imageWrapper.getHeight();
        int width = imageWrapper.getWidth();
        ImageWrapper<R> resultImageWrapper = provider.getImage(height, width, ChannelType.BINARY);

        resultImageWrapper.applyFunction((image, x, y, c) -> image.setValue(x, y, 0, UNINITIALIZED));

        Deque<JavaPoint2D> stack = new ArrayDeque<>();

        // first add the seeds:
        for (JavaPoint2D pos : seedPoints) {
            int x = pos.getIntX();
            int y = pos.getIntY();
            if (x >= 0 && x < width && y >= 0 && y < height) {
                double currVal = imageWrapper.getValue(x, y, 0);
                if (lowerThresh <= currVal && currVal <= upperThresh) {
                    imageWrapper.setValue(x, y, 0, FG_VAL);
                    stack.push(pos);
                } else {
                    imageWrapper.setValue(x, y, 0, BG_VAL);
                }
            }
        }

        while (!stack.isEmpty()) {
            JavaPoint2D currProcessingPos = stack.pop();
            // process neighbors
            for (int xOffset = -NB_ARR_RADIUS; xOffset <= NB_ARR_RADIUS; xOffset++) {
                for (int yOffset = -NB_ARR_RADIUS; yOffset <= NB_ARR_RADIUS; yOffset++) {
                    int nbX = currProcessingPos.getIntX() + xOffset;
                    int nbY = currProcessingPos.getIntY() + yOffset;

                    if (neighborType.getMask()[yOffset + NB_ARR_RADIUS][xOffset + NB_ARR_RADIUS] // check if process neighbor
                            && nbX >= 0 && nbX < width && nbY >= 0 && nbY < height // check if in bounds
                            && MathUtils.equals(resultImageWrapper.getValue(nbX, nbY, 0), UNINITIALIZED)// check if not yet processed
                    ) {
                        double currVal = imageWrapper.getValue(nbX, nbY, 0);
                        if (lowerThresh <= currVal && currVal <= upperThresh) {
                            resultImageWrapper.setValue(nbX, nbY, 0, FG_VAL);
                            stack.push(new JavaPoint2D(nbX, nbY));
                        } else {
                            resultImageWrapper.setValue(nbX, nbY, 0, BG_VAL);
                        }
                    }
                }
            }
        }

        // finally cleanup
        resultImageWrapper.applyFunction((image, x, y, c) -> {
            if (MathUtils.equals(image.getValue(x, y, 0), UNINITIALIZED)) {
                image.setValue(x, y, 0, BG_VAL);
            }
        });

        return resultImageWrapper;
    }
}
