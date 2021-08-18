package com.lukas.tiles.model;

public class Tile {
    private TileType tileType;

    public Tile(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }
}
