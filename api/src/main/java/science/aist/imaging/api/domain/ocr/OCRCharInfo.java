/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.ocr;

import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import lombok.*;

/**
 * <p>This class is used to provide information about a single character after recognizing it with ocr.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OCRCharInfo {
    /**
     * The character itself
     */
    private char character;
    /**
     * The confidence of the correct detection of the character
     */
    private double confidence;
    /**
     * At which index in the string it was detected
     */
    private int index;
    /**
     * The bounding box of the char
     */
    private JavaRectangle2D boundingBox;
}
