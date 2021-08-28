package com.lukas.tiles.model.setup;

/**
 * Different types of maps that can be generated
 *
 * @see com.lukas.tiles.generator.MapGenerator
 */
public enum MapType {
    Random,
    Continents,
    Islands,
    Pangea;

    /**
     * @return the default map type
     */
    public static MapType getDefault() {
        return Continents;
    }
}
