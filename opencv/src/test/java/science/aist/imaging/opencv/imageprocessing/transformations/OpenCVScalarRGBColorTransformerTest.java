/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformations;

import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVScalarRGBColorTransformer;
import org.opencv.core.Scalar;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Christoph Praschl
 */
public class OpenCVScalarRGBColorTransformerTest extends OpenCVTest {
    @Test
    public void testScalarToRGB() {
        //given
        OpenCVScalarRGBColorTransformer transformer = new OpenCVScalarRGBColorTransformer();
        Scalar s = new Scalar(255, 130, 60);

        //when
        RGBColor color = transformer.transformFrom(s);

        //then
        Assert.assertEquals(s.val[0], color.getBlue());
        Assert.assertEquals(s.val[1], color.getGreen());
        Assert.assertEquals(s.val[2], color.getRed());
    }

    @Test
    public void testRGBToScalar() {
        //given
        OpenCVScalarRGBColorTransformer transformer = new OpenCVScalarRGBColorTransformer();
        RGBColor c = new RGBColor(60, 130, 255);

        //when
        Scalar s = transformer.transformTo(c);

        //then
        Assert.assertEquals(s.val[0], c.getBlue());
        Assert.assertEquals(s.val[1], c.getGreen());
        Assert.assertEquals(s.val[2], c.getRed());
    }

}
