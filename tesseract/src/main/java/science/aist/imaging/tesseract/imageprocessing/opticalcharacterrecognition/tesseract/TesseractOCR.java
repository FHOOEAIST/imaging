/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.tesseract.imageprocessing.opticalcharacterrecognition.tesseract;

import science.aist.imaging.api.domain.ocr.OCRCharInfo;
import science.aist.imaging.api.domain.ocr.OCRResult;
import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.tesseract.domain.OCRMode;
import lombok.Setter;
import org.bytedeco.javacpp.BytePointer;
import science.aist.jack.general.transformer.BackwardTransformer;

import java.io.*;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.function.Function;

import static org.bytedeco.javacpp.tesseract.*;

/**
 * <p>OCR Implementation using Tesseract</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class TesseractOCR implements Function<ImageWrapper<short[][][]>, OCRResult>, AutoCloseable {
    /**
     * Tesseract API
     */
    private final TessBaseAPI api;

    @Setter
    private BackwardTransformer<byte[], ImageWrapper<short[][][]>> imageTransformer;


    /**
     * Constructor to initialize api.
     *
     * @param pathToTessData relative to this path there has to be stored the tessdata folder with the correct language files in it.
     *                       pathToTessData/tessdata/deu.traineddata
     */
    public TesseractOCR(String pathToTessData) {
        this(pathToTessData, "deu");
    }

    /**
     * Constructor to initialize api.
     *
     * @param pathToTessData relative to this path there has to be stored the tessdata folder with the correct language files in it.
     *                       pathToTessData/tessdata/deu.traineddata
     * @param tessLanguage   the language with which tesseract should be initialized
     */
    public TesseractOCR(String pathToTessData, String tessLanguage) {
        final String TESS_DATA_DIR = "./tessdata/";
        final String TESS_DATA_FILE = TESS_DATA_DIR + tessLanguage + ".traineddata";

        File file = new File(TESS_DATA_FILE);
        File dir = new File(TESS_DATA_DIR);
        if (!file.exists()) {
            if (!dir.exists()) {
                boolean b = dir.mkdirs();
                if (!b)
                    throw new IllegalStateException("Could not initialize Tesseract - Failed to create Folder Tessdata");
            }
            byte[] bytes = new byte[1024];
            try (InputStream in = getClass().getResourceAsStream("/tessdata/" + tessLanguage + ".traineddata");
                 OutputStream out = new FileOutputStream(TESS_DATA_FILE)) {
                if (in == null) throw new IllegalStateException("Could not open Inputsream to resource tessdata");
                int l = 0;
                while ((l = in.read(bytes)) > 0) {
                    out.write(bytes, 0, l);
                }
                out.flush();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(pathToTessData, tessLanguage) != 0) {
            throw new IllegalStateException("Tessdata (" + TESS_DATA_FILE + ") not found");
        }
    }

    /**
     * Performs OCR on given Java Image and returns found text.
     *
     * @param ji java image
     * @return the text which was found
     */
    @Override
    public OCRResult apply(ImageWrapper<short[][][]> ji) {
        BytePointer outText = null;
        try {
            api.SetImage(imageTransformer.transformTo(ji), ji.getWidth(), ji.getHeight(), ji.getChannels(), ji.getWidth() * (ji.getChannels()));
            outText = api.GetUTF8Text();

            OCRResult result = new OCRResult(outText.getString().trim());
            if (!"".equals(result.getResultString())) {
                Collection<OCRCharInfo> charInfos = result.getCharacterInformation();
                ResultIterator ri = api.GetIterator();
                int level = RIL_SYMBOL;
                ri.Begin();
                int idx = 0;
                do {
                    String word = ri.GetUTF8Text(level).getString();
                    float conf = ri.Confidence(level);
                    IntBuffer x1 = IntBuffer.allocate(1);
                    IntBuffer y1 = IntBuffer.allocate(1);
                    IntBuffer x2 = IntBuffer.allocate(1);
                    IntBuffer y2 = IntBuffer.allocate(1);
                    ri.BoundingBox(level, x1, y1, x2, y2);
                    charInfos.add(new OCRCharInfo(
                            word.charAt(0),
                            conf,
                            idx,
                            new JavaRectangle2D(x1.get(), y1.get(), x2.get(), y2.get())
                    ));
                    idx++;
                } while (ri.Next(level));
            }
            return result;
        } finally {
            if (outText != null)
                outText.deallocate();
        }
    }

    /**
     * Set whitelist characters, this help the ocr to know which letters are expected
     *
     * @param characters whitelist characters
     */
    public void setWhitespaceCharacters(String characters) {
        api.SetVariable("tessedit_char_whitelist", characters);
    }

    /**
     * Set the Mode type (how to perform the image)
     *
     * @param m mode
     */
    public void setMode(OCRMode m) {
        switch (m) {
            case SINGLE:
                api.SetPageSegMode(PSM_SINGLE_CHAR);
                break;
            case BLOCK:
            default:
                api.SetPageSegMode(PSM_SINGLE_BLOCK);
                break;
        }
    }

    /**
     * Close function to End api.
     */
    @Override
    public void close() {
        api.End();
    }
}
