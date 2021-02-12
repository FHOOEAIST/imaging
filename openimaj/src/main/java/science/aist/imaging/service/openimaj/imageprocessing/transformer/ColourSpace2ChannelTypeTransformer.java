/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.openimaj.imageprocessing.transformer;

import org.openimaj.image.colour.ColourSpace;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Transformer implementation for transforming between Imaging's {@link ChannelType} to OpenIMAJ's {@link ColourSpace}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ColourSpace2ChannelTypeTransformer implements Transformer<ChannelType, ColourSpace> {
    @Override
    public ChannelType transformTo(ColourSpace colourSpace) {
        switch (colourSpace) {
            case RGB:
            case RGB_INTENSITY_NORMALISED:
                return ChannelType.RGB;
            case HSV:
                return ChannelType.HSV;
            case LUMINANCE_AVG:
            case LUMINANCE_NTSC:
            case HUE:
            case SATURATION:
            case CUSTOM:
                return ChannelType.GREYSCALE;
            case RGBA:
                return ChannelType.RGBA;
            case HS:
            case HS_2:
            case H1H2:
            case H1H2_2:
                return ChannelType.UNKNOWN_2_CHANNEL;
            case HSI:
            case H2S:
            case H2S_2:
            case CIE_XYZ:
            case HSY:
            case HSL:
            case CIE_Lab:
            case CIE_Lab_Norm:
            case MODIFIED_OPPONENT:
            case OPPONENT:
                return ChannelType.UNKNOWN_3_CHANNEL;
            case YUV:
            case YUV_Norm:
                return ChannelType.YUV;
            case H2SV:
            case H2SV_2:
                return ChannelType.UNKNOWN_4_CHANNEL;
            case CIE_Luv:
                return ChannelType.LUV;
            default:
                return ChannelType.UNKNOWN;
        }
    }

    @Override
    public ColourSpace transformFrom(ChannelType channelType) {
        switch (channelType) {
            case GREYSCALE:
            case BINARY:
                return ColourSpace.LUMINANCE_AVG;
            case BGR:
            case RGB:
                return ColourSpace.RGB;
            case HSV:
                return ColourSpace.HSV;
            case BGRA:
            case RGBA:
                return ColourSpace.RGBA;
            case LUV:
                return ColourSpace.CIE_Luv;
            case YUV:
                return ColourSpace.YUV;
            default:
                throw new IllegalArgumentException("Can't convert " + channelType + " to ColourSpace.");
        }
    }
}
