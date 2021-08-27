package com.lukas.tiles.model;

import com.lukas.tiles.model.building.Building;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Tile implements Serializable {
    @Serial
    private static final long serialVersionUID = 143647069791664939L;
    private TileType tileType;
    private transient boolean selected;
    private final int id;
    private Building building;
    private transient StringProperty name;
    private Farmer owner;

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
        if (name == null) {
            name = new SimpleStringProperty(getName());
        }
        return name;
    }

    public String getName() {
        return tileType.name() + " " + id;
    }

    public Paint getPaint() {
        if (isSelected()) {
            return Color.RED;
        }
        if (owner == null) {
            return tileType.getColor();
        } else {
            Stop[] stops = new Stop[]{
                    new Stop(0.2, tileType.getColor()),
                    new Stop(0.6, owner.getColor())
            };
            return new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, stops);
        }
    }

    public void setOwner(Farmer farmer) {
        this.owner = farmer;
    }

    public Farmer getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(building, tile.building) && id == tile.id && selected == tile.selected && tileType == tile.tileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(building, tileType, selected, id);
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
