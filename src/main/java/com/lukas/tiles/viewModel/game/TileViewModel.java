package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.*;
import com.lukas.tiles.model.building.Building;
import com.lukas.tiles.model.building.Headquarter;
import javafx.beans.property.StringProperty;

import java.util.HashSet;
import java.util.Set;

/**
 * The ViewModel for a tile
 * Responsible for controlling all button presses and displayed information
 */
public class TileViewModel {
    private final Tile tile;
    private final Game game;

    /**
     * Creates a new TileViewModel based on a given tile and game
     *
     * @param tile that should be represented
     * @param game that should be represented
     */
    public TileViewModel(Tile tile, Game game) {
        this.tile = tile;
        this.game = game;
    }

    /**
     * @return get the represented tile
     */
    // TODO: 8/29/21 remove this method
    @Deprecated
    public Tile getTile() {
        return tile;
    }

    /**
     * @return get the name of a given tile
     */
    public StringProperty getName() {
        return tile.nameProperty();
    }

    /**
     * Handle the buy process of a building on a given tile
     *
     * @param building that should be build
     * @return whether the transaction was successful
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean buyBuilding(Building building) {
        //Check if enough money
        if (game.getFarmers().get(0).buyBuilding(building)) {
            tile.setOwner(game.getFarmers().get(0));
            tile.setBuilding(building);
            game.startBuilding(building);
            if (building instanceof Headquarter) {
                game.getFarmers().get(0).setHeadquarter();
            }
            GameHandler.save(game);
            return true;
        }
        //Payment failed
        return false;
    }

    /**
     * @return the type of the current tile
     */
    // TODO: 8/29/21 maybe remove as well
    public TileType getTileType() {
        return tile.getTileType();
    }

    /**
     * @return get the building build on this tile
     */
    public Building getBuilding() {
        return tile.getBuilding();
    }

    /**
     * @return whether a building is being build or stands on this tile
     */
    public boolean hasBuilding() {
        return getBuilding() != null;
    }

    /**
     * @return a set of adjacent tile types
     */
    public Set<TileType> getNeighbourTileTypes() {
        Set<TileType> result = new HashSet<>();
        for (Tile tile1 : game.getMap().getAdjacent(tile)) {
            result.add(tile1.getTileType());
        }

        return result;
    }

    /**
     * @return the money instance of the current player
     */
    public Money getPlayerMoney() {
        return getFarmer().getMoney();
    }

    /**
     * @return the current player
     */
    public Farmer getFarmer() {
        return game.getFarmers().get(0);
    }
}
