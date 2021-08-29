package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Tile;

/**
 * A factory that instantiates a new building
 */
public interface BuildingFactory {
    /**
     * @param tile where it should be build on
     * @return the created building
     */
    Building create(Tile tile);
}
