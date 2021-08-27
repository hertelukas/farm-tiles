package com.lukas.tiles.model.building;

import com.lukas.tiles.model.Tile;

public interface BuildingFactory {
    Building create(Tile tile);
}
