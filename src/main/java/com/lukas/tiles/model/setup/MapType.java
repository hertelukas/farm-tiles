package com.lukas.tiles.model.setup;

public enum MapType {
    Random,
    Continents,
    Islands,
    Pangea;

    public static MapType getDefault() {
        return Continents;
    }
}
