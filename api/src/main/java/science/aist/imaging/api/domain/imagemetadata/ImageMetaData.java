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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * <p>Base class that contains all image meta data</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@XmlRootElement(name = "javax_imageio_1.0")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ImageMetaData implements Serializable {
    @XmlElement(name = "Chroma")
    private Chroma chroma;

    @XmlElement(name = "Compression")
    private Compression compression;

    @XmlElement(name = "Dimension")
    private Dimension dimension;
}
