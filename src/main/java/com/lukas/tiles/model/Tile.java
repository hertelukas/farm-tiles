package com.lukas.tiles.model;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Tile {
    private TileType tileType;
    private boolean selected;
    private final int id;

    public Tile(TileType tileType, int id) {
        this.tileType = tileType;
        this.id = id;
        this.selected = false;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
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
        return id == tile.id && selected == tile.selected && tileType == tile.tileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tileType, selected, id);
    }
}
