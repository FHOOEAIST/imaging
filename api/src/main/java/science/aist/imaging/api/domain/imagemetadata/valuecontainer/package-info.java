/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/**
 * <p>This package implements the {@link science.aist.imaging.api.domain.imagemetadata.ValueContainer} interface for different types</p>
 * <p>The reason why this is necessary and not just a single generic container is implemented, is because of the fact, that java does not have
 * generic types at runtime, and thus the XML parser cannot parse it to the correct type</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
package science.aist.imaging.api.domain.imagemetadata.valuecontainer;