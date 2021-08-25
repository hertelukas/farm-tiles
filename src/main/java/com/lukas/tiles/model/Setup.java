package com.lukas.tiles.model;

import com.lukas.tiles.model.setup.Difficulty;
import com.lukas.tiles.model.setup.FarmerColor;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;


public class Setup {
    private int farmers = 5;
    private FarmerColor farmerColor = FarmerColor.getDefault();
    private Difficulty difficulty;
    private MapSize mapSize = MapSize.getDefault();
    private MapType mapType = MapType.getDefault();

    private String name;

    public int getFarmers() {
        return farmers;
    }

    public void setFarmers(int farmers) {
        if (farmers < 1) {
            throw new IllegalArgumentException("#Farmers has to be positive");
        }
        this.farmers = farmers;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public MapSize getMapSize() {
        return mapSize;
    }

    public void setMapSize(MapSize mapSize) {
        this.mapSize = mapSize;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(FarmerColor farmerColor) {
        this.farmerColor = farmerColor;
    }

    public FarmerColor getColor() {
        return farmerColor;
    }

    @Override
    public String toString() {
        return "Setup{" +
                "farmers=" + farmers +
                ", farmerColor=" + farmerColor +
                ", difficulty=" + difficulty +
                ", mapSize=" + mapSize +
                ", mapType=" + mapType +
                ", name='" + name + '\'' +
                '}';
    }
}
