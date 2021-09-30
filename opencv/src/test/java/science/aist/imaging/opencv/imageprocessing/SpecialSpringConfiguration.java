/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing;

import org.opencv.core.Mat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

/**
 * <p>Helper class for creating special java beans</p>
 *
 * @author Andreas Pointner
 */
@Configuration
public class SpecialSpringConfiguration {
    @Bean
    public ImageFactory<Mat> factoryMat() {
        return ImageFactoryFactory.getImageFactory(Mat.class);
    }

    @Bean
    public ImageFactory<short[][][]> factory2Byte() {
        return ImageFactoryFactory.getImageFactory(short[][][].class);
    }
}
