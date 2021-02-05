/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.opencv.imageprocessing.positioning;

import science.aist.imaging.api.domain.offset.TranslationOffsetInMM;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;
import science.aist.imaging.service.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.service.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testing OpenCVGridbasedPositionEvaluator
 *
 * @author Christoph Praschl
 */
public class OpenCVGridbasedPositionEvaluatorTest extends OpenCVTest {
    @Autowired
    private OpenCVGridbasedPositionEvaluator positionEvaluator;

    @Test
    void testCalibrate() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/1.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(19);
        positionEvaluator.setVerticalTiles(17);
        positionEvaluator.setTileHeight(10);
        positionEvaluator.setTileWidth(10);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test
    void testCalibrate2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/2.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void testCalibrate3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/logo/original.tif");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);

        // when
        positionEvaluator.calibrate(img1);

        // then - exception
    }

    @Test
    void testCalibrate4() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/4.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(36);
        positionEvaluator.setVerticalTiles(54);
        positionEvaluator.setTileHeight(5);
        positionEvaluator.setTileWidth(5);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test
    void testCalibrate5() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/5.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(76);
        positionEvaluator.setVerticalTiles(109);
        positionEvaluator.setTileHeight(2.5);
        positionEvaluator.setTileWidth(2.5);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test
    void testCalibrate6() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/6.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(76);
        positionEvaluator.setVerticalTiles(109);
        positionEvaluator.setTileHeight(2.5);
        positionEvaluator.setTileWidth(2.5);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test
    void testCalibrate7() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/7.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(13);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test
    void testCalibrate8() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/8.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(13);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);

        // when
        positionEvaluator.calibrate(img1);

        // then
        Assert.assertFalse(positionEvaluator.getTiles().isEmpty());
    }

    @Test
    void testGetPosition() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/3_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/3_object.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);
        positionEvaluator.calibrate(img1);

        // when
        Point2Wrapper<Point> position = positionEvaluator.getPosition(img3);

        // then
        long x = Math.round(position.getX());
        long y = Math.round(position.getY());
        Assert.assertEquals(x, 785);
        Assert.assertEquals(y, 595);
    }

    @Test
    void testGetPosition2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/9.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/9_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/9_object.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(36);
        positionEvaluator.setVerticalTiles(54);
        positionEvaluator.setTileHeight(5);
        positionEvaluator.setTileWidth(5);
        positionEvaluator.calibrate(img1);

        // when
        Point2Wrapper<Point> position = positionEvaluator.getPosition(img3);

        // then
        long x = Math.round(position.getX());
        long y = Math.round(position.getY());
        Assert.assertEquals(x, 647);
        Assert.assertEquals(y, 626);
    }

    @Test
    void testGetOffset() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/3_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/3_fakepos2.jpg");
        ImageWrapper<Mat> img4 = loadImageFromClassPath("/grid/3_fakepos3.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);
        positionEvaluator.calibrate(img1);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img3, img4);

        // then
        Assert.assertEquals(offset.getxOffsetInMM(), 0.0);
        Assert.assertEquals(offset.getyOffsetInMM(), -80.0);
    }

    @Test
    void testGetOffset2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/3_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/3_fakepos1.jpg");
        ImageWrapper<Mat> img4 = loadImageFromClassPath("/grid/3_fakepos5.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);
        positionEvaluator.calibrate(img1);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img3, img4);

        // then
        Assert.assertEquals(offset.getxOffsetInMM(), 20.0);
        Assert.assertEquals(offset.getyOffsetInMM(), 0.0);
    }

    @Test
    void testGetOffset3() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/3_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/3_fakepos2.jpg");
        ImageWrapper<Mat> img4 = loadImageFromClassPath("/grid/3_fakepos4.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);
        positionEvaluator.calibrate(img1);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img3, img4);

        // then
        Assert.assertEquals(offset.getxOffsetInMM(), -40.0);
        Assert.assertEquals(offset.getyOffsetInMM(), 20.0);
    }

    @Test
    void testGetOffset4() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/3_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/3_fakepos1.jpg");
        ImageWrapper<Mat> img4 = loadImageFromClassPath("/grid/3_fakepos1.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);
        positionEvaluator.calibrate(img1);


        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img3, img4);

        // then
        Assert.assertEquals(offset.getxOffsetInMM(), 0.0);
        Assert.assertEquals(offset.getyOffsetInMM(), 0.0);
    }

    @Test
    void testGetOffset5() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/9.jpg");
        ImageWrapper<Mat> img2 = loadImageFromClassPath("/grid/9_ref.jpg");
        ImageWrapper<Mat> img3 = loadImageFromClassPath("/grid/9_object.jpg");
        ImageWrapper<Mat> img4 = loadImageFromClassPath("/grid/9_object2.jpg");
        positionEvaluator.setReferenceImage(img2);
        positionEvaluator.setHorizontalTiles(36);
        positionEvaluator.setVerticalTiles(54);
        positionEvaluator.setTileHeight(5);
        positionEvaluator.setTileWidth(5);
        positionEvaluator.calibrate(img1);

        // when
        TranslationOffsetInMM offset = positionEvaluator.getOffset(img3, img4);

        // then
        Assert.assertEquals(offset.getxOffsetInMM(), 85.0);
        Assert.assertEquals(offset.getyOffsetInMM(), 5.0);
    }

    @Test
    void testGetAffectedTile() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);

        positionEvaluator.calibrate(img1);

        // when
        JavaPolygon2D jp = positionEvaluator.getAffectedTile(new OpenCVPoint2Wrapper(792.0, 598.0));

        // then
        Assert.assertNotNull(jp);
        Assert.assertEquals(jp.getSize(), 4);
        JavaPoint2D center = jp.getCentroid();
        long x = Math.round(center.getX());
        long y = Math.round(center.getY());
        Assert.assertEquals(x, 785);
        Assert.assertEquals(y, 595);
    }

    @Test
    void testGetAffectedTile2() {
        // given
        ImageWrapper<Mat> img1 = loadImageFromClassPath("/grid/3.jpg");
        positionEvaluator.setReferenceImage(null);
        positionEvaluator.setHorizontalTiles(8);
        positionEvaluator.setVerticalTiles(5);
        positionEvaluator.setTileHeight(20);
        positionEvaluator.setTileWidth(20);

        positionEvaluator.calibrate(img1);

        // when
        JavaPolygon2D jp = positionEvaluator.getAffectedTile(new OpenCVPoint2Wrapper(1000, 598.0));

        // then
        Assert.assertNull(jp);
    }
}
