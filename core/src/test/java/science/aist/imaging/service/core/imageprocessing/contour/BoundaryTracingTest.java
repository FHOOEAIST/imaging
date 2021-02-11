/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.contour;

import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.core.imageprocessing.transformation.ThresholdFunction;
import science.aist.imaging.service.core.storage.Image2ByteInputStreamLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Test class for {@link BoundaryTracing}</p>
 *
 * @author Christoph Praschl
 */
public class BoundaryTracingTest {
    private final Function<InputStream, ImageWrapper<short[][][]>> imageLoader = new Image2ByteInputStreamLoader();

    @Test
    public void testApply() {
        ThresholdFunction<short[][][], short[][][]> t = new ThresholdFunction<>(TypeBasedImageFactoryFactory.getImageFactory(short[][][].class));
        t.setBackground(0);
        t.setForeground(255);
        t.setLowerThresh(80);

        ImageWrapper<short[][][]> img1 = t.apply(imageLoader.apply(getClass().getResourceAsStream("/logo/logoBinary.bmp")));

        BoundaryTracing boundaryTracing = new BoundaryTracing();

        // when
        List<JavaPolygon2D> apply1 = boundaryTracing.apply(img1);

        // then
        Assert.assertEquals(apply1.size(), 33);
    }
}