/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.microsoftcognitiveservices;

import science.aist.imaging.api.domain.twodimensional.JavaRectangle2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.CustomLog;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import science.aist.imaging.api.facedetection.FaceDetection;
import science.aist.imaging.api.facedetection.FaceInformation;
import science.aist.jack.general.transformer.Transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <p>This class provides face detection functionality using microsoft cognitive service face api</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@CustomLog
public class MicrosoftCognitiveServiceFaceDetection implements FaceDetection<short[][][]> {
    /**
     * RestTemplate which is used for perform requests.
     */
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * Reference to Transformer to transform the java image into a byte array
     */
    @Setter
    private Transformer<byte[], ImageWrapper<short[][][]>> byteArrayImage2ByteTransformer;
    /**
     * API Key to use microsoft face api
     */
    @Value("${microsoft.cognitiveservice.facedetection.ocpapimsubscriptionkey}")
    private String ocpApimSubscriptionKey;

    /**
     * http schema e.g. http or https
     */
    @Value("${microsoft.cognitiveservice.facedetection.schema}")
    private String schema;

    /**
     * hose name e.g www.fh-ooe.at
     */
    @Value("${microsoft.cognitiveservice.facedetection.host}")
    private String host;

    /**
     * path to actionDetection e.g. /action/detect
     */
    @Value("${microsoft.cognitiveservice.facedetection.actiondetect}")
    private String actionDetect;

    /**
     * path to actionVerify e.g. /action/verify
     */
    @Value("${microsoft.cognitiveservice.facedetection.actionverify}")
    private String actionVerify;

    /**
     * This function provides default template functionality for calling microsoft cognitive services face api.
     *
     * @param action                       which action should be called
     * @param headersConsumer              consumer to set additional http header elements
     * @param uriComponentsBuilderConsumer uri components consumer, to set additional url parameters
     * @param httpEntityBody               httpEntityBody supplier, which provides the body of the request
     * @param nodeConsumer                 node consumer which gets the json decoded body of the response.
     */
    private <T> void execute(String action, Consumer<HttpHeaders> headersConsumer, Consumer<UriComponentsBuilder> uriComponentsBuilderConsumer, Supplier<T> httpEntityBody, Consumer<? super JsonNode> nodeConsumer) {
        try {
            // Initialize the Rest template
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Set the Headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Ocp-Apim-Subscription-Key", ocpApimSubscriptionKey);
            headersConsumer.accept(headers);

            // Build the URI
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme(schema).host(host).path(action);
            uriComponentsBuilderConsumer.accept(uriComponentsBuilder);

            // Execute the Request
            ResponseEntity<String> response = restTemplate.exchange(uriComponentsBuilder.build().toUri(), HttpMethod.POST, new HttpEntity<>(httpEntityBody.get(), headers), String.class);
            log.debug(response.toString());

            // Map the Response to a JsonNode
            nodeConsumer.accept(new ObjectMapper().readTree(response.getBody()));

        } catch (HttpClientErrorException hcee) {
            // Handle HTTP Status Code exception
            int statusCode = hcee.getRawStatusCode();
            String statusText = hcee.getStatusText();
            log.error(statusCode + "(" + statusText + ")", hcee);
            throw hcee;
        } catch (Exception e) {
            // Wrap managed exceptions with runtime exception
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Detects a face in the image, and returns a FaceInformation object to describe it.
     * https://westus.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395236
     *
     * @param ji the image where the face should be detected
     * @return the information about the detected face
     */
    @Override
    public Collection<FaceInformation<short[][][]>> detectFaces(ImageWrapper<short[][][]> ji) {
        Collection<FaceInformation<short[][][]>> res = new ArrayList<>();
        execute(
                // Call action detect
                actionDetect,
                // Set header to OCTET_STREAM (sending image byte[])
                headers -> headers.setContentType(MediaType.APPLICATION_OCTET_STREAM),
                // Adding URI Params
                uriBuilder -> uriBuilder.queryParam("returnFaceId", true).queryParam("returnFaceLandmarks", false),
                // Setting Request Body to the image
                () -> byteArrayImage2ByteTransformer.transformTo(ji),
                // Extract the information from the json response
                root -> {
                    for (JsonNode node : root) {
                        FaceInformation<short[][][]> fi = new FaceInformation<>();
                        fi.setId(node.at("/faceId").asText());
                        int x1 = node.at("/faceRectangle/left").asInt();
                        int y1 = node.at("/faceRectangle/top").asInt();
                        int x2 = x1 + node.at("/faceRectangle/width").asInt();
                        int y2 = y1 + node.at("/faceRectangle/height").asInt();
                        fi.setBoundingBox(new JavaRectangle2D(x1, y1, x2, y2));
                        fi.setImage(ji);
                        res.add(fi);
                    }
                }
        );
        return res;
    }

    /**
     * Calculates a confidence value, if there is the same person on img1 and on img2
     * https://westus.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f3039523a
     *
     * @param face1 first face
     * @param face2 second face
     * @return confidence value if the two faces on the images are the same
     */
    @Override
    @SuppressWarnings("java:S125")
    public double verifyFace(FaceInformation<short[][][]> face1, FaceInformation<short[][][]> face2) {
        AtomicLong al = new AtomicLong();
        execute(
                // Call action verify
                actionVerify,
                // Set Content Type to Json
                headers -> headers.setContentType(MediaType.APPLICATION_JSON),
                // No URL Params needed
                uriComponentsBuilder -> { /* Noting to do here*/ },
                // Create Json Body: {faceId1: abc, faceId2: def}
                () -> new ObjectMapper().createObjectNode().put("faceId1", face1.getId()).put("faceId2", face2.getId()).toString(),
                // Double cannot be set in lambda expression use AtomicLong and convert the double into it
                root -> al.set(Double.doubleToLongBits(root.at("/confidence").asDouble()))
        );
        // Return a double, therefore convert the long back to double
        return Double.longBitsToDouble(al.get());
    }

    /**
     * This function is only used in test classes therefore provide a package-protected function only.
     * This is needed for
     *
     * @return the rest template which is used for the request
     */
    RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
