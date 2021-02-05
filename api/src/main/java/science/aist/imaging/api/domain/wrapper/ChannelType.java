/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper;

/**
 * <p>Enum with pixel channel types</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public enum ChannelType {
    UNKNOWN(-1),
    UNKNOWN_2_CHANNEL(2),
    UNKNOWN_3_CHANNEL(3),
    UNKNOWN_4_CHANNEL(4),
    GREYSCALE(1),
    BINARY(1),
    BGR(3),
    RGB(3),
    HSV(3),
    BGRA(4),
    RGBA(4),
    LUV(3),
    YUV(3);

    private int numberOfChannels;

    ChannelType(int numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }

    /**
     * Creates a ChannelType out of the numbers of channels
     *
     * @param channels the number of channels
     * @return the channel type matching the number of channels
     */
    public static ChannelType makeChannelType(int channels) {
        switch (channels) {
            case 1:
                return GREYSCALE;
            case 3:
                return UNKNOWN_3_CHANNEL;
            case 4:
                return UNKNOWN_4_CHANNEL;
            default:
                return UNKNOWN;
        }
    }

    public int getNumberOfChannels() {
        return numberOfChannels;
    }

}
