/*
 * Copyright (c) 2021 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.imaging.api.positioning;

import science.aist.imaging.api.domain.twodimensional.JavaPolygon2D;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.Point2Wrapper;

import java.util.List;

/**
 * <p>Interface which provides functionality to evaluate e.g. the position of an object in the image
 * based on a given grid for calibration</p>
 *
 * @param <I> Type of Image wrapped by ImageWrapper
 * @param <P> Type of Point wrapped by Point2Wrapper
 * @author Christoph Praschl
 * @since 1.0
 */
public interface GridbasedPositionEvaluator<I, P> extends PositionEvaluator<I, P> {
    /**
     * @return Gets the number of tiles along the x-axis
     */
    int getHorizontalTiles();

    /**
     * @param horizontalTiles The number of tiles along the x-axis
     */
    void setHorizontalTiles(int horizontalTiles);

    /**
     * @return Gets the number of tiles along the y-axis
     */
    int getVerticalTiles();

    /**
     * @param verticalTiles The number of tiles along the y-axis
     */
    void setVerticalTiles(int verticalTiles);

    /**
     * @return Gets the width of one tile in millimeters
     */
    double getTileWidth();

    /**
     * @param tileWidth The width of one tile in millimeters
     */
    void setTileWidth(double tileWidth);

    /**
     * @return Gets the height of one tile in millimeters
     */
    double getTileHeight();

    /**
     * @param tileHeight The height of one tile in millimeters
     */
    void setTileHeight(double tileHeight);

    /**
     * @return Gets the tiles used for position evaluation
     */
    List<JavaPolygon2D> getTiles();

    /**
     * @param tiles Tiles used for position evaluation
     */
    void setTiles(List<JavaPolygon2D> tiles);

    /**
     * @param referenceImage Reference image which is used to determine the position of an object
     */
    void setReferenceImage(ImageWrapper<I> referenceImage);

    /**
     * Gets the tile which contains the given position.
     *
     * @param position The position for which the tile should be returned
     * @return The tile containing the position; Or null if position is not on a tile.
     */
    JavaPolygon2D getAffectedTile(Point2Wrapper<P> position);

    /**
     * Gets the x and y index of the given tile
     *
     * @param tile Tile for which index should be returned
     * @return The index of the tile or an index -1/-1 if tile was not found
     */
    Point2Wrapper<P> getTileIndex(JavaPolygon2D tile);

}
