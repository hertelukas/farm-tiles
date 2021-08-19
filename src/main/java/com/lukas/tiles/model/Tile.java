package com.lukas.tiles.model;

import javafx.scene.paint.Color;

public class Tile {
    private final TileType tileType;
    private boolean selected;

    public Tile(TileType tileType) {
        this.tileType = tileType;
        this.selected = false;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Color getColor() {
        if (isSelected()) {
            return Color.RED;
        }
        return tileType.getColor();
    }
}
