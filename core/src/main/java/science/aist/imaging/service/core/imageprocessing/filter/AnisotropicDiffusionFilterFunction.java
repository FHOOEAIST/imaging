/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.filter;

import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.Image8ByteFactory;
import science.aist.imaging.api.ImageFunction;
import science.aist.imaging.api.domain.wrapper.implementation.TypeBasedImageFactoryFactory;
import science.aist.imaging.api.typecheck.TypeChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import science.aist.jack.data.Pair;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.Stream;

/**
 * <p>Implementation of anisotropic diffusion</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
@Setter
public class AnisotropicDiffusionFilterFunction<T, R> implements ImageFunction<T, R> {
    private static final TypeChecker typeChecker = new TypeChecker(ChannelType.GREYSCALE);

    private static final double[][] H_N = new double[][]{{0.0, 1.0, 0.0}, {0.0, -1.0, 0.0}, {0.0, 0.0, 0.0}};
    private static final double[][] H_S = new double[][]{{0.0, 0.0, 0.0}, {0.0, -1.0, 0.0}, {0.0, 1.0, 0.0}};
    private static final double[][] H_E = new double[][]{{0.0, 0.0, 0.0}, {0.0, -1.0, 1.0}, {0.0, 0.0, 0.0}};
    private static final double[][] H_W = new double[][]{{0.0, 0.0, 0.0}, {1.0, -1.0, 0.0}, {0.0, 0.0, 0.0}};
    private static final double[][] H_NE = new double[][]{{0.0, 0.0, 1.0}, {0.0, -1.0, 0.0}, {0.0, 0.0, 0.0}};
    private static final double[][] H_SE = new double[][]{{0.0, 0.0, 0.0}, {0.0, -1.0, 0.0}, {0.0, 0.0, 1.0}};
    private static final double[][] H_SW = new double[][]{{0.0, 0.0, 0.0}, {0.0, -1.0, 0.0}, {1.0, 0.0, 0.0}};
    private static final double[][] H_NW = new double[][]{{1.0, 0.0, 0.0}, {0.0, -1.0, 0.0}, {0.0, 0.0, 0.0}};

    private static final double DY = 1.0;
    private static final double DD = Math.sqrt(2.0);

    private BiFunction<ImageWrapper<double[][][]>, double[][], ImageWrapper<double[][][]>> convolutionFunction;
    private int numberOfIterations = 1;
    private double kappa = 30.0;
    private DiffusionType diffusionType = DiffusionType.TYPE_1;

    private ImageFactory<R> provider;

    public AnisotropicDiffusionFilterFunction(ImageFactory<R> provider) {
        this.provider = provider;
        ConvolveFunction<double[][][], double[][][]> convolve8Byte = new ConvolveFunction<>(TypeBasedImageFactoryFactory.getImageFactory(double[][][].class));
        convolve8Byte.setNormalize(false);
        convolutionFunction = convolve8Byte;
    }

    private static double diffuse(double weight, double[][][] c, double[][][] nabla, int x, int y) {
        return (1.0 / (weight * weight)) * c[y][x][0] * nabla[y][x][0];
    }

    @Override
    public ImageWrapper<R> apply(ImageWrapper<T> imageWrapper) {
        typeChecker.accept(imageWrapper);
        int width = imageWrapper.getWidth();
        int height = imageWrapper.getHeight();

        ImageWrapper<double[][][]> inputImageWrapperDouble = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width);

        imageWrapper.copyTo(inputImageWrapperDouble);
        double[][][] inputImage = inputImageWrapperDouble.getImage();

        for (int iter = 0; iter < numberOfIterations; iter++) {
            double[][][] nablaN = convolutionFunction.apply(inputImageWrapperDouble, H_N).getImage();
            double[][][] nablaS = convolutionFunction.apply(inputImageWrapperDouble, H_S).getImage();
            double[][][] nablaW = convolutionFunction.apply(inputImageWrapperDouble, H_W).getImage();
            double[][][] nablaE = convolutionFunction.apply(inputImageWrapperDouble, H_E).getImage();
            double[][][] nablaNE = convolutionFunction.apply(inputImageWrapperDouble, H_NE).getImage();
            double[][][] nablaSE = convolutionFunction.apply(inputImageWrapperDouble, H_SE).getImage();
            double[][][] nablaSW = convolutionFunction.apply(inputImageWrapperDouble, H_SW).getImage();
            double[][][] nablaNW = convolutionFunction.apply(inputImageWrapperDouble, H_NW).getImage();

            double[][][] cN = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cS = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cW = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cE = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cNE = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cSE = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cSW = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();
            double[][][] cNW = TypeBasedImageFactoryFactory.getImageFactory(double[][][].class).getImage(height, width).getImage();

            Map<double[][][], double[][][]> map = Stream.of(
                    Pair.of(cN, nablaN),
                    Pair.of(cS, nablaS),
                    Pair.of(cW, nablaW),
                    Pair.of(cE, nablaE),
                    Pair.of(cNE, nablaNE),
                    Pair.of(cSE, nablaSE),
                    Pair.of(cSW, nablaSW),
                    Pair.of(cNW, nablaNW)
            ).collect(Pair.toMap());

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    final int innerY = y;
                    final int innerX = x;
                    map.forEach((c, nabla) -> c[innerY][innerX][0] = diffusionType.getFilterFunction().applyAsDouble(nabla[innerY][innerX][0], kappa));
                }
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    inputImage[y][x][0] = inputImage[y][x][0] + 1.0 / 7.0 * (
                            diffuse(DY, cN, nablaN, x, y) +
                                    diffuse(DY, cS, nablaS, x, y) +
                                    diffuse(DY, cW, nablaW, x, y) +
                                    diffuse(DY, cE, nablaE, x, y) +
                                    diffuse(DD, cNE, nablaNE, x, y) +
                                    diffuse(DD, cSE, nablaSE, x, y) +
                                    diffuse(DD, cSW, nablaSW, x, y) +
                                    diffuse(DD, cNW, nablaNW, x, y)
                    );
                }
            }
        }

        ImageWrapper<R> provide = provider.getImage(height, width);
        inputImageWrapperDouble.copyTo(provide);
        return provide;
    }

    @Getter
    @AllArgsConstructor
    enum DiffusionType {
        TYPE_1((double nabla, double ka) -> Math.exp(-Math.pow(-nabla / ka, 2))),
        TYPE_2((double nabla, double ka) -> 1.0 / (1 + Math.pow(nabla / ka, 2)));

        private final DoubleBinaryOperator filterFunction;
    }
}
