/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.wrapper.implementation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import science.aist.imaging.api.domain.wrapper.ChannelType;

import java.awt.image.BufferedImage;

/**
 * <p>Types of an buffered image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum BufferedImageType {
    TYPE_3BYTE_BGR(ChannelType.BGR, BufferedImage.TYPE_3BYTE_BGR),
    TYPE_4BYTE_ABGR(ChannelType.BGRA, BufferedImage.TYPE_4BYTE_ABGR), // Attention: ABGR to BGRA
    TYPE_4BYTE_ABGR_PRE(ChannelType.BGRA, BufferedImage.TYPE_4BYTE_ABGR_PRE), // Attention: ABGR to BGRA
    TYPE_BYTE_BINARY(ChannelType.BINARY, BufferedImage.TYPE_BYTE_BINARY),
    TYPE_BYTE_GRAY(ChannelType.GREYSCALE, BufferedImage.TYPE_BYTE_GRAY),
    TYPE_BYTE_INDEXED(ChannelType.GREYSCALE, BufferedImage.TYPE_BYTE_INDEXED),
    TYPE_CUSTOM(ChannelType.UNKNOWN, BufferedImage.TYPE_CUSTOM),
    TYPE_INT_ARGB(ChannelType.RGBA, BufferedImage.TYPE_INT_ARGB), // Attention: ARGB to RGBA
    TYPE_INT_ARGB_PRE(ChannelType.RGBA, BufferedImage.TYPE_INT_ARGB_PRE), // Attention:  ARGB to RGBA
    TYPE_INT_BGR(ChannelType.BGR, BufferedImage.TYPE_INT_BGR),
    TYPE_INT_RGB(ChannelType.RGB, BufferedImage.TYPE_INT_RGB),
    TYPE_USHORT_555_RGB(ChannelType.RGB, BufferedImage.TYPE_USHORT_555_RGB),
    TYPE_USHORT_565_RGB(ChannelType.RGB, BufferedImage.TYPE_USHORT_565_RGB),
    TYPE_USHORT_GRAY(ChannelType.GREYSCALE, BufferedImage.TYPE_USHORT_GRAY);

    /**
     * Associated Channeltype
     */
    private ChannelType associatedType;

    /**
     * int ID used in the BufferedImage class
     */
    private int id;

    /**
     * Gets the enum value for the given id
     *
     * @param id for which enum should be returned
     * @return enum for given id
     */
    public static BufferedImageType getForId(int id) {
        switch (id) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return TYPE_3BYTE_BGR;
            case BufferedImage.TYPE_4BYTE_ABGR:
                return TYPE_4BYTE_ABGR;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                return TYPE_4BYTE_ABGR_PRE;
            case BufferedImage.TYPE_BYTE_BINARY:
                return TYPE_BYTE_BINARY;
            case BufferedImage.TYPE_BYTE_GRAY:
                return TYPE_BYTE_GRAY;
            case BufferedImage.TYPE_BYTE_INDEXED:
                return TYPE_BYTE_INDEXED;
            case BufferedImage.TYPE_CUSTOM:
                return TYPE_CUSTOM;
            case BufferedImage.TYPE_INT_ARGB:
                return TYPE_INT_ARGB;
            case BufferedImage.TYPE_INT_ARGB_PRE:
                return TYPE_INT_ARGB_PRE;
            case BufferedImage.TYPE_INT_BGR:
                return TYPE_INT_BGR;
            case BufferedImage.TYPE_INT_RGB:
                return TYPE_INT_RGB;
            case BufferedImage.TYPE_USHORT_555_RGB:
                return TYPE_USHORT_555_RGB;
            case BufferedImage.TYPE_USHORT_565_RGB:
                return TYPE_USHORT_565_RGB;
            case BufferedImage.TYPE_USHORT_GRAY:
                return TYPE_USHORT_GRAY;
            default:
                throw new IllegalArgumentException("Unknown type id");
        }
    }


    /**
     * Returns a compatible buffered image type for the given type
     *
     * @param type Channel type
     * @return compatible BufferedImageType
     */
    public static BufferedImageType toBufferedImageType(ChannelType type) {
        switch (type) {
            case UNKNOWN:
            case UNKNOWN_3_CHANNEL:
            case UNKNOWN_4_CHANNEL:
                return BufferedImageType.TYPE_CUSTOM;
            case GREYSCALE:
                return BufferedImageType.TYPE_BYTE_GRAY;
            case BINARY:
                return BufferedImageType.TYPE_BYTE_BINARY;
            case BGR:
                return BufferedImageType.TYPE_INT_BGR;
            case RGB:
                return BufferedImageType.TYPE_INT_RGB;
            case BGRA:
                return BufferedImageType.TYPE_4BYTE_ABGR;
            case RGBA:
                return BufferedImageType.TYPE_INT_ARGB;
            default:
                throw new IllegalStateException("Unsupported ChannelType (" + type.name() + ")!");
        }
    }
}
