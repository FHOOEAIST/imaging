/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.service.core.imageprocessing.registration;

import lombok.AllArgsConstructor;
import science.aist.imaging.api.domain.offset.RotationOffset;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.fitnessfunction.AbstractFitnessFunction;
import science.aist.imaging.service.core.imageprocessing.transformation.TransformFunction;
import science.aist.jack.math.MathUtils;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * <p>Registers two images</p>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
@AllArgsConstructor
public class RegistrationImageFunction<T, R> implements BiFunction<ImageWrapper<T>, ImageWrapper<R>, RotationOffset> {
    private final Object synchronizer = new Object();
    private final double stepSize;
    private final double rotSize;
    private final int stepsPerDimension;
    private final AbstractFitnessFunction fitnessFunction;
    private final TransformFunction<R> imageTransformer;

    private static double getDouble(AtomicLong val) {
        return Double.longBitsToDouble(val.get());
    }

    private static void setDoubleToAtomicLong(AtomicLong val, double toSet) {
        val.set(Double.doubleToLongBits(toSet));
    }

    @Override
    public RotationOffset apply(ImageWrapper<T> ref, ImageWrapper<R> test) {
        final AtomicLong bestTx = new AtomicLong(0);
        final AtomicLong bestTy = new AtomicLong(0);
        final AtomicLong bestRot = new AtomicLong(0);
        final AtomicLong bestError = new AtomicLong(Double.doubleToLongBits(fitnessFunction.applyAsDouble(ref, test)));

        if (MathUtils.equals(Double.longBitsToDouble(bestError.get()), fitnessFunction.getBestPossibleError())) {
            return new RotationOffset(getDouble(bestTx), getDouble(bestTy), getDouble(bestError), getDouble(bestRot));
        }

        IntStream.rangeClosed(-stepsPerDimension, stepsPerDimension).parallel().forEach(x -> {
            for (int y = -stepsPerDimension; y <= stepsPerDimension; y++) {
                for (int r = -stepsPerDimension; r <= stepsPerDimension; r++) {
                    double tx = x * stepSize;
                    double ty = y * stepSize;
                    double rot = r * rotSize;

                    ImageWrapper<R> transformedImg = imageTransformer.apply(test, new RotationOffset(tx, ty, rot));

                    double currError = fitnessFunction.applyAsDouble(ref, transformedImg);
                    synchronized (synchronizer) {
                        if ((fitnessFunction.isLowerErrorValueBetter() && currError < getDouble(bestError)) || (!fitnessFunction.isLowerErrorValueBetter() && currError > getDouble(bestError))) {
                            setDoubleToAtomicLong(bestError, currError);
                            setDoubleToAtomicLong(bestTx, tx);
                            setDoubleToAtomicLong(bestTy, ty);
                            setDoubleToAtomicLong(bestRot, rot);
                        }
                    }
                }
            }
        });

        return new RotationOffset(getDouble(bestTx), getDouble(bestTy), getDouble(bestError), getDouble(bestRot));
    }

}
