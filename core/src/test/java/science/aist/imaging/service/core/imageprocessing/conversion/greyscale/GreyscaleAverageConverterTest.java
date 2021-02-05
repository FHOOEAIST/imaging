/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.conversion.greyscale;

import science.aist.imaging.service.core.imageprocessing.conversion.ColorToGreyScaleConverter;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link GreyscaleAverageConverter}</p>
 *
 * @author Andreas Pointner
 */
public class GreyscaleAverageConverterTest {
    @Test
    public void testApply() {
        // given
        double red = 3;
        double green = 5;
        double blue = 10;
        double avg = 6.5;
        ColorToGreyScaleConverter greyScale = new GreyscaleAverageConverter();

        // when
        double res = greyScale.toGreyscale(red, green, blue);

        // then
        Assert.assertEquals(res, avg);
    }
}
