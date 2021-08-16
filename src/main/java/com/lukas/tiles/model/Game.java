package com.lukas.tiles.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Farmer> farmers;
    private final Player player;
    private final Map map;

    public Game(List<Farmer> farmers, Map map, Player player) {
        this.farmers = farmers;
        this.map = map;
        this.player = player;
    }

    public static Game generate(Difficulty difficulty, int farmers) {
        List<Farmer> tempFarmers = new ArrayList<>();
        for (int i = 0; i < farmers; i++) {
            tempFarmers.add(Farmer.generate(difficulty.getFarmerStart()));
        }

        return new Game(tempFarmers, new Map(), new Player(difficulty.getPlayerStart()));
    }
}
