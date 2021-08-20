package com.lukas.tiles.viewModel.game;

import com.lukas.tiles.model.Game;
import com.lukas.tiles.model.Setup;

public class GameViewModel {
    private final Game game;

    public GameViewModel(Setup setup) {
        this.game = Game.generate(setup);
    }

    public GameViewModel(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
