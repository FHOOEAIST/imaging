/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.twodimensional;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Tests {@link MetaJavaPoint2D}</p>
 *
 * @author Andreas Pointner
 */
public class MetaJavaPoint2DTest {
    @Test
    public void testSet() {
        // given
        MetaJavaPoint2D mjp = new MetaJavaPoint2D();

        // when
        mjp.setMetaInfo("tmp", 123);

        // then
        // Just assert, that there is no exception
    }

    @Test
    public void testGet1() {
        // given
        final int valRef = 123;
        MetaJavaPoint2D mjp = new MetaJavaPoint2D();
        mjp.setMetaInfo("tmp", valRef);

        // when
        int val = mjp.getMetaInfo("tmp");

        // then
        Assert.assertEquals(val, valRef);
    }

    @Test
    public void testGet2() {
        // given
        final String valRef = "123";
        MetaJavaPoint2D mjp = new MetaJavaPoint2D();
        mjp.setMetaInfo("tmp", valRef);

        // when
        String val = mjp.getMetaInfo("tmp");

        // then
        Assert.assertEquals(val, valRef);
    }
}
