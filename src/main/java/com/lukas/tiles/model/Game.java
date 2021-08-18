package com.lukas.tiles.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Farmer> farmers;
    private final Player player;
    private final WorldMap map;

    public Game(List<Farmer> farmers, WorldMap map, Player player) {
        this.farmers = farmers;
        this.map = map;
        this.player = player;
    }

    public static Game generate(Setup setup) {
        List<Farmer> tempFarmers = new ArrayList<>();
        for (int i = 0; i < setup.getFarmers(); i++) {
            tempFarmers.add(Farmer.generate(setup.getDifficulty().getFarmerStart()));
        }

        return new Game(tempFarmers, new WorldMap(10, 10), new Player(setup.getDifficulty().getPlayerStart()));
    }

    public List<Farmer> getFarmers() {
        return farmers;
    }

    public Player getPlayer() {
        return player;
    }

    public WorldMap getMap() {
        return map;
    }
}
