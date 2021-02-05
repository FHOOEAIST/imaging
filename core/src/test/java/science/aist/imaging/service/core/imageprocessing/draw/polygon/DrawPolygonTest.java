/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw.polygon;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link DrawPolygon}</p>
 *
 * @author Christoph Praschl
 */
public class DrawPolygonTest {

    @Test
    public void testAccept() {
        // given
        ImageWrapper<short[][][]> image = Image2ByteFactory.getInstance().getImage(11, 11);
        DrawPolygon<short[][][]> draw = new DrawPolygon<>();
        draw.setColor(new double[]{1});

        // when
        draw.accept(image, new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(10, 0), new JavaPoint2D(10, 10), new JavaPoint2D(0, 10)));

        // then
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (x == 0 || y == 0 || x == image.getWidth() - 1 || y == image.getHeight() - 1) {
                    Assert.assertEquals((int) image.getValues(x, y)[0], 1);
                } else {
                    Assert.assertEquals((int) image.getValues(x, y)[0], 0);
                }
            }
        }
    }

    @Test
    public void testAccept2() {
        // given
        ImageWrapper<short[][][]> image = Image2ByteFactory.getInstance().getImage(11, 11);
        DrawPolygon<short[][][]> draw = new DrawPolygon<>();
        draw.setThickness(1);
        draw.setColor(new double[]{1});

        // when
        draw.accept(image, new JavaPolygon2D(new JavaPoint2D(0, 0), new JavaPoint2D(10, 0), new JavaPoint2D(10, 10), new JavaPoint2D(0, 10)));

        // then
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (x == 0 || y == 0 || x == 1 || y == 1 || x == image.getWidth() - 1 || y == image.getHeight() - 1 || x == image.getWidth() - 2 || y == image.getHeight() - 2) {
                    Assert.assertEquals((int) image.getValues(x, y)[0], 1);
                } else {
                    Assert.assertEquals((int) image.getValues(x, y)[0], 0);
                }
            }
        }
    }

}
