package com.lukas.tiles.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Tile {
    private TileType tileType;
    private boolean selected;
    private final int id;
    private final StringProperty name;

    public Tile(TileType tileType, int id) {
        this.tileType = tileType;
        this.id = id;
        this.selected = false;
        this.name = new SimpleStringProperty(getName());
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
        name.set(getName());
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return tileType.name() + " " + id;
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

    @Override
    public String toString() {
        return "Tile{" +
                "tileType=" + tileType +
                ", selected=" + selected +
                ", id=" + id +
                ", name=" + name +
                '}';
    }
}
