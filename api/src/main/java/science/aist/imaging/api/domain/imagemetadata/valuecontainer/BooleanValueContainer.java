/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.imagemetadata.valuecontainer;

import lombok.ToString;
import science.aist.imaging.api.domain.imagemetadata.ValueContainer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * <p>Implementation of {@link ValueContainer} for {@link Boolean}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class BooleanValueContainer implements ValueContainer<Boolean> {
    // Due to interface which needs an implementation of getValue with Boolean: @Getter is not possible here.
    @XmlAttribute(name = "value")
    private boolean value;

    /**
     * gets value of field {@link BooleanValueContainer#value}
     *
     * @return value of field value
     * @see BooleanValueContainer#value
     */
    public Boolean getValue() {
        return value;
    }
}
