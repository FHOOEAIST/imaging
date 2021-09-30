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
import science.aist.imaging.api.domain.color.HSVColor;
import science.aist.imaging.api.domain.color.RGBColor;
import science.aist.jack.general.transformer.Transformer;

/**
 * @author Christoph Praschl
 */
public class HSVTransformerTest {
    private final Transformer<RGBColor, HSVColor> colorTransformer = new HSVTransformer();

    @Test
    void testTo1() {
        // given
        RGBColor color = RGBColor.RED;

        // when
        HSVColor result = colorTransformer.transformFrom(color);

        // then
        Assert.assertEquals(result.getHue(), 0.0);
        Assert.assertEquals(result.getSaturation(), 1.0);
        Assert.assertEquals(result.getValue(), 1.0);
    }

    @Test
    void testTo2() {
        // given
        RGBColor color = RGBColor.BLACK;

        // when
        HSVColor result = colorTransformer.transformFrom(color);

        // then
        Assert.assertEquals(result.getHue(), 0.0);
        Assert.assertEquals(result.getSaturation(), 0.0);
        Assert.assertEquals(result.getValue(), 0.0);
    }

    @Test
    void testTo3() {
        // given
        RGBColor color = RGBColor.WHITE;

        // when
        HSVColor result = colorTransformer.transformFrom(color);

        // then
        Assert.assertEquals(result.getHue(), 0.0);
        Assert.assertEquals(result.getSaturation(), 0.0);
        Assert.assertEquals(result.getValue(), 1.0);
    }

    @Test
    void testTo4() {
        // given
        RGBColor color = new RGBColor(115, 204, 60);

        // when
        HSVColor result = colorTransformer.transformFrom(color);

        // then
        Assert.assertEquals(Math.round((result.getHue() * 100.0)) / 100.0, 0.27);
        Assert.assertEquals(Math.round((result.getSaturation() * 100.0)) / 100.0, 0.71);
        Assert.assertEquals(result.getValue(), 0.800000011920929);
    }

    @Test
    void testFrom1() {
        // given
        HSVColor color = HSVColor.RED;

        // when
        RGBColor result = colorTransformer.transformTo(color);

        // then
        Assert.assertEquals(result.getRed(), 255.0);
        Assert.assertEquals(result.getGreen(), 0.0);
        Assert.assertEquals(result.getBlue(), 0.0);
    }

    @Test
    void testFrom2() {
        // given
        HSVColor color = HSVColor.BLACK;

        // when
        RGBColor result = colorTransformer.transformTo(color);

        // then
        Assert.assertEquals(result.getRed(), 0.0);
        Assert.assertEquals(result.getGreen(), 0.0);
        Assert.assertEquals(result.getBlue(), 0.0);
    }

    @Test
    void testFrom3() {
        // given
        HSVColor color = HSVColor.WHITE;

        // when
        RGBColor result = colorTransformer.transformTo(color);

        // then
        Assert.assertEquals(result.getRed(), 255.0);
        Assert.assertEquals(result.getGreen(), 255.0);
        Assert.assertEquals(result.getBlue(), 255.0);
    }

    @Test
    void testFrom4() {
        // given
        HSVColor color = new HSVColor(97.08, 0.71, 0.8);

        // when
        RGBColor result = colorTransformer.transformTo(color);

        // then
        Assert.assertEquals(result.getRed(), 204.0);
        Assert.assertEquals(result.getGreen(), 129.0);
        Assert.assertEquals(result.getBlue(), 59.0);
    }
}
