/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.domain.ocr;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Class that contains the result of an OCR analysis</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Getter
@ToString
public class OCRResult {
    /**
     * The result text of the ocr as string
     */
    private String resultString;
    /**
     * The information about single chars
     */
    private Collection<OCRCharInfo> characterInformation;

    /**
     * Initialize the OCRResult
     *
     * @param resultString The result text of the ocr as string
     */
    public OCRResult(String resultString) {
        this.resultString = resultString;
        characterInformation = new ArrayList<>(resultString.length());
    }
}
