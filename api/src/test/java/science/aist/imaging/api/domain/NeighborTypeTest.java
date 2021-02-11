/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Test class for {@link NeighborType}</p>
 *
 * @author Christoph Praschl
 */
public class NeighborTypeTest {

    @Test
    public void testGetImageMask() {
        // given
        NeighborType n8 = NeighborType.N8;

        // when
        ImageWrapper<short[][][]> imageMask = n8.getImageMask();

        // then
        for (int y = 0; y < imageMask.getHeight(); y++) {
            for (int x = 0; x < imageMask.getWidth(); x++) {
                Assert.assertEquals((int) imageMask.getValue(x, y, 0), 255);
            }
        }
    }

}
