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
import science.aist.imaging.service.core.imageprocessing.draw.circle.DrawCircle;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * <p>Test class for {@link DrawDottedLine}</p>
 *
 * @author Christoph Praschl
 */
public class DrawDottedLineTest {

    @Test
    public void testAccept() {
        // given
        ImageWrapper<short[][][]> image = Image2ByteFactory.getInstance().getImage(11, 1);
        DrawCircle<short[][][]> drawCircle = new DrawCircle<>();
        drawCircle.setColor(new double[]{1});

        DrawDottedLine<short[][][]> draw = new DrawDottedLine<>(drawCircle);
        draw.setDotDistance(2);

        int[] reference = new int[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0};

        // when
        draw.accept(image, new JavaLine2D(new JavaPoint2D(0, 0), new JavaPoint2D(0, 11)));

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            assertEquals((int) image.getValue(x, 0, 0), reference[x]);
        }
    }

}
