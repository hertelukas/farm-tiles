package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Tile;
import javafx.beans.property.StringProperty;

public class TileViewModel {
    private final Tile tile;

    public TileViewModel(Tile tile) {
        this.tile = tile;
    }

    public StringProperty getName() {
        return tile.nameProperty();
    }
}
