/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.registration;

import science.aist.imaging.api.domain.offset.TranslationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.registration.Registration;
import science.aist.imaging.opencv.imageprocessing.compare.OpenCVPositionalOffsetFunction;
import science.aist.imaging.opencv.imageprocessing.transformation.OpenCVTranslateFunction;
import lombok.CustomLog;
import lombok.Setter;
import org.opencv.core.Mat;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>This class performs registration for positional offset with openCV support.
 * It does not uses opencv Image registration, because due to this post (http://answers.opencv.org/question/29436/image-registration-using-opencv-java/) it is currently not supported in the java wrapper</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
@Setter
public class OpenCVRegistrationPositional implements Registration<Mat> {

    /**
     * Reference to Image Util
     */
    private OpenCVTranslateFunction translateFunction;
    /**
     * Reference to positional offset function
     */
    private OpenCVPositionalOffsetFunction pFunction;

    /**
     * This function applies image registration on given elements. Therefore it compares it to a ref object, and tries to translate the object
     * in horizontal and vertical direction to fit the ref image.
     *
     * @param ref      the ref image, where the elements should fit to
     * @param elements the elements, which should to fit the given ref
     * @return the result elements fitting to the ref image
     */
    @Override
    public Collection<ImageWrapper<Mat>> register(final ImageWrapper<Mat> ref, Collection<ImageWrapper<Mat>> elements) {
        return elements.stream().map(element -> {
            TranslationOffset o = pFunction.apply(element, ref);
            log.debug("OrientationOffset: " + o.toString());
            translateFunction.setOffset(o);
            return translateFunction.apply(element);
        }).collect(Collectors.toList());
    }

}
