/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.positioning;

import science.aist.imaging.api.domain.offset.TranslationOffsetInMM;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.api.domain.wrapper.RectangleWrapper;
import science.aist.imaging.api.objectdetection.AbstractDifferencebasedObjectDetector;
import science.aist.imaging.api.positioning.GridbasedPositionEvaluator;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVPoint2WrapperJavaPointTransformer;
import lombok.Getter;
import lombok.Setter;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>OpenCV Implementation of the GridbasedPositionEvaluator Interface which provides
 * functionality to evaluate e.g. the position of an object in the image based on a given grid for calibration</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@Setter
@Getter
public class OpenCVGridbasedPositionEvaluator implements GridbasedPositionEvaluator<Mat, Point> {

    private static final String CALIBRATION_NEEDED = "Calibration needed.";

    @Setter
    private OpenCVPoint2WrapperJavaPointTransformer pointTransformer;
    @Setter
    private AbstractDifferencebasedObjectDetector<Mat, Point, Rect> objectDetector;
    private int horizontalTiles = -1; // number of horizontalTiles
    private int verticalTiles = -1; // number of verticalTiles
    private double tileWidth = 0.0; // width of one tile
    private double tileHeight = 0.0; // height of one tile
    private List<JavaPolygon2D> tiles = new ArrayList<>(); // list containing the tiles after calibration
    private ImageWrapper<Mat> referenceImage; //  Reference image which is used to determine the position of an object

    /**
     * @param referenceImage Reference image which is used to determine the position of an object
     */
    @Override
    public void setReferenceImage(ImageWrapper<Mat> referenceImage) {
        this.referenceImage = referenceImage;
        this.objectDetector.setReferenceImage(referenceImage);
    }

    /**
     * Gets the tile which contains the given position.
     *
     * @param position The position for which the tile should be returned
     * @return The tile containing the position; Or null if position is not on a tile.
     */
    @Override
    public JavaPolygon2D getAffectedTile(Point2Wrapper<Point> position) {
        if (tiles.isEmpty()) throw new IllegalStateException(CALIBRATION_NEEDED);

        JavaPoint2D javaPoint2D = pointTransformer.transformFrom(position);

        for (JavaPolygon2D jp : tiles) {
            if (jp.isInConvexHull(javaPoint2D)) return jp;
        }
        return null;
    }

    /**
     * Gets the x and y index of the given tile
     *
     * @param tile Tile for which index should be returned
     * @return The index of the tile or an index -1/-1 if tile was not found
     */
    @Override
    public Point2Wrapper<Point> getTileIndex(JavaPolygon2D tile) {
        if (tiles.isEmpty()) throw new IllegalStateException(CALIBRATION_NEEDED);

        int idxRef = tiles.indexOf(tile);
        if (idxRef == -1) return new OpenCVPoint2Wrapper(-1, -1);
        int yRef = idxRef / (verticalTiles - 2);
        int xRef = idxRef % (verticalTiles - 2);

        return new OpenCVPoint2Wrapper(xRef, yRef);
    }

    /**
     * Method for calibrating the PositionEvaluator
     *
     * @param imageForCalibration Image used for calibration containing the grid.
     */
    @Override
    public void calibrate(ImageWrapper<Mat> imageForCalibration) {
        if (horizontalTiles <= 0 || verticalTiles <= 0)
            throw new IllegalStateException("HorizontalTiles and verticalTiles are not set!");
        tiles.clear();
        Mat image = imageForCalibration.getImage();
        Size s = new Size();
        s.width = horizontalTiles - 1.0; // -1 because number of inner corner points are needed in findChessboardCorners
        s.height = verticalTiles - 1.0; // -1 because number of inner corner points are needed in findChessboardCorners

        MatOfPoint2f res = new MatOfPoint2f();

        try {
            // find positions of inner corner points
            boolean b = Calib3d.findChessboardCorners(image, s, res, Calib3d.CALIB_CB_ADAPTIVE_THRESH | Calib3d.CALIB_CB_FAST_CHECK | Calib3d.CALIB_CB_NORMALIZE_IMAGE);
            if (!b) throw new IllegalArgumentException("No fitting grid found.");

            Point[] points = res.toArray();
            // fill the tiles list with the quadrangles (represented as polygons because mostly not rectangles or squares!)
            for (int x = 0; x < s.width - 1; x++) {
                for (int y = 0; y < s.height - 1; y++) {
                    JavaPoint2D tl = pointTransformer.transformFrom(new OpenCVPoint2Wrapper(points[x + y * (horizontalTiles - 1)]));
                    JavaPoint2D tr = pointTransformer.transformFrom(new OpenCVPoint2Wrapper(points[(x + 1) + (y) * (horizontalTiles - 1)]));
                    JavaPoint2D br = pointTransformer.transformFrom(new OpenCVPoint2Wrapper(points[(x + 1) + (y + 1) * (horizontalTiles - 1)]));
                    JavaPoint2D bl = pointTransformer.transformFrom(new OpenCVPoint2Wrapper(points[(x) + (y + 1) * (horizontalTiles - 1)]));
                    JavaPolygon2D polygon = new JavaPolygon2D(tl, tr, br, bl);
                    tiles.add(polygon);
                }
            }
        } finally {
            res.release();
        }
    }

    /**
     * Method for evaluating e.g. the position of an object in the image
     *
     * @param image Image where Object should be found.
     * @return Position of the Object in the image (Position -1/-1 -&gt; no position found)
     */
    @Override
    public Point2Wrapper<Point> getPosition(ImageWrapper<Mat> image) {
        if (tiles.isEmpty()) throw new IllegalStateException(CALIBRATION_NEEDED);
        if (referenceImage == null) throw new IllegalStateException("ReferenceImage must not be null");

        RectangleWrapper<Rect, Point> rect = objectDetector.getBoundingBox(image);

        // get the the points of the differences´ boundingbox
        Point2Wrapper<Point> tl = rect.getTopLeftPoint();
        Point2Wrapper<Point> center = rect.getCenterPoint();
        Point2Wrapper<Point> br = rect.getBottomRightPoint();
        JavaPoint2D tr = new JavaPoint2D(br.getX(), tl.getY());
        JavaPoint2D bl = new JavaPoint2D(tl.getX(), br.getY());

        // get all affected tiles
        List<JavaPolygon2D> fittingAreas = new ArrayList<>();
        for (JavaPolygon2D poly : tiles) {
            if (poly.isInConvexHull(pointTransformer.transformFrom(tl)) || poly.isInConvexHull(pointTransformer.transformFrom(center)) || poly.isInConvexHull(pointTransformer.transformFrom(br)) || poly.isInConvexHull(tr) || poly.isInConvexHull(bl)) {
                fittingAreas.add(poly);
            }
        }

        // create one combined area to find the center of the object on the tiles

        List<JavaPoint2D> elements = new ArrayList<>();
        for (JavaPolygon2D poly : fittingAreas) {
            elements.addAll(poly.getPoints());
        }

        JavaPolygon2D area = new JavaPolygon2D(elements.toArray(new JavaPoint2D[0]));

        // find the tile containing the center of the combined area
        JavaPolygon2D tile = getAffectedTile(pointTransformer.transformTo(area.getCentroid()));
        if (tile == null) return new OpenCVPoint2Wrapper(-1, -1);
        return pointTransformer.transformTo(tile.getCentroid());
    }

    /**
     * Method for evaluating the offset of an object between two images
     *
     * @param ref     The reference image containing the object
     * @param current The current image containing the object
     * @return The offset between the object´s position in ref and the object´s position in current
     */
    @Override
    public TranslationOffsetInMM getOffset(ImageWrapper<Mat> ref, ImageWrapper<Mat> current) {
        if (tileWidth <= 0.0 || tileHeight <= 0.0)
            throw new IllegalStateException("Width and Height of the tiles must be set!");
        // get positions of the object
        Point2Wrapper<Point> positionRef = getPosition(ref);
        Point2Wrapper<Point> positionCur = getPosition(current);

        JavaPolygon2D polygonRef = getAffectedTile(positionRef);
        JavaPolygon2D polygonCur = getAffectedTile(positionCur);

        // get index of ref tile and normalize index to x,y tile position
        Point2Wrapper<Point> idxRef = getTileIndex(polygonRef);
        double yRef = idxRef.getY();
        double xRef = idxRef.getX();

        // get index of current tile and normalize index to x,y tile position
        Point2Wrapper<Point> idxCur = getTileIndex(polygonCur);
        double yCur = idxCur.getY();
        double xCur = idxCur.getX();

        // calculate x and y tile translation
        // change x translation sign because tiles are sorted like:
        // 2 1 0
        // 5 4 3
        // 9 8 7
        double xTileDif = -(xCur - xRef);
        double yTileDif = yCur - yRef;

        // calculate millimeter translation based on tile dimensions
        double xMMDif = xTileDif * tileWidth;
        double yMMDif = yTileDif * tileHeight;

        // convert -0.0 to 0.0 if necessary
        if (Math.abs(xMMDif) < 0.000000000000001) xMMDif = 0.0;
        if (Math.abs(yMMDif) < 0.000000000000001) yMMDif = 0.0;

        // calculate pixel translation based on positions
        double xPixDif = positionCur.getX() - positionRef.getX();
        double yPixDif = positionCur.getY() - positionRef.getY();


        return new TranslationOffsetInMM(xPixDif, yPixDif, xMMDif, yMMDif);
    }
}
