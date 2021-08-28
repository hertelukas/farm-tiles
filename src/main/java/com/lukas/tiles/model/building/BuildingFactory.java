package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Tile;

/**
 * A factory that instantiates a new building
 */
public interface BuildingFactory {
    Building create(Tile tile);
}
