/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.tesseract.domain;

/**
 * <p>Defining the mode for OCR. e.g. if it is a block full with text, or it is just a single character one the image</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public enum OCRMode {
    BLOCK, SINGLE
}
