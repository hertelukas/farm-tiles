package com.lukas.tiles.model;

import com.lukas.tiles.model.building.Building;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of a tile
 */
public class Tile implements Serializable {
    @Serial
    private static final long serialVersionUID = 143647069791664939L;
    private TileType tileType;
    private transient boolean selected;
    private final int id;
    private Building building;
    private transient StringProperty name;
    private Farmer owner;

    /**
     * @param tileType specifies the ground of this tile
     * @param id       a unique id of the tile
     */
    public Tile(TileType tileType, int id) {
        this.tileType = tileType;
        this.id = id;
        this.selected = false;
        this.name = new SimpleStringProperty(getName());
    }

    /**
     * @return Get the type of the tile.
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * @param tileType Specify the type of the tile.
     */
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
        name.set(getName());
    }

    /**
     * @param selected Specify whether this tile is selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return Whether the tile is selected.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @return The name of a tile.
     */
    public StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty(getName());
        }
        return name;
    }

    /**
     * @return The name of a tile.
     */
    public String getName() {
        return tileType.name() + " " + id;
    }

    /**
     * @return The visual representation of the tile based on the buildings, the owner and the tile type.
     */
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

    /**
     * @param farmer Set an owner for this tile.
     */
    public void setOwner(Farmer farmer) {
        this.owner = farmer;
    }

    /**
     * @return Gets the farmer who owns this farm.
     */
    public Farmer getOwner() {
        return owner;
    }

    /**
     * @return Get the unique id of the tile.
     */
    public int getId() {
        return id;
    }

    /**
     * @param building Specify a building placed on this tile, can only be one.
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * @return The building placed on this tile.
     */
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
