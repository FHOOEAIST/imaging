/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.registration;

import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.registration.Registration;
import science.aist.imaging.service.opencv.imageprocessing.compare.OpenCVPositionalAndRotationalOffsetFunction;
import lombok.Setter;
import org.opencv.core.Mat;
import science.aist.jack.general.transformer.Transformer;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>This class implements Registration for positional and rotational offset</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class OpenCVRegistrationPositionalRotational implements Registration<Mat> {
    /**
     * Reference to Image Compare
     */
    private OpenCVPositionalAndRotationalOffsetFunction prFunction;

    /**
     * TransformFunction Function
     */
    private BiFunction<ImageWrapper<?>, RotationOffset, ImageWrapper<short[][][]>> transform;

    /**
     * OpenCV to 2Byte transformer
     */
    private Transformer<ImageWrapper<Mat>, ImageWrapper<short[][][]>> image2ByteTransformer;


    /**
     * This function applies image registration on given elements. Therefore it compares it to a ref object, and tries to translate the object
     * in horizontal and vertical direction as well as in direction to fit the ref image.
     *
     * @param ref      the ref image, where the elements should fit to
     * @param elements the elements, which should to fit the given ref
     * @return the result elements fitting to the ref image
     */
    @Override
    public Collection<ImageWrapper<Mat>> register(ImageWrapper<Mat> ref, Collection<ImageWrapper<Mat>> elements) {
        return elements
                .stream()
                .map(element ->
                        ((Function<ImageWrapper<Mat>, ImageWrapper<short[][][]>>) image2ByteTransformer::transformFrom)
                                .andThen(ImageFunction.closeAfterApply(i -> transform.apply(i, prFunction.apply(element, ref))))
                                .andThen(ImageFunction.closeAfterApply(image2ByteTransformer::transformTo))
                                .apply(element)
                )
                .collect(Collectors.toList());
    }
}
