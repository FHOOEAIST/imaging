/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.opencv.imageprocessing.transformations;

import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.twodimensional.JavaRectangleRotated2D;
import science.aist.imaging.api.domain.wrapper.RotatedRectangleWrapper;
import science.aist.imaging.opencv.imageprocessing.OpenCVTest;
import science.aist.imaging.opencv.imageprocessing.transformers.OpenCVRotatedRectangleWrapperToJavaRectangleRotatedTransformer;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVPoint2Wrapper;
import science.aist.imaging.opencv.imageprocessing.wrapper.OpenCVRotatedRectangleWrapper;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.jack.general.transformer.Transformer;

/**
 * <p>Tests {@link OpenCVRotatedRectangleWrapperToJavaRectangleRotatedTransformer}</p>
 *
 * @author Andreas Pointner
 */
public class OpenCVRotatedRectangleWrapperToJavaRectangleRotated2DTransformerTest extends OpenCVTest {

    @Autowired
    private Transformer<RotatedRectangleWrapper<RotatedRect, Point>, JavaRectangleRotated2D> rotatedRectangleWrapperJavaRectangleRotatedTransformer;

    @Test
    public void testTransformTo() {
        // given
        JavaRectangleRotated2D jrr = new JavaRectangleRotated2D(new JavaPoint2D(2, 3), 2.0, 2.0, Math.toRadians(-90));

        // when
        RotatedRectangleWrapper<RotatedRect, Point> rotatedRectangle = rotatedRectangleWrapperJavaRectangleRotatedTransformer.transformTo(jrr);

        // then
        Assert.assertEquals(rotatedRectangle.getCenterPoint().getX(), 2.0, 0.001);
        Assert.assertEquals(rotatedRectangle.getCenterPoint().getY(), 3.0, 0.001);
        Assert.assertEquals(rotatedRectangle.getWidth(), 2.0, 0.001);
        Assert.assertEquals(rotatedRectangle.getHeight(), 2.0, 0.001);
        Assert.assertEquals(Math.toDegrees(rotatedRectangle.getRotation()), -90.0, 0.001);
    }

    @Test
    public void testTransformFrom() {
        // given
        RotatedRectangleWrapper<RotatedRect, Point> rotatedRectangle = OpenCVRotatedRectangleWrapper.create(new OpenCVPoint2Wrapper(2, 3), 2, 2, Math.toRadians(-90));

        // when
        JavaRectangleRotated2D javaRectangleRotated2D = rotatedRectangleWrapperJavaRectangleRotatedTransformer.transformFrom(rotatedRectangle);

        // then
        Assert.assertEquals(javaRectangleRotated2D.getBottomLeft().getX(), 3.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getBottomLeft().getY(), 4.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getTopLeft().getX(), 1.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getTopLeft().getY(), 4.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getTopRight().getX(), 1.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getTopRight().getY(), 2.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getBottomRight().getX(), 3.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getBottomRight().getY(), 2.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getCenterPoint().getX(), 2.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getCenterPoint().getY(), 3.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getWidth(), 2.0, 0.001);
        Assert.assertEquals(javaRectangleRotated2D.getHeight(), 2.0, 0.001);
        Assert.assertEquals(Math.toDegrees(javaRectangleRotated2D.getRotation()), -90.0, 0.001);
    }
}
