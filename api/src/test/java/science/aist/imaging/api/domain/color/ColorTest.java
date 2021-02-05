/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.color;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link Color}</p>
 *
 * @author Christoph Praschl
 */
public class ColorTest {

    @Test
    public void testIsCompatibleWithType() {
        // given
        Color c = new Color(1, 1, 1);

        // when
        boolean compatibleWithType = c.isCompatibleWithType(ChannelType.BGR);

        // then
        Assert.assertTrue(compatibleWithType);
    }

    @Test
    public void testGetChannel() {
        // given
        Color c = new Color(1, 1, 1);

        // when
        double channel = c.getChannel(0);

        // then
        Assert.assertEquals(channel, 1.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetChannel2() {
        // given
        Color c = new Color(1, 1, 1);

        // when
        double channel = c.getChannel(3);

        // then
        Assert.assertEquals(channel, 1);
    }

    @Test
    public void testTestEquals() {
        // given
        Color c = new Color(1, 1, 1);
        Color c2 = new Color(1, 1, 1);

        // when
        boolean equals = c.equals(c2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testTestEquals2() {
        // given
        Color c = new Color(1, 1, 1);
        Color c2 = new Color(1, 1);

        // when
        boolean equals = c.equals(c2);

        // then
        Assert.assertFalse(equals);
    }


}
