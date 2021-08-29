package com.lukas.tiles.model;

import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.building.Building;
import com.lukas.tiles.model.finance.EditableMoneyAccount;
import com.lukas.tiles.model.setup.FarmerColor;
import com.lukas.tiles.model.setup.Setup;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a savable game instance
 */
public class Game implements Serializable, TurnBasedUpdatable {
    @Serial
    private static final long serialVersionUID = 4745910367962440544L;
    private final List<Farmer> farmers;
    private final WorldMap map;
    private String name;
    private final Scheduler scheduler;

    /**
     * @param farmers all farmers participating in a game
     * @param map     the map of the current game
     * @param name    the name of the game
     */
    public Game(List<Farmer> farmers, WorldMap map, String name) {
        this.farmers = farmers;
        this.map = map;
        this.name = name;
        this.scheduler = new Scheduler();

        ///Saving the newly created game
        GameHandler.save(this);
        setup();
    }

    /**
     * This method sets up listeners and gets called after instantiation and after deserialization
     */
    private void setup() {
        //Update the game every ten seconds
        scheduler.counterProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.longValue() % 10 == 0) {
                doTurnBasedUpdate();
            }
        });
    }

    /**
     * @return a list of farmers participating in the game
     */
    public List<Farmer> getFarmers() {
        return farmers;
    }

    /**
     * @return the current map
     */
    public WorldMap getMap() {
        return map;
    }

    /**
     * @return the name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * @param name change the name of the current game
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return get the scheduler of this game
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * @param building that should be build
     */
    public void startBuilding(Building building) {
        scheduler.addObject(building);
    }

    /**
     * All game standings are getting recalculated
     */
    @Override
    public void doTurnBasedUpdate() {
        System.out.println("Updating game...");
        for (Farmer farmer : farmers) {
            farmer.doTurnBasedUpdate();
        }
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

    /**
     * @param setup with information about the game
     * @return a new game instance
     */
    public static Game generate(Setup setup) {
        List<Farmer> tempFarmers = new ArrayList<>();
        tempFarmers.add(Farmer.generate(new EditableMoneyAccount(setup.getDifficulty().getPlayerStart()), setup.getColor()));
        //The first farmer is always the player
        for (int i = 1; i < setup.getFarmers(); i++) {
            tempFarmers.add(Farmer.generate(new EditableMoneyAccount(setup.getDifficulty().getFarmerStart()), FarmerColor.getDefault()));
        }

        return new Game(tempFarmers, new WorldMap(setup.getMapSize(), setup.getMapType()), setup.getName());
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        setup();
    }


}
