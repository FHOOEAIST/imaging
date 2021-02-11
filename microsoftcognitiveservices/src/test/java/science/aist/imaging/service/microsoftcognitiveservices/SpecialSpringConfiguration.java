/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.microsoftcognitiveservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.awt.image.BufferedImage;

/**
 * <p>Helper class for creating special java beans</p>
 *
 * @author Andreas Pointner
 */
@Configuration
public class SpecialSpringConfiguration {
    @Bean
    public ImageFactory<BufferedImage> bufferedImageFactory() {
        return ImageFactoryFactory.getImageFactory(BufferedImage.class);
    }

    @Bean
    public ImageFactory<short[][][]> imageFactory() {
        return ImageFactoryFactory.getImageFactory(short[][][].class);
    }
}
