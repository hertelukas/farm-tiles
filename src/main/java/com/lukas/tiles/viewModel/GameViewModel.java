package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Setup;

public class GameViewModel {
    private final Game game;

    public GameViewModel(Setup setup) {
        this.game = Game.generate(setup);
    }

    public Game getGame() {
        return game;
    }
}