/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.draw.line;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image2ByteFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link DrawLine}</p>
 *
 * @author Christoph Praschl
 */
public class DrawLineTest {

    @Test
    public void testAccept() {
        // given
        ImageWrapper<short[][][]> image = Image2ByteFactory.getInstance().getImage(11, 11);
        DrawLine<short[][][]> draw = new DrawLine<>();
        draw.setColor(new double[]{1});

        // when
        draw.accept(image, new JavaLine2D(new JavaPoint2D(5, 0), new JavaPoint2D(5, 11)));

        // then
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (x == 5) {
                    Assert.assertEquals((int) image.getValues(x, y)[0], 1);
                } else {
                    Assert.assertEquals((int) image.getValues(x, y)[0], 0);
                }
            }
        }
    }

}
