/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.openimaj.imageprocessing.transformer;

import org.openimaj.image.colour.ColourSpace;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;

/**
 * <p>Test class for {@link ColourSpace2ChannelTypeTransformer}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ColourSpace2ChannelTypeTransformerTest {

    @Test
    public void testTransformTo() {
        // given
        ColourSpace2ChannelTypeTransformer transformer = new ColourSpace2ChannelTypeTransformer();

        // when
        ChannelType channelType = transformer.transformTo(ColourSpace.RGB);

        // then
        Assert.assertEquals(channelType, ChannelType.RGB);
    }

    @Test
    public void testTransformTo2() {
        // given
        ColourSpace2ChannelTypeTransformer transformer = new ColourSpace2ChannelTypeTransformer();

        // when
        ChannelType channelType = transformer.transformTo(ColourSpace.HSY);

        // then
        Assert.assertEquals(channelType, ChannelType.UNKNOWN_3_CHANNEL);
    }

    @Test
    public void testTransformFrom() {
        // given
        ColourSpace2ChannelTypeTransformer transformer = new ColourSpace2ChannelTypeTransformer();

        // when
        ColourSpace colourSpace = transformer.transformFrom(ChannelType.RGB);

        // then
        Assert.assertEquals(colourSpace, ColourSpace.RGB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTransformFrom2() {
        // given
        ColourSpace2ChannelTypeTransformer transformer = new ColourSpace2ChannelTypeTransformer();

        // when
        ColourSpace colourSpace = transformer.transformFrom(ChannelType.UNKNOWN_3_CHANNEL);

        // then - exception
    }

}
