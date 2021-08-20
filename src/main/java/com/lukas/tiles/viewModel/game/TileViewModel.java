package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.building.Building;
import javafx.beans.property.StringProperty;

public class TileViewModel {
    private final Tile tile;
    private final Game game;

    public TileViewModel(Tile tile, Game game) {
        this.tile = tile;
        this.game = game;
    }

    public StringProperty getName() {
        return tile.nameProperty();
    }

    public boolean buyBuilding(Building building) {
        //Check if enough money
        if (game.getFarmers().get(0).buy(building.getPrice())) {
            tile.setBuilding(building);
            GameHandler.save(game);
            return true;
        }
        //Payment failed
        return false;
    }

    public Building getBuilding() {
        return tile.getBuilding();
    }

    public boolean hasBuilding() {
        return getBuilding() != null;
    }
}
