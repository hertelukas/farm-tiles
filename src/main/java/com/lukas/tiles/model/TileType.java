package com.lukas.tiles.model;

import javafx.scene.paint.Color;

public enum TileType {
    Water(Color.BLUE),
    Gras(Color.GREENYELLOW);

    private final Color color;

    TileType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
