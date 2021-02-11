/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.imagemetadata;

import lombok.Getter;
import lombok.ToString;
import science.aist.imaging.api.domain.imagemetadata.valuecontainer.DoubleValueContainer;
import science.aist.imaging.api.domain.imagemetadata.valuecontainer.StringValueContainer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * <p>Image Metadata class that contains information about Dimension</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class Dimension implements Serializable {
    @XmlElement(name = "PixelAspectRatio", type = DoubleValueContainer.class)
    private ValueContainer<Double> pixelAspectRatio;

    @XmlElement(name = "ImageOrientation", type = StringValueContainer.class)
    private ValueContainer<String> imageOrientation;

    @XmlElement(name = "HorizontalPixelSize", type = DoubleValueContainer.class)
    private ValueContainer<Double> horizontalPixelSize;

    @XmlElement(name = "VerticalPixelSize", type = DoubleValueContainer.class)
    private ValueContainer<Double> verticalPixelSize;
}
