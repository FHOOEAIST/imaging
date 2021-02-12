/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * <p>Test class for {@link ChannelType}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ChannelTypeTest {

    @Test
    public void testIsValidValue() {
        // given

        // when
        boolean validValue = ChannelType.RGB.isValidValue(240, 0);

        // then
        Assert.assertTrue(validValue);
    }

    @Test
    public void testIsValidValue2() {
        // given

        // when
        boolean validValue = ChannelType.RGB.isValidValue(260, 0);

        // then
        Assert.assertFalse(validValue);
    }

    @Test
    public void testIsValidValue3() {
        // given

        // when
        boolean validValue = ChannelType.RGB.isValidValue(-10, 0);

        // then
        Assert.assertFalse(validValue);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsValidValue4() {
        // given

        // when
        boolean validValue = ChannelType.RGB.isValidValue(240, 3);

        // then - exception
    }

    @Test
    public void testGetMinVal() {
        // given

        // when
        double minVal = ChannelType.RGB.getMinVal(0);

        // then
        Assert.assertEquals(minVal, 0.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetMinVal2() {
        // given

        // when
        double minVal = ChannelType.RGB.getMinVal(-1);

        // then - exception
    }

    @Test
    public void testGetMaxVal() {
        // given

        // when
        double maxVal = ChannelType.RGB.getMaxVal(0);

        // then
        Assert.assertEquals(maxVal, 255.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetMaxVal2() {
        // given

        // when
        double maxVal = ChannelType.RGB.getMaxVal(4);

        // then - exception
    }


}
