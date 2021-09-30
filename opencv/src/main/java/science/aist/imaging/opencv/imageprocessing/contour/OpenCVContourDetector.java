/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.contour;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>Use OpenCV Contour detection to detect contours in a image</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class OpenCVContourDetector implements Function<ImageWrapper<Mat>, Collection<JavaPolygon2D>> {

    /**
     * Contour retrieval mode
     *
     * <ul>
     * <li>CV_RETR_EXTERNAL retrieves only the extreme outer contours. It sets hierarchy[i][2]=hierarchy[i][3]=-1 for all the contours.</li>
     * <li>CV_RETR_LIST retrieves all of the contours without establishing any hierarchical relationships.</li>
     * <li>CV_RETR_CCOMP retrieves all of the contours and organizes them into a two-level hierarchy. At the top level, there are external boundaries of the components. At the second level, there are boundaries of the holes. If there is another contour inside a hole of a connected component, it is still put at the top level.</li>
     * <li>CV_RETR_TREE retrieves all of the contours and reconstructs a full hierarchy of nested contours. This full hierarchy is built and shown in the OpenCV contours.c demo.</li>
     * </ul>
     *
     * @see <a href="https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=findcontours#findcontours">https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=findcontours#findcontours</a>
     */
    private int mode = Imgproc.RETR_CCOMP;

    /**
     * Contour approximation method.
     * <ul>
     * <li>CV_CHAIN_APPROX_NONE stores absolutely all the contour points. That is, any 2 subsequent points (x1,y1) and (x2,y2) of the contour will be either horizontal, vertical or diagonal neighbors, that is, max(abs(x1-x2),abs(y2-y1))==1.</li>
     * <li>CV_CHAIN_APPROX_SIMPLE compresses horizontal, vertical, and diagonal segments and leaves only their end points. For example, an up-right rectangular contour is encoded with 4 points.</li>
     * <li>CV_CHAIN_APPROX_TC89_L1,CV_CHAIN_APPROX_TC89_KCOS applies one of the flavors of the Teh-Chin chain approximation algorithm. See [TehChin89] for details.</li>
     * </ul>
     *
     * @see <a href="https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=findcontours#findcontours">https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=findcontours#findcontours</a>
     */
    private int method = Imgproc.CHAIN_APPROX_NONE;

    /**
     * Optional offset by which every contour point is shifted. This is useful if the contours are extracted from the image ROI and then they should be analyzed in the whole image context.
     *
     * @see <a href="https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=findcontours#findcontours">https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=findcontours#findcontours</a>
     */
    private Point offset = new Point(0, 0);

    @Override
    public Collection<JavaPolygon2D> apply(ImageWrapper<Mat> matImageWrapper) {
        // hierarchy is ignored at the moment
        Mat hierarchy = new Mat();
        try {
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(matImageWrapper.getImage(), contours, hierarchy, mode, method, offset);

            return contours
                    .stream()
                    .map(mop -> IntStream
                            .range(0, mop.height())
                            .mapToObj(i -> mop.get(i, 0))
                            .map(dArr -> new JavaPoint2D(dArr[0], dArr[1]))
                            .collect(Collectors.toList())
                    )
                    .map(JavaPolygon2D::new)
                    .collect(Collectors.toList());
        } finally {
            hierarchy.release();
        }
    }
}
