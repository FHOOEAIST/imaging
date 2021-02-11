/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.storage;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVFactory;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import lombok.CustomLog;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * <p>Loads a Open CV Image by InputStream using the</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@CustomLog
@Setter
public class OpenCVLoader implements Function<InputStream, ImageWrapper<Mat>> {
    /**
     * If the image should be loaded as colored image, default is true
     */
    private boolean colored = true;

    /**
     * Method which reads an inputstream into a byte array
     *
     * @param stream stream which should be read
     * @return Returns read inputstream
     * @throws IOException is thrown when stream could not be read/written
     */
    private static byte[] readStream(InputStream stream) throws IOException {
        // Copy content of the image to byte-array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] temporaryImageInMemory = buffer.toByteArray();
        buffer.close();
        stream.close();
        return temporaryImageInMemory;
    }

    @Override
    public ImageWrapper<Mat> apply(InputStream input) {
        Mat m;
        byte[] temporaryImageInMemory;
        try {
            temporaryImageInMemory = readStream(input);

            // Decode into mat.
            int codec;
            if (colored) {
                codec = Imgcodecs.IMREAD_COLOR;
            } else {
                codec = Imgcodecs.IMREAD_GRAYSCALE;
            }
            m = Imgcodecs.imdecode(new MatOfByte(temporaryImageInMemory), codec);
        } catch (IOException e) {
            throw new IllegalArgumentException("Inputstream could not be converted into image.", e);
        }

        ChannelType channelType = colored ? ChannelType.BGR : ChannelType.GREYSCALE;

        // rotate image based on exif-orientation
        if (temporaryImageInMemory.length > 0) {
            try {
                InputStream stream = new ByteArrayInputStream(temporaryImageInMemory);
                Metadata metadata = ImageMetadataReader.readMetadata(stream);
                Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                if (directory != null && directory.hasTagName(ExifDirectoryBase.TAG_ORIENTATION) && directory.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) {
                    int orientation = directory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
                    if (orientation == 6 || orientation == 8) {
                        m = m.t();
                        Mat resultMat = new Mat();
                        Core.flip(m, resultMat, 1);
                        m = resultMat;
                    }
                }
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }

        return ((OpenCVFactory)TypeBasedImageFactoryFactory.getImageFactory(Mat.class)).getImage(m, channelType);
    }

}
