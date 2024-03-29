package com.lukas.tiles.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Representation of all different tile types
 */
public enum TileType implements Serializable {
    Water(Color.web("4EADD3")),
    Grass(Color.web("496000")),
    Coastal(Color.web("EAB47C")),
    Rock(Color.web("53565b"));

    private final Color color;

    TileType(Color color) {
        this.color = color;
    }

    /**
     * @return The Color of a tile type.
     */
    public Color getColor() {
        return color;
    }
}
