package com.lukas.tiles.viewModel;

import com.lukas.tiles.model.Difficulty;
import com.lukas.tiles.model.Game;

public class GameViewModel {
    private final Game game;

    public GameViewModel() {
        this.game = Game.generate(Difficulty.Medium, 5);
    }
}
