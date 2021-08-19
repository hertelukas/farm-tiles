package com.lukas.tiles.model;

import javafx.scene.paint.Color;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return selected == tile.selected && tileType == tile.tileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tileType, selected);
    }
}
