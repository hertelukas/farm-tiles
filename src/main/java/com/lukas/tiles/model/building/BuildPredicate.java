package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.TileType;

import java.util.Set;

/**
 * Predicate that decides whether a Building can be build given the tile, the neighboring tiles and the farmer
 */
public interface BuildPredicate {
    /**
     * @param standingOn the tileType where the building should be placed on
     * @param neighbours the neighbouring tiles
     * @param farmer     the farmer who tries to build a given building
     * @return whether a building can be build there
     */
    boolean test(TileType standingOn, Set<TileType> neighbours, Farmer farmer);
}
