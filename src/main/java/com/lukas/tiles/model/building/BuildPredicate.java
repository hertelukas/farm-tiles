package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.TileType;

import java.util.Set;

/**
 * Predicate that decides whether a Building can be build given the tile, the neighboring tiles and the farmer
 */
public interface BuildPredicate {
    boolean test(TileType standingOn, Set<TileType> neighbours, Farmer farmer);
}
