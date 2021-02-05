/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.featureextraction;

import science.aist.imaging.api.domain.RecognizedObject;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.featureextraction.RegionDetection;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Use watershed algorithm to isolate objects from each other.
 * Isolates object, then transforms them into recognized objects containing coordinates.
 * <p>
 * Suggestion from: https://stackoverflow.com/questions/19273161/watershed-in-opencv-android
 *
 * @author Christoph Praschl
 * @author Andreas Pointner
 * @since 1.0
 */
@SuppressWarnings({"ALL", "squid:S4165", "squid:S3878", "squid:S2184"})
public class OpenCVWatershedRegionDetection implements RegionDetection<Mat, Double> {

    /**
     * Searchs neighboring pixels for a specific grey value.
     * If said required grey value is given, will continue search at position of found pixel.
     * This way, can find bigger objects made of these values.
     *
     * @param object object to create
     * @param image  image containing pixel values
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @return object filled with pixel coordinates
     */
    private static RecognizedObject searchForObject(RecognizedObject object, Mat image, int x, int y) {

        //add the data for the first found coordinate
        object.getCoordinates().add(new JavaPoint2D(x, y));

        //remove the information of the start point, so we don't find it again
        image.put(y, x, new double[]{0.0});

        //create array for coordinates we still have to look through
        LinkedHashSet<JavaPoint2D> coordinates = new LinkedHashSet<>();

        //add the first neighboring pixels according to the diagram at the top
        coordinates = findNeighbors(coordinates, x, y, image);

        while (!coordinates.isEmpty()) {
            JavaPoint2D currentCoordinate = coordinates.iterator().next();
            object.getCoordinates().add(currentCoordinate);

            //add neighboring coordinates
            coordinates = findNeighbors(coordinates, currentCoordinate.getIntX(), currentCoordinate.getIntY(), image);

            //remove the current coordinate, both from array as well as from image
            coordinates.remove(currentCoordinate);
            //remove values from the image, so we don't analyze it again
            image.put(currentCoordinate.getIntY(), currentCoordinate.getIntX(), new double[]{0.0});
        }

        return object;
    }

    /**
     * Method for finding neighboring pixels with the correct grey value.
     * Will look into the four cardinal directions.
     *
     * @param coordinates current list of pixels, that we still have to look through
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param image       image containing color information
     * @return list with additional pixels to search through
     */
    private static LinkedHashSet<JavaPoint2D> findNeighbors(LinkedHashSet<JavaPoint2D> coordinates, int x, int y, Mat image) {

        //right
        if (x + 1 < image.width() && image.get(y, x + 1)[0] == 128.0) {
            coordinates.add(new JavaPoint2D(x + 1, y));
        }
        //left
        if (x - 1 >= 0 && image.get(y, x - 1)[0] == 128.0) {
            coordinates.add(new JavaPoint2D(x - 1, y));
        }
        //up
        if (y + 1 < image.height() && image.get(y + 1, x)[0] == 128.0) {
            coordinates.add(new JavaPoint2D(x, y + 1));
        }
        //down
        if (y - 1 >= 0 && image.get(y - 1, x)[0] == 128.0) {
            coordinates.add(new JavaPoint2D(x, y - 1));
        }

        return coordinates;
    }

    @Override
    public List<RecognizedObject<Mat, Double>> recognizeRegion(Mat image, double threshold) {
        Mat greyscale = new Mat();
        Mat foreground = new Mat(image.size(), CvType.CV_8U);
        Mat background = new Mat(image.size(), CvType.CV_8U);
        Mat markers = new Mat(image.size(), CvType.CV_8U, new Scalar(0));

        try {

            //transform the image to greyscale
            Imgproc.cvtColor(image, greyscale, Imgproc.COLOR_BGR2GRAY);

            //apply binarization as first filter step
            Imgproc.threshold(greyscale, greyscale, threshold, 255, Imgproc.THRESH_OTSU);

            //find foreground objects, erode them to stronger isolate them from each other
            Imgproc.erode(greyscale, foreground, new Mat(), new Point(-1, -1), 2);

            //find background, dilate them, similar effect as above
            Imgproc.dilate(greyscale, background, new Mat(), new Point(-1, -1), 3);
            Imgproc.threshold(background, background, 1, 128, Imgproc.THRESH_BINARY_INV);

            //create markers by calculating difference of foreground and background
            Core.add(foreground, background, markers);

            //apply watershed and try to find different objects
            markers.convertTo(markers, CvType.CV_32S);
            Imgproc.watershed(image, markers);
            markers.convertTo(markers, CvType.CV_8U);

            //now find different objects
            //colors: 0 = background, 128 = object, 255 = separating line
            List<RecognizedObject<Mat, Double>> objects = new ArrayList<>();

            for (int y = 0; y < markers.height(); y++) {
                for (int x = 0; x < markers.width(); x++) {
                    //check if we found an object at this position
                    if (markers.get(y, x)[0] == 128.0) {
                        RecognizedObject<Mat, Double> newObject = new RecognizedObject<>();
                        newObject.setFromImage(image);
                        newObject.setThresholdUsed(threshold);
                        newObject.setCoordinates(new ArrayList<>());
                        objects.add(searchForObject(newObject, markers, x, y));
                    }
                }
            }

            //set ids for the objects
            int id = 0;
            for (RecognizedObject<Mat, Double> object : objects) {
                object.setId(id);
                id++;
            }


            return objects;
        } finally {
            foreground.release();
            background.release();
            markers.release();
            greyscale.release();
        }
    }
}
