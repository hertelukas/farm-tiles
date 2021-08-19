package com.lukas.tiles.model;

import com.lukas.tiles.io.GameHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private final List<Farmer> farmers;
    private final WorldMap map;
    private String name;

    public Game(List<Farmer> farmers, WorldMap map) {
        this.farmers = farmers;
        this.map = map;
        this.name = "test";
        GameHandler.save(this);
    }

    public static Game generate(Setup setup) {
        List<Farmer> tempFarmers = new ArrayList<>();
        //The first farmer is always the player
        for (int i = 1; i < setup.getFarmers(); i++) {
            tempFarmers.add(Farmer.generate(setup.getDifficulty().getFarmerStart()));
        }

        return new Game(tempFarmers, new WorldMap(setup.getMapSize(), setup.getMapType()));
    }

    public List<Farmer> getFarmers() {
        return farmers;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        System.out.println("Farmers: " + Objects.equals(farmers, game.farmers));
        System.out.println("Map: " + Objects.equals(map, game.map));
        System.out.println("Name: " + Objects.equals(name, game.name));
        return Objects.equals(farmers, game.farmers) && Objects.equals(map, game.map) && Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(farmers, map, name);
    }
}
