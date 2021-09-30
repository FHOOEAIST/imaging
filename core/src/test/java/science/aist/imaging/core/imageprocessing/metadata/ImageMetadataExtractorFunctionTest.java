/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.metadata;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.imagemetadata.Chroma;
import science.aist.imaging.api.domain.imagemetadata.Compression;
import science.aist.imaging.api.domain.imagemetadata.Dimension;
import science.aist.imaging.api.domain.imagemetadata.ImageMetaData;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;

/**
 * <p>Test class for {@link ImageMetadataExtractorFunction}</p>
 *
 * @author Andreas Pointner
 */
public class ImageMetadataExtractorFunctionTest {

    private ImageMetadataExtractorFunction imageMetadataExtractor = new ImageMetadataExtractorFunction();

    @Test
    public void testApply() throws IOException {
        // given
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(getClass().getResourceAsStream("/imageWithMetaData.jpg"));

        // when
        ImageMetaData imageMetaData = imageMetadataExtractor.apply(imageInputStream);

        // then
        Assert.assertNotNull(imageMetaData);
        Assert.assertNotNull(imageMetaData.getChroma());
        Assert.assertNotNull(imageMetaData.getCompression());
        Assert.assertNotNull(imageMetaData.getDimension());

        Chroma chroma = imageMetaData.getChroma();
        Assert.assertNotNull(chroma.getColorSpaceType());
        Assert.assertNotNull(chroma.getNumChannels());
        Assert.assertEquals(chroma.getColorSpaceType().getName(), "YCbCr");
        Assert.assertEquals(chroma.getNumChannels().getValue().intValue(), 3);

        Compression compression = imageMetaData.getCompression();
        Assert.assertNotNull(compression.getCompressionTypeName());
        Assert.assertNotNull(compression.getLossless());
        Assert.assertNotNull(compression.getNumProgressiveScans());
        Assert.assertEquals(compression.getCompressionTypeName().getValue(), "JPEG");
        Assert.assertFalse(compression.getLossless().getValue());
        Assert.assertEquals(compression.getNumProgressiveScans().getValue().intValue(), 10);

        Dimension dimension = imageMetaData.getDimension();
        Assert.assertNotNull(dimension.getPixelAspectRatio());
        Assert.assertNotNull(dimension.getImageOrientation());
        Assert.assertNotNull(dimension.getHorizontalPixelSize());
        Assert.assertNotNull(dimension.getVerticalPixelSize());
        Assert.assertEquals(dimension.getPixelAspectRatio().getValue(), 1.0D);
        Assert.assertEquals(dimension.getImageOrientation().getValue(), "normal");
        Assert.assertEquals(dimension.getHorizontalPixelSize().getValue(), 0.16933332D, 0.00000001);
        Assert.assertEquals(dimension.getVerticalPixelSize().getValue(), 0.16933332D, 0.00000001);
    }
}
