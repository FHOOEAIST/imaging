/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.microsoftcognitiveservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.twodimensional.JavaPoint2D;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.facedetection.FaceInformation;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author Andreas Pointner
 */
@ContextConfiguration("classpath:microsoftcognitiveservices-config.xml")
public class FaceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MicrosoftCognitiveServiceFaceDetection faceDetection;

    @Autowired
    private ImageFactory<short[][][]> imageFactory;


    /**
     * MicrosoftCognitiveServiceFaceDetection.detectFaces()
     * <p>
     * Only test it with a MockRestService, because the api-key is only 30 days valid
     */
    @Test
    void testDetectFaceMock() {
        // given
        ImageWrapper<short[][][]> ji = imageFactory.getRandomImage(1, 1, ChannelType.RGB, new Random(), 0, 255, true);
        MockRestServiceServer server = MockRestServiceServer.bindTo(faceDetection.getRestTemplate()).build();
        server.expect(requestTo("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("[{\"faceId\":\"e3fe492f-f754-43b6-819c-abfb1fb53350\",\"faceRectangle\":{\"top\":404,\"left\":279,\"width\":134,\"height\":134}}]", MediaType.APPLICATION_JSON));

        // when
        Collection<FaceInformation<short[][][]>> faces = faceDetection.detectFaces(ji);

        // then
        server.verify();
        Optional<FaceInformation<short[][][]>> fiO = faces.stream().findFirst();
        Assert.assertTrue(fiO.isPresent());
        FaceInformation<short[][][]> fi = fiO.get();
        Assert.assertEquals(fi.getBoundingBox().getTopLeft(), new JavaPoint2D(279, 404));
        Assert.assertEquals(fi.getBoundingBox().getBottomRight(), new JavaPoint2D(413, 538));
    }

    /**
     * MicrosoftCognitiveServiceFaceDetection.verifyFace()
     * <p>
     * Only test it with a MockRestService, because the api-key is only 30 days valid
     */
    @Test
    void testVerifyFaceMock() {
        // given
        ImageWrapper<short[][][]> ji = imageFactory.getRandomImage(1, 1, ChannelType.RGB, new Random(), 0, 255, true);
        MockRestServiceServer server = MockRestServiceServer.bindTo(faceDetection.getRestTemplate()).build();
        server.expect(requestTo("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("[{\"faceId\":\"e3fe492f-f754-43b6-819c-abfb1fb53350\",\"faceRectangle\":{\"top\":404,\"left\":279,\"width\":134,\"height\":134}}]", MediaType.APPLICATION_JSON));
        server.expect(requestTo("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/verify"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"isIdentical\":true,\"confidence\":1.0}", MediaType.APPLICATION_JSON));
        Collection<FaceInformation<short[][][]>> faces = faceDetection.detectFaces(ji);
        FaceInformation<short[][][]> fi = faces.stream().findFirst().get();

        // when
        double conf = faceDetection.verifyFace(fi, fi);

        // then
        Assert.assertEquals(conf, 1.0, 0.01);
    }

    /**
     * MicrosoftCognitiveServiceFaceDetection.detectFaces() - Exception
     * <p>
     * Only test it with a MockRestService, because the api-key is only 30 days valid
     */
    @Test(expectedExceptions = HttpClientErrorException.class)
    void testDetectFaceMockException() {
        // given
        ImageWrapper<short[][][]> ji = imageFactory.getRandomImage(1, 1, ChannelType.RGB, new Random(), 0, 255, true);
        MockRestServiceServer server = MockRestServiceServer.bindTo(faceDetection.getRestTemplate()).build();
        server.expect(requestTo("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        // when
        faceDetection.detectFaces(ji);

        // then
        server.verify();
    }
}
