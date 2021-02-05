/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.color;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 * @author Andreas Pointner
 */
public class RGBColorTest {
    /**
     * test if color to colorRep conversion works
     */
    @Test
    void testColorConversion() {
        // given
        RGBColor color = new RGBColor(20, 30, 40);

        // when
        int colorRep = RGBColor.createColorRepresentation(color);

        // then
        Assert.assertEquals(colorRep, 20 * 256 * 256 + 30 * 256 + 40);
    }

    /**
     * test if colorRep to color conversion works
     */
    @Test
    void testColorRepConversion() {
        // given
        int colorRep = 15 * 256 * 256 + 100 * 256 + 243;

        // when
        RGBColor color = RGBColor.createRGBColor(colorRep);

        // then
        Assert.assertEquals(color.getRed(), 15.0);
        Assert.assertEquals(color.getGreen(), 100.0);
        Assert.assertEquals(color.getBlue(), 243.0);
    }
}
