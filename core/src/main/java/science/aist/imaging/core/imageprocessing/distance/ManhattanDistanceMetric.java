/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.core.imageprocessing.distance;

/**
 * <p>Manhattan Distance implementation of the {@link AbstractDistanceMetric}</p>
 *
 * <p>A manhattan distance with masksize = 5 would look like the following:</p>
 * <table>
 *   <caption>manhattan distance with masksize = 5</caption>
 *   <tr>
 *     <th>4</th>
 *     <th>3</th>
 *     <th>2</th>
 *     <th>3</th>
 *     <th>4</th>
 *   </tr>
 *   <tr>
 *     <td>3</td>
 *     <td>2</td>
 *     <td>1</td>
 *     <td>2</td>
 *     <td>3</td>
 *   </tr>
 *   <tr>
 *     <td>2</td>
 *     <td>1</td>
 *     <td>0</td>
 *     <td>1</td>
 *     <td>2</td>
 *   </tr>
 *   <tr>
 *     <td>3</td>
 *     <td>2</td>
 *     <td>1</td>
 *     <td>2</td>
 *     <td>3</td>
 *   </tr>
 *   <tr>
 *     <td>4</td>
 *     <td>3</td>
 *     <td>2</td>
 *     <td>3</td>
 *     <td>4</td>
 *   </tr>
 * </table>
 *
 * @author Christoph Praschl
 * @since 1.0
 */
public class ManhattanDistanceMetric extends AbstractDistanceMetric {
    @Override
    protected double calculateMaskValue(int x, int y) {
        return Math.abs(x) + Math.abs(y);
    }
}
