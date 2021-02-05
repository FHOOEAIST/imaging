/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.threedimensional;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>Test class for {@link NormalJavaPoint3D}</p>
 *
 * @author Christoph Praschl
 */
public class NormalJavaPoint3DTest {

    @Test
    public void testTestEquals() {
        // given
        NormalJavaPoint3D njp1 = new NormalJavaPoint3D(0, 0, 0, 0, 0, 0);
        NormalJavaPoint3D njp2 = new NormalJavaPoint3D(0, 0, 0, 0, 0, 0);

        // when
        boolean equals = njp1.equals(njp2);

        // then
        Assert.assertTrue(equals);
    }

    @Test
    public void testTestEquals2() {
        // given
        NormalJavaPoint3D njp1 = new NormalJavaPoint3D(0, 0, 0, 0, 0, 0);
        NormalJavaPoint3D njp2 = new NormalJavaPoint3D(0, 0, 0, 1, 0, 0);

        // when
        boolean equals = njp1.equals(njp2);

        // then
        Assert.assertFalse(equals);
    }

}
