package com.lukas.tiles.view.game;

import com.lukas.tiles.model.Tile;

/**
 * A functional interface that can handle a tile
 */
public interface TileSelectedHandler {
    void handle(Tile tile);
}
