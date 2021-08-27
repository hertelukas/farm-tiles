package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Farmer;
import com.lukas.tiles.model.TileType;

import java.util.Set;

public interface BuildPredicate {
    boolean test(TileType standingOn, Set<TileType> neighbours, Farmer farmer);
}
