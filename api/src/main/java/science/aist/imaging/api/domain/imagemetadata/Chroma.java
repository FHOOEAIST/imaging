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
import science.aist.imaging.api.domain.imagemetadata.valuecontainer.IntegerValueContainer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * <p>Image Metadata class that contains information about Chroma</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.NONE)
@Getter
@ToString
public class Chroma implements Serializable {
    @XmlElement(name = "ColorSpaceType")
    private NameContainer colorSpaceType;

    @XmlElement(name = "NumChannels", type = IntegerValueContainer.class)
    private ValueContainer<Integer> numChannels;
}
