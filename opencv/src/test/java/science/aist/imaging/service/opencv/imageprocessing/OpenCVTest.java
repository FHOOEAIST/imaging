/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing;

import science.aist.imaging.api.domain.twodimensional.JavaLine2D;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.LineWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.featureextraction.AbstractEdgeFeatureDetection;
import science.aist.imaging.api.featureextraction.AbstractPointFeatureDetection;
import science.aist.imaging.api.optimization.Optimizer;
import science.aist.imaging.service.opencv.imageprocessing.compare.OpenCVImageCompareFunction;
import science.aist.imaging.service.opencv.imageprocessing.edgedetection.OpenCVCannyEdgeDetection;
import science.aist.imaging.service.opencv.imageprocessing.edgedetection.OpenCVSobelEdgeDetection;
import science.aist.imaging.service.opencv.imageprocessing.storage.OpenCVLoader;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVImageWrapper;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import science.aist.jack.general.transformer.Transformer;
import science.aist.seshat.Logger;

import java.io.File;
import java.io.InputStream;

/**
 * Abstract OpenCVTest class for configuration
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 */
@ContextConfiguration("classpath:imageprocessing-config.xml")
@SuppressWarnings({"java:S2925"})
public abstract class OpenCVTest extends AbstractTestNGSpringContextTests {
    protected static final transient Logger log = Logger.getInstance();

    /**
     * Instance of Point Transformer
     */
    @Autowired
    protected Transformer<Point2Wrapper<Point>, JavaPoint2D> pointTransformer;
    /**
     * Instance of Line Transformer
     */
    @Autowired
    protected Transformer<LineWrapper<Point>, JavaLine2D> lineTransformer;

    /**
     * Instance of FeatureDetection
     */
    @Autowired
    protected AbstractPointFeatureDetection<KeyPoint, Mat> extractor;

    @Autowired
    protected AbstractEdgeFeatureDetection<KeyPoint, Mat> edgeExtractor;

    /**
     * Instance of Canny Edge detection
     */
    @Autowired
    protected OpenCVCannyEdgeDetection cannyEdge;
    /**
     * Instance of Sobel Operator
     */
    @Autowired
    protected OpenCVSobelEdgeDetection sobelEdge;

    /**
     * Instance of Optimizer
     */
    @Autowired
    protected Optimizer<Mat> optimizer;
    /**
     * Instance of Optimizer
     */
    @Autowired
    protected OpenCVLoader openCVLoader;
    protected OpenCVImageCompareFunction compareFunction = new OpenCVImageCompareFunction();

    @AfterMethod(alwaysRun = true)
    public void closeAllOpenedImageWrappers() {
        AbstractImageWrapper.freeAllocatedImageWrappers();
    }

    /**
     * Loads an image (colored!) from the classPath.
     *
     * @param imagePath relative path to the image. (e.g. /logo.jpg for logo.jpg file in test/resources folder)
     * @return the loaded imageWrapper
     */
    protected OpenCVImageWrapper loadImageFromClassPath(String imagePath) {
        return loadImageFromClassPath(imagePath, true);
    }

    /**
     * Loads and image from the classPath.
     *
     * @param imagePath relative path to the image. (e.g. /logo.jpg for logo.jpg file in test/resources folder)
     * @param colored   Flag if image should be loaded colored or in greyscale
     * @return an ImageWrapper containing the loaded image or an empty wrapper if image could not be loaded
     */
    protected OpenCVImageWrapper loadImageFromClassPath(String imagePath, boolean colored) {
        log.debug("Load image from given path " + imagePath);
        InputStream is = OpenCVTest.class.getResourceAsStream(imagePath);
        if (is == null) throw new IllegalArgumentException("Can not find image at given path " + imagePath);
        openCVLoader.setColored(colored);
        return (OpenCVImageWrapper) openCVLoader.apply(is);
    }

    /**
     * Method which returns the absolute path to the tempfolder
     *
     * @return the path to the tempfolder
     */
    private String getPathToTempFolder() {
        StringBuilder sb = new StringBuilder();
        String tempFolder = System.getProperty("java.io.tmpdir");
        String imagingSubFolder = "imaging" + File.separator + "tests" + File.separator;
        sb.append(tempFolder);
        if (!tempFolder.endsWith(File.separator)) {
            sb.append(File.separator);
        }
        sb.append(imagingSubFolder);
        return sb.toString();
    }

    /**
     * Method which returns the absolute path to a given subfolder in the tempfolder
     * Creates all needed subfolders
     *
     * @param subfolder Name of the subfolder(s) in the temp folder
     * @return The path to the subfolder in the the temp folder
     */
    protected String getPathToTempSubfolder(String subfolder) {
        return getPathToTempSubfolder(subfolder, true);
    }

    /**
     * Method which returns the absolute path to a given subfolder in the tempfolder
     * Creates the needed subfolders if flag is set
     *
     * @param subfolder        Name of the subfolder(s) in the temp folder
     * @param createSubfolders Flag which signals if subfolders should be created if they are not existing
     * @return The path to the subfolder in the the temp folder
     */
    protected String getPathToTempSubfolder(String subfolder, boolean createSubfolders) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPathToTempFolder());
        String replacedSubfolder = subfolder.replace("/", File.separator);
        if (replacedSubfolder.length() > 0) {
            sb.append(replacedSubfolder);
            if (!replacedSubfolder.endsWith(File.separator))
                sb.append(File.separator);
        }

        if (createSubfolders) {
            File dir = new File(sb.toString());
            dir.mkdirs();
        }

        return sb.toString();
    }


    @AfterMethod(alwaysRun = true)
    public void memoryCleanUp() {
        System.gc();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
