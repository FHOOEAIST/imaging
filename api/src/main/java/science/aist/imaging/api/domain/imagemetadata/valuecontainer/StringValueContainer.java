/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.imagemetadata.valuecontainer;

import science.aist.imaging.api.domain.imagemetadata.ValueContainer;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * <p>Implementation of {@link ValueContainer} for {@link String}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class StringValueContainer implements ValueContainer<String> {
    @XmlAttribute(name = "value")
    @Getter
    private String value;
}
