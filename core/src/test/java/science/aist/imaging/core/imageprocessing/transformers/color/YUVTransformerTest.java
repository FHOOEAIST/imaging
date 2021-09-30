/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.transformers.color;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.imaging.api.domain.color.YUVColor;

/**
 * Unittest-class for {@link YUVTransformer}
 *
 * @author Christoph Praschl
 */
public class YUVTransformerTest {

    private YUVTransformer transformer = new YUVTransformer();

    @Test
    public void testToYUV() {
        //GIVEN

        //WHEN
        YUVColor yuv = transformer.transformFrom(RGBColor.RED);

        //THEN
        Assert.assertNotNull(yuv);
        Assert.assertEquals(yuv.getYLuma(), 0.299);
        Assert.assertEquals(yuv.getUChroma(), -0.147);
        Assert.assertEquals(yuv.getVChroma(), 0.615);
    }

    @Test
    public void testFromYUV() {
        //GIVEN

        //WHEN
        RGBColor rgb = transformer.transformTo(YUVColor.RED);

        //THEN
        Assert.assertNotNull(rgb);
        Assert.assertEquals(rgb.getRed(), 255.0);
        Assert.assertEquals(rgb.getGreen(), 0.0);
        Assert.assertEquals(rgb.getBlue(), 0.0);
    }

    @Test
    public void testYUVbackAndForth() {
        //GIVEN
        RGBColor rgbOld = RGBColor.RED;
        YUVColor yuvOld = YUVColor.RED;

        //WHEN
        RGBColor rgb = transformer.transformTo(transformer.transformFrom(rgbOld));
        YUVColor yuv = transformer.transformFrom(transformer.transformTo(yuvOld));

        //THEN
        Assert.assertNotNull(rgb);
        Assert.assertEquals(rgb.getRed(), rgbOld.getRed());
        Assert.assertEquals(rgb.getGreen(), rgbOld.getGreen());
        Assert.assertEquals(rgb.getBlue(), rgbOld.getBlue());

        Assert.assertNotNull(yuv);
        Assert.assertEquals(yuv.getYLuma(), yuvOld.getYLuma());
        Assert.assertEquals(yuv.getUChroma(), yuvOld.getUChroma());
        Assert.assertEquals(yuv.getVChroma(), yuvOld.getVChroma());
    }


}
