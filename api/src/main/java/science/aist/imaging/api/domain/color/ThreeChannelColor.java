/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.color;

import java.io.Serializable;

/**
 * <p>Interface representing a three channels color (e.g. RGB, HSV, HSL, HSB, Lab, ...)</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public interface ThreeChannelColor extends Serializable {
    /**
     * @return The first channel for the represented color
     */
    double getChannel1();

    /**
     * @param channel The first channel for the represented color
     */
    void setChannel1(double channel);

    /**
     * @return The second channel for the represented color
     */
    double getChannel2();

    /**
     * @param channel The second channel for the represented color
     */
    void setChannel2(double channel);

    /**
     * @return The third channel for the represented color
     */
    double getChannel3();

    /**
     * @param channel The third channel for the represented color
     */
    void setChannel3(double channel);
}
