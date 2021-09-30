/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.microsoftcognitiveservices.transformers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.typecheck.TypeChecker;
import science.aist.jack.general.transformer.Transformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * <p>This class transforms a javaImage into an application/octet-stream compatible byte array with bmp encoding.</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@RequiredArgsConstructor
public class ByteArrayImage2ByteTransformer implements Transformer<byte[], ImageWrapper<short[][][]>> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.RGB, ChannelType.BGR);

    @NonNull
    private Transformer<ImageWrapper<short[][][]>, ImageWrapper<BufferedImage>> transformer;

    /**
     * Transforms a java image into an bmp encoded octet-stream which can be sent to an rest api
     *
     * @param javaImage to be transformed
     * @return From corresponding to To
     */
    @Override
    public byte[] transformTo(ImageWrapper<short[][][]> javaImage) {
        typeChecker.accept(javaImage);
        try {
            BufferedImage res = transformer.transformFrom(javaImage).getImage();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(res, "bmp", b);
            return b.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * This function is not needed, so currently not implemented
     *
     * @param bytes to be transformed
     * @return To corresponding to From
     */
    @Override
    public ImageWrapper<short[][][]> transformFrom(byte[] bytes) {
        throw new UnsupportedOperationException();
    }
}
