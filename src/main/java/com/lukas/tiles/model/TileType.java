package com.lukas.tiles.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

public enum TileType implements Serializable {
    Water(Color.BLUE),
    Grass(Color.GREEN),
    Coastal(Color.YELLOW),
    Rock(Color.GREY);

    private final Color color;

    TileType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
