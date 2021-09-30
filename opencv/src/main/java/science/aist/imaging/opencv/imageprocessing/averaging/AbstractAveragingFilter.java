/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.averaging;

import science.aist.imaging.api.domain.AverageType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.Getter;
import org.opencv.core.Mat;

import java.util.Collection;
import java.util.function.Function;

/**
 * <p>Abstract class for an filter for averaging multiple images to one</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Getter
public abstract class AbstractAveragingFilter implements Function<Collection<ImageWrapper<Mat>>, ImageWrapper<Mat>> {
    protected AverageType averageType;

    @Override
    public ImageWrapper<Mat> apply(Collection<ImageWrapper<Mat>> imageWrappers) {
        if (imageWrappers.isEmpty()) {
            throw new IllegalArgumentException("Background subtraction needs at least 1 images");
        }
        ImageWrapper<Mat> firstMat = imageWrappers.stream().findFirst().orElseThrow(IllegalArgumentException::new);

        if (imageWrappers.size() == 1) {
            return firstMat;
        }

        boolean b = imageWrappers.stream().allMatch(matImageWrapper -> matImageWrapper.getWidth() == firstMat.getWidth() &&
                matImageWrapper.getHeight() == firstMat.getHeight() &&
                matImageWrapper.getChannels() == firstMat.getChannels());
        if (!b) {
            throw new IllegalArgumentException("All images have to be of same size (height, width) and channels.");
        }

        return averaging(imageWrappers, firstMat);
    }

    protected abstract ImageWrapper<Mat> averaging(Collection<ImageWrapper<Mat>> imageWrappers, ImageWrapper<Mat> firstMat);
}
