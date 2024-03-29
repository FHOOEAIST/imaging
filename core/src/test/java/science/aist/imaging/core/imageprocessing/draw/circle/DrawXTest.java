/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.draw.circle;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;
import science.aist.imaging.core.imageprocessing.draw.line.DrawLine;

/**
 * <p>Test class for {@link DrawX}</p>
 *
 * @author Christoph Praschl
 */
public class DrawXTest {

    @Test
    public void testAccept() {
        // given
        ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(11, 11);
        DrawLine<short[][][]> drawLine = new DrawLine<>();
        drawLine.setColor(new double[]{1});

        DrawX<short[][][]> draw = new DrawX<>(drawLine);
        draw.setLength(50);

        // when
        draw.accept(image, new JavaPoint2D(6, 6));

        // then
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (x == y || x == image.getHeight() - y - 1) {
                    Assert.assertEquals((int) image.getValue(x, y, 0), 1);
                }
            }
        }
    }

}
