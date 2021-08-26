package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.io.GameHandler;
import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Money;
import com.lukas.tiles.model.Tile;
import com.lukas.tiles.model.TileType;
import com.lukas.tiles.model.building.Building;
import javafx.beans.property.StringProperty;

import java.util.HashSet;
import java.util.Set;

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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean buyBuilding(Building building) {
        //Check if enough money
        if (game.getFarmers().get(0).buyBuilding(building)) {
            tile.setOwner(game.getFarmers().get(0));
            tile.setBuilding(building);
            game.startBuilding(building);
            GameHandler.save(game);
            return true;
        }
        //Payment failed
        return false;
    }

    public TileType getTileType() {
        return tile.getTileType();
    }

    public Building getBuilding() {
        return tile.getBuilding();
    }

    public boolean hasBuilding() {
        return getBuilding() != null;
    }

    public Set<TileType> getNeighbourTileTypes() {
        Set<TileType> result = new HashSet<>();
        for (Tile tile1 : game.getMap().getAdjacent(tile)) {
            result.add(tile1.getTileType());
        }

        return result;
    }

    public Money getPlayerMoney() {
        return game.getFarmers().get(0).getMoney();
    }
}
