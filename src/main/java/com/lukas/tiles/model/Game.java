package com.lukas.tiles.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private final List<Farmer> farmers;
    private final Player player;
    private final WorldMap map;
    private String name;

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

        return new Game(tempFarmers, new WorldMap(setup.getMapSize().getWidth(), setup.getMapSize().getHeight()), new Player(setup.getDifficulty().getPlayerStart()));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO: 8/19/21 Player is missing in comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(farmers, game.farmers) && Objects.equals(map, game.map) && Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(farmers, player, map, name);
    }
}
