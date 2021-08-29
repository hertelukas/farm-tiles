package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Tile;

/**
 * A functional interface that can handle a tile
 */
public interface TileSelectedHandler {
    /**
     * @param tile that should be handled
     */
    void handle(Tile tile);
}
