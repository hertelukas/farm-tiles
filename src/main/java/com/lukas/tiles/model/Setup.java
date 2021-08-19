package com.lukas.tiles.model;

import com.lukas.tiles.model.setup.Difficulty;
import com.lukas.tiles.model.setup.MapSize;
import com.lukas.tiles.model.setup.MapType;

public class Setup {
    private int farmers;
    private Difficulty difficulty;
    private MapSize mapSize = MapSize.Medium;
    private MapType mapType;

    public int getFarmers() {
        return farmers;
    }

    public void setFarmers(int farmers) {
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
}
